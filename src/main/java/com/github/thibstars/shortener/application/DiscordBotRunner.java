package com.github.thibstars.shortener.application;

import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthentication;
import com.github.thibstars.chatbotengine.auth.discord.DiscordTokenAuthenticationHandler;
import com.github.thibstars.chatbotengine.cli.commands.CommandExecutor;
import com.github.thibstars.chatbotengine.cli.io.discord.MessageChannelOutputStream;
import com.github.thibstars.chatbotengine.provider.discord.DiscordProvider;
import com.github.thibstars.shortener.config.DiscordBotEnvironment;
import com.github.thibstars.shortener.service.ShortenerService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Component running the discord bot.
 *
 * @author Thibault Helsmoortel
 */
@Component
@RequiredArgsConstructor
public class DiscordBotRunner extends ListenerAdapter implements CommandLineRunner {

    private final DiscordBotEnvironment discordBotEnvironment;
    private final CommandExecutor commandExecutor;

    private final DiscordProvider discordProvider;
    private final DiscordTokenAuthentication discordTokenAuthentication;
    private final MessageChannelOutputStream messageChannelOutputStream;
    private final ShortenerService shortenerService;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (processMessage(message)) {
            String msg = message.getContentDisplay();
            handleMessage(event, msg);
        }
    }

    private boolean processMessage(Message message) {
        return (discordBotEnvironment.isProcessBotMessages() && message.getAuthor().isBot()) || !message.getAuthor().isBot();
    }

    private void handleMessage(MessageReceivedEvent event, String msg) {
        if (msg.startsWith(discordBotEnvironment.getCommandPrefix())) {
            event.getChannel().sendTyping().queue();

            String parsedMessage = msg.substring(discordBotEnvironment.getCommandPrefix().length());
            messageChannelOutputStream.setMessageChannel(event.getChannel());
            commandExecutor.tryExecute(event, parsedMessage, () -> event.getChannel().sendMessage("Command not recognized...").queue(), () -> event.getChannel().sendMessage("Command failed!").queue());
        }
    }

    @Override
    public void run(String... args) {
        int tokenIndex = 0;
        int shortenerApiKeyIndex = tokenIndex + 1;
        int shortenerWorkspaceIndex = shortenerApiKeyIndex + 1;
        String token;
        String apiKey;
        String workspace;
        if (StringUtils.isNotBlank(discordBotEnvironment.getToken())) {
            token = discordBotEnvironment.getToken();
        } else {
            // Take token as first run arg (for example for when running from docker with an ENV variable)
            if (args != null && args.length > 0) {
                token = args[tokenIndex];
            } else {
                token = null;
                shortenerApiKeyIndex--;
                shortenerWorkspaceIndex--;
            }

            apiKey = args[shortenerApiKeyIndex];
            workspace = args[shortenerWorkspaceIndex];

            shortenerService.setApiKey(apiKey);
            shortenerService.setWorkspace(workspace);
        }

        JDABuilder jdaBuilder = ((DiscordTokenAuthenticationHandler) discordTokenAuthentication.getHandler()).getJdaBuilder();
        jdaBuilder.addEventListeners(this);
        discordTokenAuthentication.setToken(token);
        discordProvider.authenticate();

    }
}
