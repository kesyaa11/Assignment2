package assign251_2;

import org.junit.jupiter.api.Test;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import static org.junit.jupiter.api.Assertions.*;

/* Test class for VelocityLayout formatting */
public class VelocityLayoutTest {
    @Test
    void testPatternFormatting() {
        VelocityLayout layout = new VelocityLayout("[$p] $m$n");
        String formatted = layout.toSerializable(
                Log4jLogEvent.newBuilder().setMessage(() -> "Hello!").setLevel(org.apache.logging.log4j.Level.INFO).build()
        );

        assertTrue(formatted.contains("INFO"), "Should include log level");
        assertTrue(formatted.contains("Hello!"), "Should include message");
    }
}
