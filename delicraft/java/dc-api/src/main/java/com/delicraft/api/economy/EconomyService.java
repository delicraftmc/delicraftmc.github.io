package com.delicraft.api.economy;

import java.util.Optional;
import java.util.UUID;

public interface EconomyService {
    long getBalance(UUID player);
    boolean deposit(UUID player, long amount);
    boolean withdraw(UUID player, long amount);
    boolean transfer(UUID from, UUID to, long amount);
    boolean createAccount(UUID player);
    Optional<Long> getTransactionCount(UUID player);
}
