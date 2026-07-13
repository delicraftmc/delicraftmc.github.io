package com.delicraft.api.anticheat;

import java.util.Optional;
import java.util.UUID;

public interface AntiCheatService {
    boolean isEnabled();
    void setEnabled(boolean enabled);
    CheckResult checkMovement(UUID player, MovementData data);
    CheckResult checkCombat(UUID player, CombatData data);
    int getViolationLevel(UUID player);
    void resetViolations(UUID player);

    record MovementData(double deltaX, double deltaY, double deltaZ, float yaw, float pitch) {}
    record CombatData(double reach, double angle, int hitCount) {}
    record CheckResult(String check, boolean failed, String reason, double vl) {}
}
