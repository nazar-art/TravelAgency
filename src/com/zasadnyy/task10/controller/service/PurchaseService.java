package com.zasadnyy.task10.controller.service;

import com.zasadnyy.task10.controller.dao.PurchaseDao;
import com.zasadnyy.task10.model.Purchase;
import com.zasadnyy.task10.model.Tour;
import com.zasadnyy.task10.model.User;

import javax.servlet.http.HttpServlet;
import java.util.List;

public class PurchaseService implements IBaseService<Purchase> {
    private PurchaseDao dao = new PurchaseDao();

    @Override
    public Purchase getByID(int id) {
        return dao.getByID(id);
    }

    @Override
    public Purchase save(Purchase purchase) {
        return dao.save(purchase);
    }

    public Purchase save(User user, int tourId) {
        TourService tourService = new TourService();
        Tour tour = tourService.getByID(tourId);
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setTour(tour);
        int discount = (user.getDiscount() > tour.getDiscount())
                ? user.getDiscount() : tour.getDiscount();
        double price = tour.getPrice() * (100 - discount) / 100;
        purchase.setPrice(price);
        return save(purchase);
    }

    @Override
    public Purchase update(Purchase purchase) {
        return dao.update(purchase);
    }

    @Override
    public void delete(Purchase purchase, HttpServlet servlet) {
        dao.delete(purchase);
    }

    public void delete(int purchaseId, HttpServlet servlet) {
        Purchase purchase = getByID(purchaseId);
        delete(purchase, servlet);
    }

    @Override
    public List<Purchase> getListOfObjects() {
        return dao.getListOfObjects();
    }

    public Purchase check(int purchaseId) {
        Purchase purchase = getByID(purchaseId);
        purchase.setChecked(true);
        return update(purchase);
    }
}
