package wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;


public class WordCountMain {
    public  void A() {
        // 输入文件路径
        String inputFilePath = "D:\\idea\\untitled2\\data\\input\\en_book.txt";  // 请替换为你的文件路径
        String outputFilePath = "D:/idea/untitled2/data/ouput/output.txt"; // 输出文件路径

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
             FileWriter writer = new FileWriter(outputFilePath)) {

            String line;
            while ((line = br.readLine()) != null) {
                // 用正则表达式去除所有非字母数字的字符，并将多个空格替换为单个空格
                String cleanedLine = line.replaceAll("[^a-zA-Z\\s]", "").replaceAll("\\s+", " ");
                writer.write(cleanedLine + "\n");
            }
            System.out.println("文件处理完成！结果已写入 " + outputFilePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void wordCount() throws IOException, InterruptedException, ClassNotFoundException {
        A();
        Job job = Job.getInstance(new Configuration());
        job.setJarByClass(WordCountMain.class);

        job.setMapperClass(WordCountMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(WordCountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        File folder = new File("D:\\idea\\untitled2\\data\\ouput\\output1");
        FileInputFormat.setInputPaths(job, new Path("D:\\idea\\untitled2\\data\\ouput\\output.txt"));
        if (folder.exists()){
            deleteDir(folder);
            FileOutputFormat.setOutputPath(job, new Path("D:\\idea\\untitled2\\data\\ouput\\output1"));
        }else {
            FileOutputFormat.setOutputPath(job, new Path("D:\\idea\\untitled2\\data\\ouput\\output1"));
        }

//        FileInputFormat.setInputPaths(job, new Path("hdfs://192.168.248.128:9000//input//en_book.txt"));
//        FileOutputFormat.setOutputPath(job, new Path("hdfs://192.168.248.128:9000//output//output2"));
//        if (folder.exists()){
//            deleteDir(folder);
//            FileOutputFormat.setOutputPath(job, new Path("D:\\idea\\untitled2\\data\\ouput\\output1"));
//        }else {
//            FileOutputFormat.setOutputPath(job, new Path("D:\\idea\\untitled2\\data\\ouput\\output1"));
//        }

        boolean result = job.waitForCompletion(true);
        deleteDir(new File("D:\\idea\\untitled2\\data\\ouput\\output.txt"));
        B();

        if (!result) {
            System.exit(1);
        }

    }

    public void B() {
        String inputFilePath = "D:\\idea\\untitled2\\data\\ouput\\output1\\part-r-00000"; // 输入文件路径
        String outputFilePath = "D:\\idea\\untitled2\\data\\ouput\\output.txt"; // 输出文件路径

        // 定义常用单词列表
        Set<String> commonWords = new HashSet<>(Arrays.asList(
                "the", "be", "to", "of", "and", "a", "in", "that", "have", "I",
                "it", "for", "not", "on", "with", "he", "as", "you", "do", "at",
                "this", "but", "his", "by", "from", "they", "we", "say", "her",
                "she", "or", "an", "will", "my", "one", "all", "would", "there",
                "their", "what", "so", "up", "out", "if", "about", "who", "get",
                "which", "go", "me", "when", "make", "can", "like", "time", "no",
                "just", "him", "know", "take", "people", "into", "your", "good",
                "some", "could", "them", "see", "other", "than", "then", "now",
                "look", "only", "come", "its", "over", "think", "also", "back",
                "after", "use", "two", "how", "our", "work", "first", "well",
                "way", "even", "new", "want", "because", "any", "these", "give",
                "day", "most", "us","had","his","him","her","said","Mr","is","she",
                "I","i","","was","Mr","mr",""));

        List<String[]> lines = new ArrayList<>();
        Pattern numberPattern = Pattern.compile("\\d+"); // 匹配数字的正则表达式

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+"); // 分割行，假设每行是以空格分隔的
                if (parts.length == 2) {
                    String word = parts[0].toLowerCase(); // 获取单词部分
                    String count = parts[1]; // 获取数字部分

                    // 过滤掉常用单词
                    if (!commonWords.contains(word) && numberPattern.matcher(count).matches()) {
                        lines.add(parts); // 将符合条件的行加入列表
                    }
                }
            }

            // 按照数字进行降序排序
            lines.sort((o1, o2) -> Integer.parseInt(o2[1]) - Integer.parseInt(o1[1]));

            // 写入排序后的结果到输出文件
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath))) {
                for (String[] entry : lines) {
                    bw.write(entry[0] + " " + entry[1]);
                    bw.newLine();
                }
            }



            System.out.println("处理完成！结果已保存到 " + outputFilePath);

        } catch (IOException e) {
            System.out.println("发生错误：" + e.getMessage());
        }
    }

    public static boolean deleteDir(File file){
        if (file.isDirectory()){
            File[] files = file.listFiles();
            if (files != null) {
                for(File f:files){
                    boolean b = deleteDir(f);
                    if (!b){
                        deleteDir(f);
                    }
                }
            }
        }
        return file.delete();
    }
}
