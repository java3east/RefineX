package net.refinedsolution.util.file;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

/**
 * This class creates temporary files from a given path / string.
 * @author Java3east
 */
public class TempFileManager {
    public static Optional<String> fromFile(@NotNull String path, Function<String, String> modifier) {
        File file = new File(path);
        if (!file.exists())
            throw new IllegalArgumentException("File does not exist: " + path);
        if (!file.isFile())
            throw new IllegalArgumentException("Path is not a file: " + path);

        try {
            Scanner scanner = new Scanner(file);
            StringBuilder content = new StringBuilder();
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            String cont = modifier.apply(content.toString());

            String userDir = System.getProperty("user.dir");
            String tempPath = userDir + "/temp/";
            File tempDir = new File(tempPath);
            if (!tempDir.exists()) {
                tempDir.mkdirs();
            }
            File tempFile = new File(tempPath + file.getAbsolutePath().replace(userDir, ""));
            if (!tempFile.exists()) {
                if (!tempFile.getParentFile().exists()) {
                    tempFile.getParentFile().mkdirs();
                }
                tempFile.createNewFile();
            }
            System.out.println("Writing temp file: " + tempFile.getAbsolutePath());
            FileWriter writer = new FileWriter(tempFile);
            writer.write(cont);
            writer.close();
            return Optional.of(tempFile.getAbsolutePath().replace(userDir, ""));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<String> fromFile(@NotNull String path) {
        return fromFile(path, Function.identity());
    }
}
