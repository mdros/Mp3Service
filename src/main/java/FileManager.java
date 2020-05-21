import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private Path mp3Directory;
    private List<Path> filePaths;

    public FileManager(Path mp3Directory) {
        this.mp3Directory = mp3Directory;
        filePaths = null;
    }

    public void fillFilePaths() throws IOException {
        List<Path> mp3Paths = new ArrayList<>();

        try (DirectoryStream<Path> paths = Files.newDirectoryStream(mp3Directory, "*.mp3")) {
            paths.forEach(p -> {
                System.out.println("Found : " + p.getFileName().toString());
                mp3Paths.add(p);
            });
        }
        filePaths = mp3Paths;
    }

    public void printManager() {
        System.out.println("Directory: " + mp3Directory);
        System.out.println("Files:");
        for (Path filePath: filePaths) {
            System.out.println(filePath);
        }

    }

    public Path getMp3Directory() {
        return mp3Directory;
    }

    public List<Path> getFilePaths() {
        return filePaths;
    }

}
