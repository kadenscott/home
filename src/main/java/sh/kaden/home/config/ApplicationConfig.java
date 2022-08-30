package sh.kaden.home.config;

public class ApplicationConfig {

    public final String templatePath;
    public final String pagePath;
    public final String pageTemplate = "page.peb";

    public ApplicationConfig(final String templatePath,
                             final String pagePath) {
        this.templatePath = templatePath;
        this.pagePath = pagePath;
    }
}
