package com.xiaolu.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by chinaD on 2017/11/24.
 */
public class LoginFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    private Set<String> prefixIignores = new HashSet<String>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String cp = filterConfig.getServletContext().getContextPath();
        String ignoresParam = filterConfig.getInitParameter("ignores");
        String[] ignoreArray = ignoresParam.split(",");
        for (String s : ignoreArray) {
            prefixIignores.add(cp + s);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取请求
        HttpServletRequest req = (HttpServletRequest) request;
        //获取响应
        HttpServletResponse resp = (HttpServletResponse) response;
        //获取Session
        HttpSession session  = req.getSession();

        String url =  req.getRequestURI();
        //获取请求路径
        String path = req.getContextPath();

        String login =  (String)session.getAttribute("login");
        if (!(canIgnore(req))){
            if( null == login || !login.equals("successLogin")){
                logger.debug("Interceptor：跳转到login页面！");
                //request.getRequestDispatcher("/WEB-INF/view/login.html").forward(request, response);
                resp.sendRedirect(path +"/login");
                return;
            }
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        prefixIignores = null;
    }

    private boolean canIgnore(HttpServletRequest request) {
        String url = request.getRequestURI();
        for (String ignore : prefixIignores) {
            if (url.startsWith(ignore) || url.equals("/")) {
                return true;
            }
        }
        return false;
    }
}
