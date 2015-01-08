package com.zasadnyy.task10.web;

import com.zasadnyy.task10.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        if (session == null || session.getAttribute("user") == null) {
            session.setAttribute("waitUrl", request.getRequestURL().append('?').append(request.getQueryString()));
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            User user = (User) session.getAttribute("user");
            if (request.getRequestURI().contains("/admin/")) {
                if (user.isAdmin()) {
                    chain.doFilter(req, res);
                } else {
                    response.sendRedirect("/tours");
                }
            } else {
                chain.doFilter(req, res);
            }

        }
    }

    @Override
    public void destroy() {
    }
}