package sh.kaden.home;

import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import io.javalin.Javalin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class Entrypoint {

    public static void main(final String[] args) {
        new Entrypoint();
    }

    private final Javalin javalin;
    private final MutableDataSet markdownOptions;
    private final Parser parser;
    private final HtmlRenderer renderer;

    public Entrypoint() {
        this.markdownOptions = new MutableDataSet();
        this.markdownOptions.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));
        this.parser = Parser.builder(this.markdownOptions)
                .build();
        this.renderer = HtmlRenderer
                .builder(this.markdownOptions)
                .build();

        final Node document = this.parser.parse("""
# kaden.sh

Welcome to my site.

## Music

TODO

## Cool Links

TODO
""");

        this.javalin = Javalin.create()
                .get("/", ctx -> {
                    ctx.render("/index.md");
                })
                .start(7070);

    }

}
