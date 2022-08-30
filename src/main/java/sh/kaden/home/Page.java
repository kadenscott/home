package sh.kaden.home;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class Page {

    private final File file;

    public Page(final File file) {
        this.file = file;
    }

    public File file() {
        return this.file;
    }

}
