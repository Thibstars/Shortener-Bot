package com.github.thibstars.shortener.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.thibstars.shortener.config.DiscordBotEnvironment;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * @author Thibault Helsmoortel
 */
class AboutCommandTest extends CommandBaseTest {

    @Mock
    private DiscordBotEnvironment discordBotEnvironment;

    @DisplayName("Should reply mystery.")
    @Test
    void shouldReplyMystery() {
        when(discordBotEnvironment.getName()).thenReturn(null);
        when(discordBotEnvironment.getAuthor()).thenReturn(null);
        AboutCommand command = new AboutCommand(discordBotEnvironment);
        command.setContext(messageReceivedEvent);

        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(mock(MessageAction.class));

        MessageEmbed embed = (MessageEmbed) command.call();

        Assertions.assertNotNull(embed, "Message should not be null.");
        Assertions.assertEquals("Mystery bot by mystery author.", embed.getTitle(), "Message should be correct.");

        verifyOneMessageSent(embed);
    }

    @DisplayName("Should reply about message.")
    @Test
    void shouldReplyAboutMessage() {
        String name = "myBot";
        when(discordBotEnvironment.getName()).thenReturn(name);
        String author = "myAuthor";
        when(discordBotEnvironment.getAuthor()).thenReturn(author);
        String description = "my bot is the best";
        when(discordBotEnvironment.getDescription()).thenReturn(description);

        AboutCommand command = new AboutCommand(discordBotEnvironment);
        command.setContext(messageReceivedEvent);

        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(mock(MessageAction.class));

        MessageEmbed embed = (MessageEmbed) command.call();

        Assertions.assertNotNull(embed, "Message should not be null.");
        Assertions.assertEquals(name + " created by " + author + "." + System.lineSeparator() + description, embed.getDescription(), "Message should be correct.");

        verifyOneMessageSent(embed);
    }

    @DisplayName("Should reply about message without bot name.")
    @Test
    void shouldReplyAboutMessageWithoutBotName() {
        when(discordBotEnvironment.getName()).thenReturn(null);
        String author = "myAuthor";
        when(discordBotEnvironment.getAuthor()).thenReturn(author);
        String description = "my bot is the best";
        when(discordBotEnvironment.getDescription()).thenReturn(description);

        AboutCommand command = new AboutCommand(discordBotEnvironment);
        command.setContext(messageReceivedEvent);

        when(messageChannel.sendMessage(any(MessageEmbed.class))).thenReturn(mock(MessageAction.class));

        MessageEmbed embed = (MessageEmbed) command.call();

        Assertions.assertNotNull(embed, "Message should not be null.");
        Assertions.assertEquals("Bot created by " + author + "." + System.lineSeparator() + description, embed.getDescription(), "Message should be correct.");

        verifyOneMessageSent(embed);
    }

    @DisplayName("Should not process event.")
    @Test
    void shouldNotProcessEvent() throws Exception {
        AboutCommand command = new AboutCommand(discordBotEnvironment);

        verifyDoNotProcessEvent(command, null);
    }

}
