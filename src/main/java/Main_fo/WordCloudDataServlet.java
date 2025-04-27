package Main_fo;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * &#064;author: Long
 * &#064;date: 2024/10/25
 */
@WebServlet("/wordcloudData")
public class WordCloudDataServlet extends HttpServlet {

    private static final String FILE_PATH = "D:\\idea\\untitled2\\data\\ouput\\first200.txt"; // 更新为实际路径

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        System.out.println("wordcloudDataServlet");

        List<WordData> wordDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 2) {
                    String word = parts[0];
                    int count = Integer.parseInt(parts[1]);
                    wordDataList.add(new WordData(word, count));
                }
            }
        } catch (IOException e) {
            System.out.println("读取文件失败" + e.getMessage());
        }

        Gson gson = new Gson();
        String json = gson.toJson(wordDataList);
        response.getWriter().write(json);
    }

    private record WordData(String name, int value) {
    }
}
