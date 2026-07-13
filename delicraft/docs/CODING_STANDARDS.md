# DeliCraft Coding Standards

## Naming Conventions
- Packages: `com.delicraft.{module}.{feature}` (all lowercase)
- Interfaces: `Dli` prefix only when global (DliModule), otherwise semantic (PlayerService)
- Implementations: `Default` prefix (DefaultPlayerService) or `Server`/`Client` prefix
- Records: immutable, no prefix (PlayerData, HandshakeRequest)
- Constants: UPPER_SNAKE in `final class` with private constructor
- Enums: PascalCase (ModuleCategory.HUD)

## Package Structure
```
com.delicraft.common      — platform-independent models, DTOs, packets
com.delicraft.api         — public interfaces only (no implementation)
com.delicraft.server      — Paper plugin implementation
com.delicraft.client      — NeoForge mod implementation
```

## Manager vs Service vs Registry vs Factory
- **Manager**: lifecycle ownership, stateful (ModuleManager, PlayerManager)
- **Service**: stateless business logic (EconomyService, AntiCheatService)
- **Registry**: lookup/store by key (PacketRegistry, ServiceRegistry)
- **Factory**: creates instances (PacketFactory)
- **Repository**: data access (PlayerRepository)
- **Handler**: event/callback processing (PacketHandler)
- **Provider**: supplies configuration (ConfigProvider)

## Module Rules
- Module extends AbstractModule (or implements DliModule + applicable interfaces)
- Module ID format: `{category}.{name}` (e.g. `hud.fps`, `feature.anticheat`)
- Server modules never implement RenderableModule
- Client HUD modules always implement RenderableModule
- Circular dependencies between modules are detected at startup

## Dependency Rules
- dc-common → (zero dependencies, Java 21 only)
- dc-api → dc-common
- dc-server → dc-common + dc-api
- dc-client → dc-common + dc-api
- No downward imports. No circular dependencies.

## Code Style
- Java 21, `--release 21`
- Records for DTOs and models
- Sealed interfaces for packet hierarchies
- `@Inject` annotation for DI (no framework)
- SLF4J for logging (never System.out)
- UTF-8 encoding everywhere
- No wildcard imports
- Braces on same line (K&R style)
