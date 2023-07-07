package com.alliancesystem.alliancesystem.service;

import com.alliancesystem.alliancesystem.cache.ContextCache;
import com.alliancesystem.alliancesystem.dto.AllianceDto;
import com.alliancesystem.alliancesystem.dto.request.AllianceCreateRequest;
import com.alliancesystem.alliancesystem.dto.response.AllianceMembersInfoResponse;
import com.alliancesystem.alliancesystem.dto.response.AllianceRankResponse;
import com.alliancesystem.alliancesystem.exception.alliancerelated.*;
import com.alliancesystem.alliancesystem.exception.userrelated.UserNotEnoughCoinException;
import com.alliancesystem.alliancesystem.mapper.AllianceDtoMapper;
import com.alliancesystem.alliancesystem.model.Alliance;
import com.alliancesystem.alliancesystem.model.User;
import com.alliancesystem.alliancesystem.repository.AllianceRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AllianceService {
    private final AllianceRepository allianceRepository;
    private final ContextCache contextCache;
    private final UserService userService;
    private final AllianceDtoMapper allianceDtoMapper;

    public AllianceService(AllianceRepository allianceRepository, ContextCache contextCache, UserService userService, AllianceDtoMapper allianceDtoMapper) {
        this.allianceRepository = allianceRepository;
        this.contextCache = contextCache;
        this.userService = userService;
        this.allianceDtoMapper = allianceDtoMapper;
    }

    public Alliance getAllianceEntityById(String allianceId) {
        return allianceRepository.findById(allianceId)
                .orElseThrow(AllianceNotFoundException::new);
    }

    private void checkAllianceRequirements(User user, Alliance alliance) {
        if (user.isInAlliance()) throw new UserAlreadyHasAllianceException();
        if (alliance.getMemberCount() >= 50) throw new AllianceHasMaximumMemberException();
        if (user.getLevel() < alliance.getRequiredLevel()) throw new UserLevelInsufficientException();
    }

    private void checkAllianceBlockUser(User user, Alliance alliance) {
        boolean blockCheck = allianceRepository.existsByBlockedUsersIdAndId(user.getId(), alliance.getId());
        if (blockCheck) throw new UserOnBlockedListException();
    }

    public AllianceDto getAllianceDtoById(Optional<String> allianceId) {
        if (allianceId.isPresent()) {
            Alliance alliance = getAllianceEntityById(allianceId.get());

            Integer allianceScore = allianceRepository.getSumOfMembersLevelByAllianceId(allianceId.get());

            List<AllianceMembersInfoResponse> allianceMembersInfoResponses = allianceRepository.getMembersInfoByAllianceId(allianceId.get());

            return allianceDtoMapper.toDto(alliance, allianceScore, allianceMembersInfoResponses);

        } else {
            User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());
            if (!currentUser.isInAlliance()) throw new UserHasNoAllianceException();

            Alliance alliance = currentUser.getAlliance();

            Integer allianceScore = allianceRepository.getSumOfMembersLevelByAllianceId(alliance.getId());

            List<AllianceMembersInfoResponse> allianceMembersInfoResponses = allianceRepository.getMembersInfoByAllianceId(alliance.getId());

            return allianceDtoMapper.toDto(alliance, allianceScore, allianceMembersInfoResponses);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AllianceDto createAlliance(AllianceCreateRequest allianceCreateRequest) {
        boolean allianceNameControl = allianceRepository.existsByName(allianceCreateRequest.getName());

        if (allianceNameControl) throw new AllianceNameAlreadyExistsException();

        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (currentUser.isInAlliance()) throw new UserAlreadyHasAllianceException();

        if (currentUser.getLevel() < allianceCreateRequest.getRequiredLevel())
            throw new UserLevelInsufficientException();

        if(currentUser.getCoin() < 200) throw new UserNotEnoughCoinException();

        allianceRepository.deleteUserAllianceRequestForAll(currentUser.getId());

        Alliance alliance = Alliance.builder()
                .name(allianceCreateRequest.getName())
                .description(allianceCreateRequest.getDescription())
                .joinType(allianceCreateRequest.isJoinType())
                .emblem(allianceCreateRequest.getEmblem())
                .requiredLevel(allianceCreateRequest.getRequiredLevel())
                .leader(currentUser)
                .memberCount(1)
                .createdAt(LocalDateTime.now())
                .build();

        Alliance savedAlliance = allianceRepository.save(alliance);

        currentUser.setCoin(currentUser.getCoin() - 200);
        currentUser.setInAlliance(true);
        currentUser.setAlliance(savedAlliance);
        userService.updateUserWithEntity(currentUser);

        AllianceMembersInfoResponse allianceMembersInfoResponse = new AllianceMembersInfoResponse();
        allianceMembersInfoResponse.setId(currentUser.getId());
        allianceMembersInfoResponse.setUsername(currentUser.getUsername());
        allianceMembersInfoResponse.setAvatar(currentUser.getAvatar());
        allianceMembersInfoResponse.setLevel(currentUser.getLevel());

        return allianceDtoMapper
                .toDto(savedAlliance, currentUser.getLevel(), List.of(allianceMembersInfoResponse));
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void joinAlliance(String allianceId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());
        Alliance alliance = getAllianceEntityById(allianceId);

        if (!alliance.isJoinType()) throw new AllianceInvitationRequiredException();

        checkAllianceBlockUser(currentUser, alliance);
        checkAllianceRequirements(currentUser, alliance);

        alliance.setMemberCount(alliance.getMemberCount() + 1);
        allianceRepository.save(alliance);

        currentUser.setInAlliance(true);
        currentUser.setAlliance(alliance);
        userService.updateUserWithEntity(currentUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void sendJoinRequest(String allianceId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());
        Alliance alliance = getAllianceEntityById(allianceId);

        if (alliance.isJoinType()) throw new AllianceNoInvitationRequiredException();

        checkAllianceBlockUser(currentUser, alliance);
        checkAllianceRequirements(currentUser, alliance);

        allianceRepository.insertUserAllianceRequest(currentUser.getId(), alliance.getId());
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void acceptJoinRequest(String userId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());
        if (!currentUser.isInAlliance()) throw new UserInvalidAuthorizationException();

        Alliance alliance = currentUser.getAlliance();
        if (!currentUser.getId().equals(alliance.getLeader().getId())) throw new UserInvalidAuthorizationException();

        boolean hasRequest = allianceRepository.existsByUserRequestsToJoinIdAndId(userId, alliance.getId());
        if (!hasRequest) throw new UserHasNoJoinRequestException();

        User requestingUser = userService.getUserEntityById(userId);
        checkAllianceRequirements(requestingUser, alliance);

        alliance.setMemberCount(alliance.getMemberCount() + 1);
        allianceRepository.save(alliance);

        requestingUser.setInAlliance(true);
        requestingUser.setAlliance(alliance);
        userService.updateUserWithEntity(requestingUser);

        allianceRepository.deleteUserAllianceRequestForAll(requestingUser.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void rejectJoinRequest(String userId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (!currentUser.isInAlliance()) throw new UserInvalidAuthorizationException();

        User requestingUser = userService.getUserEntityById(userId);

        Alliance alliance = currentUser.getAlliance();

        if (!currentUser.getId().equals(alliance.getLeader().getId())) throw new UserInvalidAuthorizationException();

        boolean checkRequest = allianceRepository.existsByUserRequestsToJoinIdAndId(userId, alliance.getId());

        if (!checkRequest) throw new UserHasNoJoinRequestException();

        allianceRepository.deleteUserAllianceRequest(requestingUser.getId(), alliance.getId());
        allianceRepository.insertAllianceBlockedUser(requestingUser.getId(), alliance.getId());

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void leaveAlliance() {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (!currentUser.isInAlliance()) throw new UserHasNoAllianceException();

        Alliance alliance = currentUser.getAlliance();

        if (currentUser.getId().equals(alliance.getLeader().getId())) throw new AllianceLeaderLeaveException();

        alliance.setMemberCount(alliance.getMemberCount() - 1);
        allianceRepository.save(alliance);

        currentUser.setInAlliance(false);
        currentUser.setAlliance(null);

        userService.updateUserWithEntity(currentUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void removeUser(String userId) {
        User currentUser = userService.getUserEntityById(contextCache.getCurrentUser().getId());

        if (!currentUser.isInAlliance()) throw new UserInvalidAuthorizationException();

        Alliance alliance = currentUser.getAlliance();

        if (!currentUser.getId().equals(alliance.getLeader().getId())) throw new UserInvalidAuthorizationException();

        User selectedUser = userService.getUserEntityById(userId);

        if (!selectedUser.isInAlliance()) throw new UserHasNoAllianceException();

        if (currentUser.getId().equals(selectedUser.getId())) throw new UserSelfRemoveAllianceException();

        if (!selectedUser.getAlliance().getId().equals(alliance.getId())) throw new UserNotInSameAllianceException();

        selectedUser.setInAlliance(false);
        selectedUser.setAlliance(null);
        userService.updateUserWithEntity(selectedUser);

        alliance.setMemberCount(alliance.getMemberCount() - 1);
        allianceRepository.save(alliance);
        allianceRepository.insertAllianceBlockedUser(selectedUser.getId(), alliance.getId());

    }

    public List<AllianceRankResponse> getRankAlliances(Optional<Integer> page, Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.get(), size.get());
        return allianceRepository.getAllianceRankList(pageRequest);
    }


}
