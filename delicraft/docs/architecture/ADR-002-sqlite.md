# ADR-002: SQLite Veritabanı

## Problem
Oyuncu verileri, ekonomi, ceza kayıtları ve istatistiklerin kalıcı olarak saklanması gerekiyor.

## Alternatifler
1. **JSON dosyaları**: Basit, kurulum gereksiz. Ama 10k+ kayıtta performans düşer, query imkansız.
2. **SQLite**: Tek dosya, kurulum yok, SQL sorguları, ACID uyumlu.
3. **MySQL/PostgreSQL**: Güçlü ama kurulum gerektirir, sunucu yükü fazla.

## Seçilen Çözüm
SQLite, Flyway migration ile.

## Neden Seçildi
- Paper sunucusu ile birlikte tek JAR içinde dağıtılır
- Sıfır konfigürasyon (otomatik oluşturulur)
- Hypixel ilk yıllarında SQLite kullandı
- Backup tek dosyayı kopyalamak kadar kolay
- Flyway ile migration standardı

## Sonuçları
- Veri büyüdükçe (50k+ kayıt) performans düşebilir → o noktada MySQL'e geçiş
- Concurrent write sınırlı (WAL mode ile optimize edilir)
- Replikasyon imkansız (single file)
