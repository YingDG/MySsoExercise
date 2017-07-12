package yingdg.exercise.sso.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by yingdg on 2017/6/8.
 */
@WebServlet(urlPatterns = "/ticket")
public class Ticket extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ticket = req.getParameter("ticket");
        String username = TokenMap.TICKET_AND_NAME.get(ticket);
        TokenMap.TICKET_AND_NAME.remove(ticket);
        PrintWriter writer = resp.getWriter();
        writer.write(username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
