package com.xiaolu.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 功能说明：websocket连接的拦截器
 * 有两种方式
 *          一种是实现接口HandshakeInterceptor，实现beforeHandshake和afterHandshake函数
 *          一种是继承HttpSessionHandshakeInterceptor，重载beforeHandshake和afterHandshake函数
 * 我这里是参照spring官方文档中的继承HttpSessionHandshakeInterceptor的方式
 * Created by chinaD on 2018/1/5.
 */
public class WebSocketSessionHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    Logger logger = LoggerFactory.getLogger(WebSocketSessionHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

        if (getSession(request) != null){
            ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletServerHttpRequest.getServletRequest();
            Map param = httpServletRequest.getParameterMap();
            attributes.put("user_id",httpServletRequest.getParameter("user_id"));
        }

        super.beforeHandshake(request, response, wsHandler, attributes);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }
}
