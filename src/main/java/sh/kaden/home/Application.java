package sh.kaden.home;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.FileLoader;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.markdown.JavalinCommonmark;
import io.javalin.plugin.rendering.template.JavalinPebble;
import org.commonmark.renderer.html.HtmlRenderer;
import org.jetbrains.annotations.NonNls;
import sh.kaden.home.config.ApplicationConfig;

import java.util.Map;

public class Application {

    public static void main(final String[] args) {
        new Application();
    }

    private final ApplicationConfig config;

    public Application() {
        this.config = new ApplicationConfig(
                "./templates",
                "./pages"
        );

        Javalin
                .create(config -> {
                    this.registerRenderers(config);
                })
                .get("/", this::handleIndex)
                .get("/projects", ctx -> ctx.render("projects.html"))
                .start(7070);

    }

    private void handleIndex(final Context ctx) {
        this.renderFile("index.md", ctx);
    }

    private void registerRenderers(final JavalinConfig config) {
        config.addStaticFiles("/static", Location.CLASSPATH);
        JavalinRenderer.register(new MarkdownRenderer(this.config), ".md");
        JavalinRenderer.register(new PebbleRenderer(this.config), ".html");

    }

    private void renderFile(final String path,
                            final Context ctx) {
        try {
            ctx.render(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
