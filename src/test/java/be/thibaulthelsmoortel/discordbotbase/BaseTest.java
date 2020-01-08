package be.thibaulthelsmoortel.discordbotbase;

import com.github.thibstars.chatbotengine.provider.discord.DiscordProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @author Thibault Helsmoortel
 */
@SpringBootTest
public abstract class BaseTest {

    @MockBean
    private DiscordProvider discordProvider;

}
