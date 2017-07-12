package yingdg.exercise.sso.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by yingdg on 2017/6/8.
 */
@WebServlet(urlPatterns = "/login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String service = req.getParameter("service");

        if ("admin".equals(username) && "admin".equals(password)) {
            Cookie cookie = new Cookie("sso", username);
            cookie.setPath("/");
            resp.addCookie(cookie);

            long time = System.currentTimeMillis();
            String timeString = username + time;
            TokenMap.TICKET_AND_NAME.put(timeString, username);

            if (null != service) {
                StringBuilder url = new StringBuilder();
                url.append(service);
                if (0 <= service.indexOf("?")) {
                    url.append("&");
                } else {
                    url.append("?");
                }
                url.append("ticket=").append(timeString);
                resp.sendRedirect(url.toString());
            } else {
                resp.sendRedirect("./index.jsp");
            }
        } else {
            resp.sendRedirect("./index.jsp?service=" + service);
        }
    }

}
