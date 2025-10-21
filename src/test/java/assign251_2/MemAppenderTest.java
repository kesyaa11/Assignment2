package assign251_2;

import org.apache.logging.log4j.message.SimpleMessage;
import org.junit.jupiter.api.*;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.LogEvent;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/* Test class for MemAppender using TDD before implementing MemAppender.java */
public class MemAppenderTest {
    private MemAppender appender;

    @BeforeEach
    public void setup(){
        appender = MemAppender.getInstance(new ArrayList<>(), null, 5);
    }

    @Test
    public void testSinglePattern(){
        MemAppender appender2 = MemAppender.getInstance();
        assertSame(appender, appender2, "MemAppender should follow singleton pattern");
    }

    @Test
    public void testDependencyInjection(){
        List<LogEvent> injectedList = new LinkedList<>();
        MemAppender injectedAppender = MemAppender.getInstance(injectedList, null, 5);
        injectedAppender.getCurrentLogs();
    }

    @Test
    public void testMaxSizeLimitAndDiscardCount(){
        for (int i=0; i<10; i++) {
            final int index = i;
            appender.append(Log4jLogEvent.newBuilder().setMessage(new SimpleMessage("Msg " + index)).build());
        }
        assertEquals(5, appender.getCurrentLogs().size(), "Logs should be capped at max size");
        assertEquals(5, appender.getDiscardedLogCount(), "Discarded logs should be tracked");
    }

    @Test
    void testEventStringsWithoutLayout() {
        assertThrows(IllegalStateException.class, appender::getEventStrings,
                "Should throw error if layout not supplied");
    }

    @Test
    void testPrintLogsClearsMemory() {
        appender.append(Log4jLogEvent.newBuilder().setMessage(new SimpleMessage("Test")).build());
        appender.printLogs(); //prints and clears
        assertTrue(appender.getCurrentLogs().isEmpty(), "Logs should clear after printLogs()");
    }
}
