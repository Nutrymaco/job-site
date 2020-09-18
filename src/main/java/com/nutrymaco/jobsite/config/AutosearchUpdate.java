package com.nutrymaco.jobsite.config;

import com.nutrymaco.jobsite.service.autosearch.AutosearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class AutosearchUpdate {
    @Autowired
    AutosearchService autosearchService;

    @Scheduled(cron = " 0 0 12,16 * * *")
    public void updateAutosearches() {
        log.info("updating autosearches");
        autosearchService.updateAllAutosearches();
    }
}
