package assign251_2;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.slf4j.event.LoggingEvent;

import java.util.List;

public class MemAppender {

    public static MemAppender getInstance(List<LogEvent> injectedList, Object o, int i) {
        return null;
    }

    public static MemAppender getInstance() {
        return null;
    }

    public List<LoggingEvent> getCurrentLogs() {
        return List.of();
    }

    public void append(Log4jLogEvent build) {
    }

    public int getDiscardedLogCount() {
        return 0;
    }

    public void getEventStrings() {
    }

    public void printLogs() {
    }
}
