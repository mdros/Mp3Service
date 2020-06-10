import files.Database;
import files.FileManager;
import server.Server;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        Path mp3Directory = getDirectory();

        FileManager fileManager = new FileManager(mp3Directory);
        fileManager.fillFilePaths();
        fileManager.convertPathsToSongs();
        fileManager.printSongs();

        Database database = new Database();
        database.clearTable();
        database.setUp(fileManager.getSongs());

        Server server = new Server(8080);
        server.setup();
        server.start();
    }

    public static Path getDirectory() throws URISyntaxException {
        String directory =  "C:/Users/micha/Desktop/Muzyka";
        Path mp3Directory = Paths.get(directory); //Paths.get(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        if (!Files.exists(mp3Directory)) {
            throw new IllegalArgumentException("The specified directory does not exist : " + mp3Directory);
        }

        return mp3Directory;
    }
    
}
