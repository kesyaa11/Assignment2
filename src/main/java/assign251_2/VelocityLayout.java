package assign251_2;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.velocity.*;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/* Class with Log4j Layout that formats logs using Velocity template
   $c - logger name
   $d - date
   $m - message
   $p - level
   $t - thread name
   $n - newline
 */
@Plugin(name = "VelocityLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE)
public class VelocityLayout extends AbstractStringLayout {

    private final VelocityEngine engine;
    private String pattern;


    public VelocityLayout(String pattern) {
        super(StandardCharsets.UTF_8); //specify charset required by AbstractStringLayout
        this.engine = new VelocityEngine();
        this.engine.init();
        this.pattern = (pattern != null) ? pattern : "[$p] $c $d: $m$n";
    }

    @Override
    public String toSerializable(LogEvent event) {
        VelocityContext ctx = new VelocityContext();
        ctx.put("c", event.getLoggerName());
        ctx.put("d", new Date(event.getTimeMillis()).toString());
        ctx.put("m", event.getMessage().getFormattedMessage());
        ctx.put("p", event.getLevel().toString());
        ctx.put("t", event.getThreadName());
        ctx.put("n", System.lineSeparator());

        StringWriter out = new StringWriter();
        engine.evaluate(ctx, out, "VelocityLayout", pattern);
        return out.toString();
    }

    public void setPattern(String newPattern) { this.pattern = newPattern; }

    public String getPattern() { return pattern; }
}
