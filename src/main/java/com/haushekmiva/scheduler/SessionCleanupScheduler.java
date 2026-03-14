package com.haushekmiva.scheduler;

import com.haushekmiva.service.AuthService;
import com.haushekmiva.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionCleanupScheduler {

    private final SessionService sessionService;

    @Scheduled(cron = "0 0 * * * *")
    public void cleanOldSessions() {
        sessionService.removeExpiredSessions();
    }

}
