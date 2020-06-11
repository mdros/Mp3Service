package pl.michaldros.server;

import pl.michaldros.files.Database;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.Map;

public class SongServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        File jarDirectory = null;
        try {
            jarDirectory = new File(Database.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        String propertiesPath = jarDirectory.getParentFile().getAbsolutePath();
        InputStream is = new FileInputStream(propertiesPath + "/config.yml");
        Yaml yaml = new Yaml();
        Map<String, String> config = yaml.load(is);
        String DB_URL = config.get("DB_URL");
        String USER = config.get("DB_USER");
        String PASS = config.get("DB_PASS");

        StringBuilder builder = new StringBuilder();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            Statement stmt = conn.createStatement();
            ResultSet song = stmt.executeQuery("SELECT * FROM songs");

            while (song.next()) {
                builder.append("<tr class=\"table\">")
                        .append("<td>").append(song.getString("year")).append("</td>")
                        .append("<td>").append(song.getString("artist")).append("</td>")
                        .append("<td>").append(song.getString("album")).append("</td>")
                        .append("<td>").append(song.getString("title")).append("</td>")
                        .append("</tr>");
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        String string = "<html><h1>Your Songs</h1><table><tr><th>Year</th><th>Artist</th><th>Album</th><th>Title</th></tr>" + builder.toString() + "</table></html>";
        resp.getWriter().write(string);

    }
}
