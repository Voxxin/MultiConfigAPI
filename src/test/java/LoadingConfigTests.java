
import com.github.voxxin.api.config.ConfigManager;
import com.github.voxxin.api.config.option.ConfigOption;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class LoadingConfigTests {

    private static final ConfigManager MANAGER = new ConfigManager(new File("run/tests"));

    public LoadingConfigTests() {
        ConfigOption option = new ConfigOption.Builder("category.1")
                .addBoolean(Options.booleanConfigOption)
                .addNumber(Options.numberValueOption)
                .build();

        MANAGER.addOption(option);
        MANAGER.runOptions();
    }

    @Test
    void bool() {
        assertTrue(Options.booleanConfigOption.getValue());
    }

    @Test
    void number() {
        assertEquals(0, Options.numberValueOption.getValueAsInteger());
    }

    @Test
    void setNumber() {
        Options.numberValueOption.setValue(2);
        assertEquals(2, Options.numberValueOption.getValueAsInteger());
    }
}
