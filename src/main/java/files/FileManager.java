package files;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import files.Song;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {
    private final Path mp3Directory;
    private List<Path> filePaths;
    private List<Song> songs;

    public FileManager(Path mp3Directory) {
        this.mp3Directory = mp3Directory;
        filePaths = new ArrayList<>();
        songs = new ArrayList<>();
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

    public void convertPathsToSongs() {
        songs = filePaths.stream().map(path -> {
            try {
                Mp3File mp3file = new Mp3File(path);
                ID3v2 id3 = mp3file.getId3v2Tag();
                String artist = id3.getArtist() == null ? "unknown" : id3.getArtist();
                String year = id3.getYear() == null ? "unknown" : id3.getYear();
                String album = id3.getAlbum() == null ? "unknown" : id3.getAlbum();
                String title = id3.getTitle() == null ? "unknown" : id3.getTitle();
                return new Song(artist, year, album, title);
            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                throw new IllegalStateException(e);
            }
        }).collect(Collectors.toList());
    }

    public void printManager() {
        System.out.println("Directory: " + mp3Directory);
        System.out.println("Files:");
        for (Path filePath: filePaths) {
            System.out.println(filePath);
        }
    }

    public void printSongs() {
        System.out.println("Songs:");
        for (Song song : songs) {
            System.out.println('"' + song.getTitle() + '"' + " by " + song.getArtist());
        }
    }

    public Path getMp3Directory() {
        return mp3Directory;
    }

    public List<Path> getFilePaths() {
        return filePaths;
    }

    public List<Song> getSongs() {
        return songs;
    }
}
