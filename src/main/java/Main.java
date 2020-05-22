import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        Path mp3Directory = getDirectory(args);

        FileManager fileManager = new FileManager(mp3Directory);
        fileManager.fillFilePaths();
        //fileManager.printManager();
        fileManager.convertPathsToSongs();
        fileManager.printSongs();
        Database database = new Database();
        database.clearTable();
        database.setUp(fileManager.getSongs());
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
