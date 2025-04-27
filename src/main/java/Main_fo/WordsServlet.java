package Main_fo;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/wordsServlet")
public class WordsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String filePath = "D:\\idea\\untitled2\\data\\input\\translate.txt"; // 替换为实际路径
        List<String> wordsList = new ArrayList<>();
        List<String> meaningsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" "); // 假设用空格分隔
                if (parts.length == 2) {
                    wordsList.add(parts[0].trim());
                    meaningsList.add(parts[1].trim());
                }
            }
        }

        // 设置响应内容
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 创建 JSON 对象
        String jsonResponse = "{\"wordsArray\": " + new Gson().toJson(wordsList) + ", " +
                "\"WordsMeaning\": " + new Gson().toJson(meaningsList) + "}";

        response.getWriter().write(jsonResponse);
    }
}

