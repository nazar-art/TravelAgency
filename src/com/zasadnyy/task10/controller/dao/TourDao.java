package com.zasadnyy.task10.controller.dao;

import com.zasadnyy.task10.controller.transformer.TourTransformer;
import com.zasadnyy.task10.model.Tour;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TourDao implements IBaseDao<Tour> {
    private static Logger log = Logger.getLogger(TourDao.class);

    @Override
    public Tour find(int id) {
        Tour tour = new Tour();
        try {
            PreparedStatement select = TourTransformer.getInstance().getSelectStatementById(id);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                tour = TourTransformer.getInstance().fromRsToObject(rs);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return tour;
    }

    @Override
    public Tour save(Tour tour) {
        try {
            PreparedStatement insert = TourTransformer.getInstance().fromObjectToInsertStatement(tour);
            insert.executeUpdate();
            ResultSet keys = insert.getGeneratedKeys();
            keys.next();
            tour = find(keys.getInt(1));
        } catch (SQLException e) {
            log.error(e);
        }
        return tour;
    }

    @Override
    public Tour update(Tour tour) {
        try {
            PreparedStatement update = TourTransformer.getInstance().fromObjectToUpdateStatement(tour);
            update.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        return tour;
    }

    @Override
    public void delete(Tour tour) {
        try {
            PreparedStatement delete = TourTransformer.getInstance().fromObjectToDeleteStatement(tour);
            delete.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    @Override
    public List<Tour> getListOfObjects() {
        List<Tour> tours = new ArrayList<>();
        try {
            PreparedStatement select = TourTransformer.getInstance().listOfObjects();
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                Tour tour = TourTransformer.getInstance().fromRsToObject(rs);
                tours.add(tour);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return tours;
    }
}
