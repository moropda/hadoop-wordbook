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
@WebServlet(urlPatterns = "/texts")
public class TextServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> knownWords = new ArrayList<>();
        List<String> unknownWords = new ArrayList<>();
        System.out.println("TextServlet.doGet");
        // 使用绝对路径
        String filePath = "D:\\idea\\untitled2\\data\\ouput\\file_know_unknow.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\d+", "").trim(); // 去掉数字
                // 根据需要分配已知和未知词汇
                if (line.contains("已知")) {
                    knownWords.add(line.replace("已知", "").trim());
                } else {
                    unknownWords.add(line.replace("未知", "").trim());
                }
            }
        } catch (IOException e) {
            System.out.println("读取文件失败" + e.getMessage());
        }

        // 设置响应类型为 JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 使用 Gson 库将数据转为 JSON 格式
        String jsonResponse = new Gson().toJson(new ResponseData(knownWords, unknownWords));
        response.getWriter().write(jsonResponse);
    }

    private static class ResponseData {
        List<String> known;
        List<String> unknown;

        ResponseData(List<String> known, List<String> unknown) {
            this.known = known;
            this.unknown = unknown;
        }
    }
}

