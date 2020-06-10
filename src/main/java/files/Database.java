package files;

import org.yaml.snakeyaml.Yaml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class Database {
    private Connection conn;
    private Statement stmt;

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static String DB_URL;
    private static String USER;
    private static String PASS;

    public Database() throws IOException, URISyntaxException {
        File jarDirectory = new File(Database.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        String propertiesPath = jarDirectory.getParentFile().getAbsolutePath();
        InputStream is = new FileInputStream(propertiesPath + "/config.yml");
        Yaml yaml = new Yaml();
        Map<String, String> config = yaml.load(is);
        DB_URL = config.get("DB_URL");
        USER = config.get("DB_USER");
        PASS = config.get("DB_PASS");
        is.close();
        conn = null;
        stmt = null;
    }

    public void setUp(List<Song> songs) {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Opened database successfully.");

            System.out.println("Creating table...");
            stmt = conn.createStatement();
            String sql = createTableStmt();
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Table created successfully.");

            System.out.println("Adding songs...");

            for (Song song : songs) {
                stmt = conn.createStatement();
                sql = "INSERT INTO SONGS (ARTIST, YEAR, ALBUM, TITLE) "
                        + "VALUES (\'" + song.getArtist() + "\',\'" + song.getYear() + "\',\'" + song.getAlbum() + "\',\'" + song.getTitle() + "\');";
                stmt.executeUpdate(sql);
                System.out.println("Song added.");
            }

            stmt.close();

            System.out.println("Inserted [" + songs.size() + "] records into the database");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
        }
    }

    private String createTableStmt() {
        return "CREATE TABLE IF NOT EXISTS SONGS" +
                "(ID            SERIAL PRIMARY KEY      NOT NULL," +
                " ARTIST        varchar(255)            NOT NULL, " +
                " YEAR          varchar(255)            NOT NULL, " +
                " ALBUM         varchar(255)            NOT NULL, " +
                " TITLE         varchar(255)            NOT NULL)";
    }

    public void clearTable() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Opened database successfully.");

            stmt = conn.createStatement();
            String sql = "DELETE FROM SONGS";
            stmt.executeUpdate(sql);
            sql = "ALTER SEQUENCE songs_id_seq RESTART with 1";
            stmt.executeUpdate(sql);
            stmt.close();
            System.out.println("Cleared table: SONGS");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

}
