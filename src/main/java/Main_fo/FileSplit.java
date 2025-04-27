package Main_fo;

import wc.WordCountMain;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileSplit {
    private static final Map<String, String> translationMap = new HashMap<>();

    public void D() {
        // 原始文件路径
        String inputFilePath = "D:\\idea\\untitled2\\data\\ouput\\output.txt";
        // 前200行保存的文件路径
        String first200FilePath = "D:\\idea\\untitled2\\data\\ouput\\first200.txt";
        // 处理后文件的路径
        String fileKnowUnknownPath = "D:\\idea\\untitled2\\data\\ouput\\file_know_unknow.txt";

        // 初始化翻译库
        initializeTranslationMap();

        splitAndExtract(inputFilePath, first200FilePath, fileKnowUnknownPath);

        System.out.println("文件已经成功生成");

        WordCountMain.deleteDir(new File("D:\\idea\\untitled2\\data\\ouput\\output1"));
    }

    public void initializeTranslationMap() {
        TranslateServlet.Translate_1(translationMap);
    }

    public static void splitAndExtract(String inputFilePath, String first200FilePath, String fileKnowUnknownPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath))) {
            // 保存前200行
            saveFirst200Lines(reader, first200FilePath);
            // 处理并保存每一行
            saveProcessedLines(reader, fileKnowUnknownPath);
        } catch (IOException e) {
            System.out.println("文件读取出错");
        }
    }

    private static void saveFirst200Lines(BufferedReader reader, String outputFilePath) throws IOException {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null && count < 200) {
                writer.write(line + "\n");
                count++;
            }
        }
    }

    private static void saveProcessedLines(BufferedReader reader, String outputFilePath) throws IOException {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String processedLine = line.replaceAll("\\d+", "").trim(); // 去掉数字并去除多余空格
                // 获取翻译
                String translation = translationMap.getOrDefault(processedLine, "翻译出错，请更新");
                if (count < 4333) {
                    writer.write(processedLine + " 已知\t" + translation + "\n");
                } else {
                    writer.write(processedLine + " 未知\t" + translation + "\n");
                }
                count++;
            }
        }
    }
}
