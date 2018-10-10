package com.codeko.funnythread.service.deadlock;

import java.lang.management.ThreadInfo;

public interface DeadlockHandler {
    void handleDeadlock(final ThreadInfo[] deadlockedThreads);
}
