# Spring Boot İttifak Sistemi Uygulaması
## Temel Gereklilikler:
- Oyuncular 5 can ve 500 coin ile başlar
- Oyuncu 25 seviye üzerindeyse 200 coin karşılığı takım oluşturabilir ( test durumları için seviye kısmı yorum satırına alınarak deaktif edildi)
- Oyuncu maksimum kendi seviyesi kadar takım için minimum gerekli seviye belirleyebilir
- Takımlar davet usulu veya açık katılım ile maksimum 50 üye kabul edebilir
- Bir oyuncu katılma başvurusunda bulunduğu takımdan reddilirse bir daha o takımın üyesi olamaz
- Bir oyuncunun katılma isteği kabul edilirse diğer takıma attıkları istekler iptal edilir
- Bir oyuncu bulunduğu takımdan çıkarılırsa bir daha o takıma başvuramaz
- Kendi isteği ile takımından ayrılan oyuncu tekrar o takımın üyesi olabilir
- Takım oyuncuları kendi aralarında can isteğinde bulunabilir
- Bir oyuncu her bir isteğe sadece bir kez yardımda bulunabilir
- Takım arkadaşının can isteğine yardım eden oyuncu 10 coin kazanır ve kendi canı eksilmez
- Oyuncular 3 saatte bir can yardımı talep edebilirler
- Oyuncunun mevcutta 10'dan fazla hediye canı varsa takımından can yardımı talep edemez
- Oyuncunun mevcut canı 5 ise hediye canı kullanamaz, kullanılmak için bekletilirler
- Can yardımı istekleri 5 oyuncu yardım edene dek veya oyuncu yeniden yardım isteği bulunana dek aktif olarak kalır
- Takımlar sıralamaları üyelerin toplam seviyesine göre belirlenir
