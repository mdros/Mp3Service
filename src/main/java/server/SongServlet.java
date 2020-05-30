package server;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class SongServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/songs", "postgres", "ReedEs123")) {
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
