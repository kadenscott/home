package sh.kaden.home;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import io.javalin.http.Context;
import io.javalin.plugin.rendering.FileRenderer;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import sh.kaden.home.config.ApplicationConfig;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;

public class MarkdownRenderer implements FileRenderer {

    private final ApplicationConfig config;
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();
    private final PebbleEngine engine;
    public MarkdownRenderer(final ApplicationConfig config) {
        this.config = config;
        this.engine = new PebbleEngine.Builder()
                .loader(new TemplateLoader(config))
                .autoEscaping(false)
                .build();
    }

    @Override
    public String render(final String filePath,
                         final Map<String, Object> model,
                         final Context context) throws Exception {
        final String content = Files.readString(new File(new File(this.config.pagePath).getAbsolutePath(), filePath).toPath());
        final String rendered =  renderer.render(parser.parse(content));
        final PebbleTemplate template = engine.getTemplate(this.config.pageTemplate);
        final StringWriter writer = new StringWriter();
        template.evaluate(writer, Map.of("content", rendered), Locale.CANADA);
        return writer.toString();
    }
}
