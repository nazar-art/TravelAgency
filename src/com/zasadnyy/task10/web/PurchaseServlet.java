package com.zasadnyy.task10.web;

import com.zasadnyy.task10.controller.service.PurchaseService;
import com.zasadnyy.task10.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PurchaseService purchaseService = new PurchaseService();
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        int tourId = Integer.parseInt(request.getParameter("tour"));
        purchaseService.save(user, tourId);
        request.getRequestDispatcher("/pages/purchase.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
