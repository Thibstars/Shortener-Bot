package com.github.thibstars.shortener.commands;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.github.thibstars.shortener.BaseTest;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

/**
 * @author Thibault Helsmoortel
 */
abstract class CommandBaseTest extends BaseTest {

    @Mock
    MessageReceivedEvent messageReceivedEvent;

    @Mock
    Message message;

    @Mock
    MessageChannel messageChannel;

    @BeforeEach
    void setUp() {
        when(messageReceivedEvent.getChannel()).thenReturn(messageChannel);
        when(messageReceivedEvent.getMessage()).thenReturn(message);
        when(message.addReaction(anyString())).thenReturn(mock(RestAction.class));
        when(messageChannel.sendMessage(anyString())).thenReturn(mock(MessageAction.class));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void verifyOneMessageSent(String message) {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(message);
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    void verifyOneMessageSent(MessageEmbed embed) {
        verify(messageReceivedEvent).getChannel();
        verify(messageChannel).sendMessage(embed);
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

    void verifyDoNotProcessEvent(BaseCommand baseCommand, Event event) throws Exception {
        baseCommand.setContext(event);

        String message = (String) baseCommand.call();

        Assertions.assertNull(message, "Message should not be processed.");
        verifyNoMoreJDAInteractions();
    }

    void verifyNoMoreJDAInteractions() {
        verifyNoMoreInteractions(messageChannel);
        verifyNoMoreInteractions(messageReceivedEvent);
    }

}