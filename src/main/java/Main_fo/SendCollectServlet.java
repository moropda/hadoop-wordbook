package Main_fo;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/SendCollectServlet")
public class SendCollectServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "D:\\idea\\untitled2\\data\\input\\collect.txt";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> words = new ArrayList<>();
        request.setCharacterEncoding("UTF-8"); // 设置请求的字符编码
        response.setContentType("text/html;charset=UTF-8");

        System.out.println("SendCollectServlet");
        Path path = Paths.get(FILE_PATH);
        List<String> lines = Files.readAllLines(path);
        // 使用 LinkedHashSet 去重并保持原始顺序
        Set<String> uniqueLines = new LinkedHashSet<>(lines);
        // 将去重后的内容覆盖写回原始文件
        Files.write(path, uniqueLines);

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            System.out.println("读取文件失败" + e.getMessage());
        }

        // 使用 Gson 库将单词列表转换为 JSON 字符串
        Gson gson = new Gson();
        String json = gson.toJson(words);

        // 设置响应类型为 JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }
}
