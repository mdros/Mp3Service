import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Path mp3Directory = getDirectory(args);

        FileManager fileManager = new FileManager(mp3Directory);
        fileManager.fillFilePaths();
        fileManager.printManager();

    }

    public static Path getDirectory(String[] args) {
        /*if (args.length != 1) {
            throw new IllegalArgumentException("You need to specify a valid mp3 directory");
        }*/

        String directory = /*args[0]*/ "C:/Users/micha/Desktop/Muzyka";
        Path mp3Directory = Paths.get(directory);

        if (! Files.exists(mp3Directory)) {
            throw new IllegalArgumentException("The specified directory does not exist : " + mp3Directory);
        }

        return mp3Directory;
    }
    
}
