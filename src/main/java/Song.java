public class Song {
    private final String artist;
    private final String year;
    private final String album;
    private final String title;

    public Song(String artist, String year, String album, String title) {
        this.artist = artist;
        this.year = year;
        this.album = album;
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public String getYear() {
        return year;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }
}
