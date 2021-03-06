package com.github.thibstars.shortener.application;

import com.github.thibstars.shortener.config.DiscordBotEnvironment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Startup application listener performing tasks upon startup.
 *
 * @author Thibault Helsmoortel
 */
@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationStartedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartupApplicationListener.class);

    private final DiscordBotEnvironment discordBotEnvironment;

    @Autowired
    public StartupApplicationListener(DiscordBotEnvironment discordBotEnvironment) {
        this.discordBotEnvironment = discordBotEnvironment;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent applicationStartedEvent) {
        LOGGER.info("Application started.");
        if (StringUtils.isNotBlank(discordBotEnvironment.getAuthor())) {
            LOGGER.info("Author: {}", discordBotEnvironment.getAuthor());
        }

        if (StringUtils.isNotBlank(discordBotEnvironment.getName())) {
            LOGGER.info("Name: {}", discordBotEnvironment.getName());
        }

        if (StringUtils.isNotBlank(discordBotEnvironment.getVersion())) {
            LOGGER.info("Version: {}", discordBotEnvironment.getVersion());
        }
    }
}
