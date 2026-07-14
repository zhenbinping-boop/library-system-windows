package com.library.filter;

import com.alibaba.fastjson2.JSON;
import com.library.common.Result;
import com.library.entity.Users;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录过滤器
 * 拦截未登录的请求
 */
@WebFilter(urlPatterns = "/api/*")
public class LoginFilter implements Filter {
    
    // 白名单：不需要登录就能访问的接口
    private static final String[] WHITE_LIST = {"/api/user/login", "/api/user/logout","/api/user/register","/api/borrow/fineInfo","/api/readertype/list"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();

        // 白名单放行
        for (String white : WHITE_LIST) {
            if (uri.equals(white)) {
                chain.doFilter(request, response);
                return;
            }
        }

        // 检查是否登录
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.setContentType("application/json;charset=UTF-8");
            resp.setStatus(401);
            resp.getWriter().write(JSON.toJSONString(Result.error(401, "未登录，请先登录")));
            return;
        }

        // 权限校验：普通用户不能访问管理接口
        Users user = (Users) session.getAttribute("user");
        if (user.getUtype() == 2) {
            if (uri.startsWith("/api/user/list") || uri.startsWith("/api/user/add") ||
                uri.startsWith("/api/user/delete") || uri.startsWith("/api/book/add") ||
                uri.startsWith("/api/book/update") || uri.startsWith("/api/book/delete") ||
                uri.startsWith("/api/reader/add") || uri.startsWith("/api/reader/update") ||
                uri.startsWith("/api/reader/delete") || uri.startsWith("/api/booktype/add") ||
                uri.startsWith("/api/booktype/update") || uri.startsWith("/api/booktype/delete") ||
                uri.startsWith("/api/readertype/add") || uri.startsWith("/api/readertype/update") ||
                uri.startsWith("/api/readertype/delete") || uri.startsWith("/api/borrow/all")) {
                resp.setContentType("application/json;charset=UTF-8");
                resp.setStatus(403);
                resp.getWriter().write(JSON.toJSONString(Result.error(403, "无权限访问")));
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
