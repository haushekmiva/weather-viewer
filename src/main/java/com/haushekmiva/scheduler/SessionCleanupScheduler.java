package com.haushekmiva.scheduler;

import com.haushekmiva.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionCleanupScheduler {

    private final AuthService authService;

    @Scheduled(cron = "0 0 * * * *")
    public void cleanOldSessions() {
        authService.removeExpiredSessions();
    }

}
