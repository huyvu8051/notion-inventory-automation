package io.huyvu.notion.inventory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtils {
    public static String readFileAsString(String resourcePath) throws IOException {
        // Use classloader to get the resource file
        Path path = Paths.get(ClassLoader.getSystemResource(resourcePath).getPath());
        return new String(Files.readAllBytes(path));
    }
}
