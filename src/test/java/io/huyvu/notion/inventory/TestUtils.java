package io.huyvu.notion.inventory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URISyntaxException;
import java.net.URL;

public class TestUtils {
    public static String readFileAsString(String resourcePath) throws IOException, URISyntaxException {
        // Use ClassLoader to get the resource URL
        URL resourceUrl = ClassLoader.getSystemResource(resourcePath);
        if (resourceUrl == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }

        // Convert URL to Path, handling spaces and special characters
        Path path = Paths.get(resourceUrl.toURI());
        return new String(Files.readAllBytes(path));
    }
}