# ADR-004: Module System

## Problem
Hem client (HUD, Cosmetic, Utility) hem server (AntiCheat, Economy) tarafında standart bir eklenti sistemine ihtiyaç var.

## Alternatifler
1. **Monolithic**: Tüm özellikler aynı kod tabanında. Basit ama genişletilemez.
2. **Interface-based modules**: Her özellik ayrı interface. Esnek, test edilebilir.
3. **Service-based**: Her özellik ayrı service. Gevşek bağlı, ama fazla abstraction.

## Seçilen Çözüm
Interface-based module sistemi. Her module ayrı interface'lerden oluşur (LifecycleModule, RenderableModule, ConfigurableModule, DependentModule).

## Neden Seçildi
- Her module sadece ihtiyacı olan interface'i implemente eder
- Server module'leri render implement etmek zorunda değil
- Annotation-based discovery ile kayıt otomatik
- Dependency graph + cycle detection

## Sonuçları
- Her module bağımsız test edilebilir
- Hot-reload sadece config reload olarak sınırlı (v1)
- Module sayısı 100+ olunca discovery performansı izlenmeli
