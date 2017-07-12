package yingdg.exercise.sso.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * Created by yingdg on 2017/6/8.
 */
@WebFilter(urlPatterns = "/*")
public class Client1Filter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String ticket = request.getParameter("ticket");
        String url = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");

        if (null == username) {
            if (null != ticket && !"".equals(ticket)) {
                PostMethod postMethod = new PostMethod("http://localhost:8080/server/ticket");
                postMethod.addParameter("ticket", ticket);
                HttpClient httpClient = new HttpClient();
                try {
                    httpClient.executeMethod(postMethod);
                    username = postMethod.getResponseBodyAsString();
                    postMethod.releaseConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (null != username && !"".equals(username)) {
                    session.setAttribute("username", username);
                    filterChain.doFilter(request, response);
                } else {
                    response.sendRedirect("http://localhost:8080/server/index.jsp?service=" + url);
                }
            } else {
                response.sendRedirect("http://localhost:8080/server/index.jsp?service=" + url);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
