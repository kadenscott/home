package sh.kaden.home;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import io.javalin.http.Context;
import io.javalin.plugin.rendering.FileRenderer;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import sh.kaden.home.config.ApplicationConfig;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

public class PebbleRenderer implements FileRenderer {

    private final ApplicationConfig config;
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();
    private final PebbleEngine engine;
    public PebbleRenderer(final ApplicationConfig config) {
        this.config = config;
        this.engine = new PebbleEngine.Builder()
                .loader(new PageLoader(this.config))
                .autoEscaping(false)
                .build();
    }

    @Override
    public String render(final String filePath,
                         final Map<String, Object> model,
                         final Context context) throws Exception {
        final PebbleTemplate template = engine.getTemplate(this.config.pageTemplate);
        final StringWriter writer = new StringWriter();
        template.evaluate(writer, model, Locale.CANADA);
        return writer.toString();
    }
}
