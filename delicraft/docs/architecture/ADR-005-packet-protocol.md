# ADR-005: Packet Protocol

## Problem
Client ve server arasında standart bir iletişim protokolü gerekiyor. Packet yapısı, serialization ve direction net olmalı.

## Alternatifler
1. **Raw bytes**: Performanslı ama hata yapmaya açık.
2. **JSON**: Okunabilir, ama boyut büyük, parsing yavaş.
3. **Binary protocol**: Kompakt, hızlı, tip güvenli.

## Seçilen Çözüm
Binary packet protocol. Her packet:
- int packetId
- byte[] data (custom serializer ile)

## Neden Seçildi
- Minecraft network protocol'ü ile uyumlu
- Packet boyutu küçük (kritik, çünkü her tick 20+ packet)
- Serialization/Deserialization hızlı
- Direction: CLIENTBOUND / SERVERBOUND ayrımı net

## Sonuçları
- Packet version negotiation (handshake'de kontrol)
- Protocol version breaking change → disconnect
- Yeni packet eklemek kolay (sadece yeni ID + serializer)
