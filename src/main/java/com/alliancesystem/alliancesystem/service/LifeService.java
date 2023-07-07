package com.alliancesystem.alliancesystem.service;

import com.alliancesystem.alliancesystem.cache.ContextCache;
import com.alliancesystem.alliancesystem.dto.GiftedLifeDto;
import com.alliancesystem.alliancesystem.dto.LifeRequestDto;
import com.alliancesystem.alliancesystem.exception.alliancerelated.UserHasNoAllianceException;
import com.alliancesystem.alliancesystem.exception.alliancerelated.UserNotInSameAllianceException;
import com.alliancesystem.alliancesystem.exception.liferelated.*;
import com.alliancesystem.alliancesystem.model.Alliance;
import com.alliancesystem.alliancesystem.model.GiftedLife;
import com.alliancesystem.alliancesystem.model.LifeRequest;
import com.alliancesystem.alliancesystem.model.User;
import com.alliancesystem.alliancesystem.repository.GiftedLifeRepository;
import com.alliancesystem.alliancesystem.repository.LifeRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LifeService {
    private final LifeRequestRepository lifeRequestRepository;
    private final GiftedLifeRepository giftedLifeRepository;
    private final ContextCache contextCache;
    private final UserService userService;

    public LifeService(LifeRequestRepository lifeRequestRepository, GiftedLifeRepository giftedLifeRepository, ContextCache contextCache, UserService userService) {
        this.lifeRequestRepository = lifeRequestRepository;
        this.giftedLifeRepository = giftedLifeRepository;
        this.contextCache = contextCache;
        this.userService = userService;
    }

    public LifeRequest getLifeRequestEntityById(String lifeRequestId) {
        return lifeRequestRepository.findById(lifeRequestId)
                .orElseThrow(LifeRequestNotFoundException::new);
    }

    public long timeDifference(LocalDateTime existingTime) {
        return Duration.between(existingTime, LocalDateTime.now()).getSeconds();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createLifeRequest() {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (!currentUser.isInAlliance()) throw new UserHasNoAllianceException();

        Short countGiftedLifes = giftedLifeRepository.countByReceiverIdAndUsedIsFalse(currentUser.getId());

        if (countGiftedLifes >= 10) throw new UserMaximumGiftedLifeException();

        if (timeDifference(currentUser.getLastLifeRequestTime()) <= 10800)
            throw new InsufficientTimeElapsedException();

        Optional<LifeRequest> lastRequest = lifeRequestRepository.findByReceiverIdAndActiveIsTrue(currentUser.getId());

        if (lastRequest.isPresent()) {
            LifeRequest extractLifeRequest = lastRequest.get();
            extractLifeRequest.setActive(false);
            lifeRequestRepository.save(extractLifeRequest);
        }

        LifeRequest lifeRequest = new LifeRequest(currentUser, currentUser.getAlliance());
        lifeRequestRepository.save(lifeRequest);

        currentUser.setLastLifeRequestTime(LocalDateTime.now());
        userService.updateUserWithEntity(currentUser);

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void giveLifeGift(String lifeRequestId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (!currentUser.isInAlliance()) throw new UserHasNoAllianceException();

        LifeRequest lifeRequest = getLifeRequestEntityById(lifeRequestId);
        User targetUser = lifeRequest.getReceiver();

        if (currentUser.equals(targetUser)) throw new SelfGiftException();

        if (!currentUser.getAlliance().equals(targetUser.getAlliance())) throw new UserNotInSameAllianceException();

        if (lifeRequest.getSenders().contains(currentUser)) throw new UserAlreadyInSendersException();

        lifeRequest.getSenders().add(currentUser);

        if (lifeRequest.getSenders().size() >= 5)
            lifeRequest.setActive(false);

        lifeRequestRepository.save(lifeRequest);

        GiftedLife giftedLife = new GiftedLife(currentUser, targetUser);
        giftedLifeRepository.save(giftedLife);

        currentUser.setCoin(currentUser.getCoin() + 10);
        userService.updateUserWithEntity(currentUser);

    }

    public List<LifeRequestDto> activeLifeRequests() {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());
        Alliance alliance = currentUser.getAlliance();

        if (Objects.isNull(alliance)) throw new UserHasNoAllianceException();

        return lifeRequestRepository.getActiveLifeRequestsByAllianceId(alliance.getId());
    }

    public List<GiftedLifeDto> getActiveGiftedLifeList() {
        return giftedLifeRepository.getActiveGiftedLifeDtoListByReceiverId(contextCache.getCurrentUser().getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void useGiftedLife(String giftLifeId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (currentUser.getLife() >= 5) throw new UserMaximumLifeException();

        GiftedLife giftedLife = giftedLifeRepository.findByIdAndReceiverIdAndUsedIsFalse(giftLifeId, currentUser.getId())
                .orElseThrow(GiftedLifeNotFoundException::new);

        giftedLife.setUsed(true);
        giftedLifeRepository.save(giftedLife);

        currentUser.setLife(currentUser.getLife() + 1);
        userService.updateUserWithEntity(currentUser);
    }


}
