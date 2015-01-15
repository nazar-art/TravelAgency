package com.zasadnyy.task10.controller.service;

import com.zasadnyy.task10.controller.dao.TourDao;
import com.zasadnyy.task10.model.Tour;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TourService implements IBaseService<Tour> {
    TourDao dao = new TourDao();

    @Override
    public Tour find(int id) {
        return dao.find(id);
    }

    @Override
    public Tour save(Tour tour) {
        return dao.save(tour);
    }

    @Override
    public Tour update(Tour tour) {
        return dao.update(tour);
    }

    @Override
    public void delete(Tour tour, HttpServlet servlet) {
        if (!tour.getImage().equals("default_" + tour.getType() + ".jpg")) {
            String path = servlet.getServletContext().getRealPath("") + "\\img\\tours\\" + tour.getImage();
            new File(path).delete();
        }
        dao.delete(tour);
    }

    public void delete(int tourId, HttpServlet servlet) {
        Tour tour = find(tourId);
        delete(tour, servlet);
    }

    @Override
    public List<Tour> getListOfObjects() {
        return dao.getListOfObjects();
    }

    public List<Tour> getSelectedTours(String type) {
        List<Tour> tours = new ArrayList<>();
        for (Tour tour : getListOfObjects()) {
            if (tour.getType().toLowerCase().equals(type)) {
                tours.add(tour);
            }
        }
        return tours;
    }
}
