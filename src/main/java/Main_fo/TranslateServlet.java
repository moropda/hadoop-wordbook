package Main_fo;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@WebServlet("/translateServlet")
public class TranslateServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    private static final Map<String, String> translationMap = new HashMap<>();

    static {
        Translate_1(translationMap);
    }

    static void Translate_1(Map<String, String> translationMap) {
        String filePath = "D:\\idea\\untitled2\\data\\input\\translate.txt";
        Pattern pattern = Pattern.compile("\\s*\\(.*?\\)\\s*|\\s+");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                String[] parts = pattern.split(line);

                if (parts.length >= 2) {
                    String key = parts[0].trim().toLowerCase(); // 转换为小写
                    StringBuilder valueBuilder = new StringBuilder();
                    for (int i = 1; i < parts.length; i++) {
                        if (!parts[i].isEmpty()) {
                            valueBuilder.append(parts[i].trim());
                            if (i < parts.length - 1) {
                                valueBuilder.append(" ");
                            }
                        }
                    }
                    translationMap.put(key, valueBuilder.toString());
                } else {
                    System.err.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String word = request.getParameter("word").toLowerCase(); // 转换为小写
        String translation = translationMap.getOrDefault(word, "未找到翻译");

        PrintWriter out = response.getWriter();
        out.println("<p id='translatedWord'>" + word + "</p>");
        out.println("<p id='translationMeaning'>" + translation + "</p>");
    }
}
