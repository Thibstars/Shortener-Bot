package be.thibaulthelsmoortel.discordbotbase.commands;

import be.thibaulthelsmoortel.discordbotbase.config.DiscordBotEnvironment;
import com.github.thibstars.chatbotengine.cli.commands.BaseCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;

/**
 * Basic command providing general information on the bot.
 *
 * @author Thibault Helsmoortel
 */
@Command(name = "about", description = "Provides general information about the bot.")
@Component
public class AboutCommand extends BaseCommand<MessageReceivedEvent, Object> {

    private final DiscordBotEnvironment discordBotEnvironment;

    @Autowired
    public AboutCommand(DiscordBotEnvironment discordBotEnvironment) {
        this.discordBotEnvironment = discordBotEnvironment;
    }

    @Override
    public Object call() {
        MessageEmbed embed = null;

        if (getContext() != null) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            if (StringUtils.isAllBlank(discordBotEnvironment.getName(), discordBotEnvironment.getAuthor())) {
                embedBuilder.setTitle("Mystery bot by mystery author.");
            } else {
                embedBuilder.setDescription(whenNotBlankPrint(discordBotEnvironment.getName(), "Bot")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getAuthor()) ? " created by " + discordBotEnvironment.getAuthor() + "." : "")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getVersion()) ? " Version: " + discordBotEnvironment.getVersion() : "")
                    + (StringUtils.isNotBlank(discordBotEnvironment.getDescription()) ? System.lineSeparator() + discordBotEnvironment.getDescription() : ""));
            }

            embed = embedBuilder.build();
            getContext().getChannel().sendMessage(embed).queue();
        }

        return embed;
    }

    private String whenNotBlankPrint(String toPrint, String fallBack) {
        if (StringUtils.isNotBlank(toPrint)) {
            return toPrint;
        } else {
            return fallBack;
        }
    }
}
