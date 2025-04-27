package Main_fo;

import wc.WordCountMain;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * &#064;author: Long
 * &#064;date: 2024/10/25
 */
@WebServlet(urlPatterns = "/Main")
public class Main extends HttpServlet {

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws IOException {
        System.out.println("hello world");
        resp.getWriter().write("hello doGet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("MainServlet");

        // 实例化 WordCountMain 并调用 B() 方法
        WordCountMain wc = new WordCountMain();
        try {
            wc.wordCount();
        } catch (InterruptedException | ClassNotFoundException e) {
            // 更好的异常处理，记录错误日志或者返回详细错误信息
            System.out.println("Error in WordCountMain.B(): " + e.getMessage());
        }


        // 实例化 FileSplit 并调用 D() 方法
        FileSplit fs1 = new FileSplit();
        fs1.D();

        // 重定向到指定页面
        resp.sendRedirect("/untitled2/design_and_show.html");
    }
}
