package com.zasadnyy.task10.web.secured;

import com.zasadnyy.task10.controller.service.PurchaseService;
import com.zasadnyy.task10.model.Purchase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/purchasestable")
public class PurchasesTableServlet extends HttpServlet {
    private PurchaseService purchaseService;
    private List<Purchase> purchases;

    @Override
    public void init() throws ServletException {
        purchaseService = new PurchaseService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateTable();
        request.setAttribute("purchases", purchases);
        request.getRequestDispatcher("/pages/secured/purchasesTable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("check") != null) {
            checkPurchases(request, response);
        } else if (request.getParameter("delete") != null) {
            deletePurchases(request, response);
        }
        doGet(request, response);
    }

    public void updateTable() {
        purchases = purchaseService.getListOfObjects();
    }

    public void checkPurchases(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] purchases = request.getParameterValues("checkedPurchases");
        for (String purchaseId : purchases) {
            purchaseService.check(Integer.parseInt(purchaseId));
        }
    }

    public void deletePurchases(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] purchases = request.getParameterValues("checkedPurchases");
        for (String purchaseId : purchases) {
            purchaseService.delete(Integer.parseInt(purchaseId), this);
        }
    }
}

