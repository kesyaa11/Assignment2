package assign251_2;

import org.junit.jupiter.api.*;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.LogEvent;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

/* Test class for MemAppender using TDD before implementing MemAppender.java */
public class MemAppenderTest {
    private MemAppender appender;

    //setup before each test
    @BeforeEach
    public void setup(){
        appender = MemAppender.getInstance(new ArrayList<>(), null, 5);
    }

    //getInstance() returns same object
    @Test
    public void testSinglePattern(){
        MemAppender appender2 = MemAppender.getInstance();
        assertSame(appender, appender2, "MemAppender should follow singleton pattern");
    }

    //for dependency injection
    @Test
    public void testDependencyInjection(){
        List<LogEvent> injectedList = new LinkedList<>();
        MemAppender injectedAppender = MemAppender.getInstance(injectedList, null, 5);
        injectedAppender.getCurrentLogs();
    }

    //for max size behaviour
    @Test
    public void testMaxSizeLimitAndDiscardCount(){
        for (int i=0; i<10; i++){
            appender.append(Log4jLogEvent.newBuilder().setMessage(()-> "Msg " + i).build());
        }
        assertEquals(5, appender.getCurrentLogs().size(), "Logs should be capped at max size");
        assertEquals(5, appender.getDiscardedLogCount(), "Discarded logs should be tracked");
    }

    //for layout-generated string outputs
    @Test
    void testEventStringsWithoutLayout() {
        assertThrows(IllegalStateException.class, appender::getEventStrings,
                "Should throw error if layout not supplied");
    }

    //for clear and printLogs() functions
    @Test
    void testPrintLogsClearsMemory() {
        appender.append(Log4jLogEvent.newBuilder().setMessage(() -> "Test").build());
        appender.printLogs(); //prints and clears
        assertTrue(appender.getCurrentLogs().isEmpty(), "Logs should clear after printLogs()");
    }
}
