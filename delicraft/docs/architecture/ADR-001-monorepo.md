# ADR-001: Monorepo Yapısı

## Problem
DeliCraft ekosistemi dört ana bileşenden oluşur: ortak kütüphane, API katmanı, Paper sunucu plugin'i ve NeoForge istemci mod'u. Bu bileşenlerin versiyon uyumu ve bağımlılık yönetimi tek bir repository'de daha kolay yönetilir.

## Alternatifler
1. **Multi-repo**: Her bileşen ayrı repository. Bağımsız geliştirme, ama versiyon uyumu zor.
2. **Monorepo**: Tüm bileşenler aynı repository. Bağımlılık yönetimi kolay, CI entegre.

## Seçilen Çözüm
Monorepo. Tek bir repository altında:
- `java/` — Java multi-module Gradle projesi (dc-common, dc-api, dc-server, dc-client)
- `launcher/` — WPF/C# launcher (ayrı build sistemi)
- `docs/` — Dokümantasyon

## Neden Seçildi
- Tüm bileşenler aynı versiyonda kalır
- Breaking change anında tespit edilir
- CI/CD tek pipeline ile yönetilir
- Paper/NeoForge güncellemeleri tek noktadan yapılır

## Sonuçları
- Versiyon uyumu garanti
- Build süresi daha uzun (paralel build ile optimize edilir)
- Disk kullanımı daha fazla
- Git history daha büyük
