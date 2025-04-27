package Main_fo;
import wc.WordCountMain;

import java.io.IOException;

public class Main_test {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        WordCountMain w = new WordCountMain();
        w.wordCount();

        FileSplit  fs = new FileSplit();
        fs.D();

    }
}
