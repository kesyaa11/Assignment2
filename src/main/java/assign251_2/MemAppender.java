package assign251_2;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Custom log4j Appender class
   -enforces singleton design patterns
   -support dependency injection
   -configurable maxSize
   -tracks discarded logs
   -provides unmodificable access to current logs
 */
public class MemAppender extends AbstractAppender {
    private static MemAppender instance;

    private List<LogEvent> logList;
    private long discardedLogCount = 0;
    private int maxSize = 1000;

    private MemAppender(String name, List<LogEvent> list, Layout<?> layout, int maxSize) {
        super(name, null, layout, true);
        this.logList = (list != null) ? list : new ArrayList<>();
        this.maxSize = maxSize;
    }

    //accessor for singleton
    public static MemAppender getInstance() {
        if (instance == null) {
            instance = new MemAppender("MemAppender", new ArrayList<>(), null, 1000);
        }
        return instance;
    }

    //overloaded singleton with dependency injection
    public static synchronized MemAppender getInstance(List<LogEvent> list, Layout<?> layout, int maxSize) {
        if (instance == null) {
            instance = new MemAppender("MemAppender", list, layout, maxSize);
        }
        return instance;
    }

    //append log while respecting max size
    @Override
    public void append(LogEvent event) {
        if (logList.size() >= maxSize) {
            logList.remove(0);
            discardedLogCount++;
        }
        logList.add(event.toImmutable());
    }

    public List<LogEvent> getCurrentLogs() {
        return Collections.unmodifiableList(logList);
    }

    public List<String> getEventStrings() {
        if (getLayout() == null) {
            throw new IllegalStateException("No layout is set for this appender.");
        }

        List<String> formatted = new ArrayList<>();
        for (LogEvent e : logList) {
            formatted.add(getLayout().toSerializable(e).toString());
        }
        return Collections.unmodifiableList(formatted);
    }

    public void printLogs() {
        if (getLayout() == null) {
            throw new IllegalStateException("Cannot print logs without layout.");
        }

        for (LogEvent e : logList) {
            System.out.println(getLayout().toSerializable(e));
        }
        logList.clear();
    }

    //clear manually
    public void clear() {
        logList.clear();
    }

    public long getDiscardedLogCount() { return discardedLogCount; }

    public void setMaxSize(int size) { this.maxSize = size; }

    public int getMaxSize() { return maxSize; }
}
