package com.github.thibstars.shortener.commands;

import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import com.github.thibstars.shortener.service.ShortenerResponse;
import com.github.thibstars.shortener.service.ShortenerService;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

/**
 * @author Thibault Helsmoortel
 */
@Command(name = "shorten", description = "Shortens provided urls.")
@Component
@EqualsAndHashCode(callSuper = false)
@RequiredArgsConstructor
public class ShortenCommand extends BaseCommand<MessageReceivedEvent, ShortenerResponse> {

    @SuppressWarnings("unused") // Used through CLI
    @Parameters(paramLabel = "URL", description = "The long url to shorten.", index = "0", arity = "1")
    private String url;

    private final ShortenerService shortenerService;

    @Override
    public ShortenerResponse call() {
        ShortenerResponse shortenerResponse = shortenerService.shorten(url);
        String shortUrl = shortenerResponse.getShortUrl();

        if (!shortUrl.startsWith("http")) {
            shortUrl = "http://" + shortUrl;
        }

        if (getContext() != null) {
            getContext().getChannel().sendMessage(shortUrl).queue();
        }

        return shortenerResponse;
    }
}
