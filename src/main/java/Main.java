import files.Database;
import files.FileManager;
import server.Server;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

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
        File jarDirectory = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        File parentDirectory = jarDirectory.getParentFile();
        Path mp3Path = parentDirectory.toPath();

        if (!Files.exists(mp3Path)) {
            throw new IllegalArgumentException("The specified directory does not exist : " + mp3Path);
        }

        return mp3Path;
    }
    
}
