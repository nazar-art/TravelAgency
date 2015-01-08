package com.zasadnyy.task10.web;

import com.zasadnyy.task10.controller.service.UserService;
import com.zasadnyy.task10.model.User;
import com.zasadnyy.task10.utils.MD5Utils;
import com.zasadnyy.task10.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("logout") != null) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            log.info("Logged out: " + user.getFirstName() + " " + user.getLastName());
            session.removeAttribute("user");
            response.sendRedirect("/login");
        } else {
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String password = MD5Utils.getMD5String(request.getParameter("password"));
        User user = new UserService().getByEmail(email);
        if (!ValidationUtils.isNullOrEmpty(user.getEmail()) && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            log.info("Logged in: " + user.getFirstName() + " " + user.getLastName());
            if (session.getAttribute("waitUrl") != null) {
                String url = session.getAttribute("waitUrl").toString();
                response.sendRedirect(url);
            } else {
                response.sendRedirect("/tours");
            }
        } else {
            request.setAttribute("loginErrors", "Wrong email or password");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }

}
