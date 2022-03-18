import net.thesimpleteam.jrevolt.utils.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void wordUpperCase() {
        assertEquals("HelloWorld", StringUtils.wordUpperCase("HELLO_WORLD"));
    }

}
