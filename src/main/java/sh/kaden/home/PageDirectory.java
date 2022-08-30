package sh.kaden.home;

import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PageDirectory {

    private final File file;
    private final Map<String, Page> pages;

    public PageDirectory(final File file) throws IOException {
        this.file = file;
        this.pages = new HashMap<>();
        System.out.println("File: "+file.getAbsolutePath());
        Files.walkFileTree(Path.of(file.toURI()), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                System.out.println("File: " + path);
                final Page page = new Page(new File(path.toUri()));
                pages.put(new File("./pages").getAbsoluteFile().toPath().relativize(path).toString(), page);
                pages.entrySet().forEach(entry -> {
                    System.out.println("Path: "+entry.getKey());
                });
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public Optional<Page> getPage(final String path) {
        return Optional.ofNullable(this.pages.get(path));
    }

}
