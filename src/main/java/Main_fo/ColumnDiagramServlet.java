package Main_fo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/column_diagramServlet")
public class ColumnDiagramServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<DataPoint> dataPoints = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("D:\\idea\\untitled2\\data\\input\\first20.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    String word = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    dataPoints.add(new DataPoint(word, count));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file："+e.getMessage());
        }

        out.println("[");
        for (int i = 0; i < dataPoints.size(); i++) {
            DataPoint point = dataPoints.get(i);
            out.printf("{\"word\":\"%s\", \"count\":%d}", point.word, point.count);
            if (i < dataPoints.size() - 1) {
                out.print(", ");
            }
        }
        out.println("]");
    }

    private static class DataPoint {
        String word;
        int count;

        DataPoint(String word, int count) {
            this.word = word;
            this.count = count;
        }
    }
}
