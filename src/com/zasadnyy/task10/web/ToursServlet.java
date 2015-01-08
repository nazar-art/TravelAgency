package com.zasadnyy.task10.web;

import com.zasadnyy.task10.controller.service.TourService;
import com.zasadnyy.task10.model.Tour;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tours")
public class ToursServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(ToursServlet.class);
    private TourService tourService;
    private List<Tour> tours;

    @Override
    public void init() throws ServletException {
        tourService = new TourService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("select") != null){
            String type = request.getParameter("select");
            tours = tourService.getSelectedTours(type);
            request.setAttribute("tours", tours);
            request.getRequestDispatcher("/pages/tours.jsp").forward(request, response);
        } else {
            updateTours();
            request.setAttribute("tours", tours);
            request.getRequestDispatcher("/pages/tours.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void updateTours() {
        tours = tourService.getListOfObjects();
    }
}
