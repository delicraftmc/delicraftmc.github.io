# ADR-003: EventBus Sistemi

## Problem
Modüller arası iletişim için gevşek bağlı bir event sistemine ihtiyaç var. Doğrudan method çağrıları bağımlılığı artırır.

## Alternatifler
1. **Doğrudan method çağrısı**: Basit ama sıkı bağlı, genişletmesi zor.
2. **Observer pattern**: Interface-based, ama her event için yeni interface gerekir.
3. **EventBus**: Generic, gevşek bağlı, priority/cancellation/async destekler.

## Seçilen Çözüm
Custom EventBus implementasyonu (dc-api içinde).

## Neden Seçildi
- Zero external dependency
- Priority sistemi (LOWEST → MONITOR)
- Cancellation desteği
- Sync + Async dispatch
- Sticky event (geç event'i yeni subscriber'a gönder)

## Sonuçları
- Event sayısı arttıkça performans etkilenmez (HashMap lookup)
- Async event için thread pool yönetimi gerekir
- Memory leak riski (unregister unutulursa)
