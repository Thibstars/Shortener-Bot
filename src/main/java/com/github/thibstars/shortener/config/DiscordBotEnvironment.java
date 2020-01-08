package com.github.thibstars.shortener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Discord bot environment properties.
 *
 * @author Thibault Helsmoortel
 */
@ConfigurationProperties(prefix = "bot")
public class DiscordBotEnvironment {

    private String token;

    private String author;

    private String name;

    private String description;

    private String version;

    private String commandPrefix;

    private boolean processBotMessages;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommandPrefix() {
        return commandPrefix;
    }

    public void setCommandPrefix(String commandPrefix) {
        this.commandPrefix = commandPrefix;
    }

    public boolean isProcessBotMessages() {
        return processBotMessages;
    }

    public void setProcessBotMessages(boolean processBotMessages) {
        this.processBotMessages = processBotMessages;
    }
}
