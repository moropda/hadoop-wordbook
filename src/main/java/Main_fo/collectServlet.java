package Main_fo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/collectServlet")
public class collectServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "D:\\idea\\untitled2\\data\\input\\collect.txt";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Set request encoding
        response.setCharacterEncoding("UTF-8"); // Set response encoding
        response.setContentType("text/plain; charset=UTF-8"); // Set content type with UTF-8

        String word = request.getParameter("word");
        System.out.println("collectServlet: " + word);

        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(word + System.lineSeparator());
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            System.out.println(e.getMessage());
        }
    }
}
