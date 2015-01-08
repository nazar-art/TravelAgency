package com.zasadnyy.task10.controller.transformer;

import com.zasadnyy.task10.controller.persistance.ConnectionManager;
import com.zasadnyy.task10.model.SQLStatements;
import com.zasadnyy.task10.model.Tour;
import org.apache.log4j.Logger;

import java.sql.*;

public class TourTransformer implements IBaseTranformer<Tour> {
    private static Logger log = Logger.getLogger(TourTransformer.class);
    private static TourTransformer instance = null;

    protected TourTransformer() {
    }

    public static TourTransformer getInstance() {
        if (instance == null) {
            instance = new TourTransformer();
        }
        return instance;
    }

    @Override
    public Tour fromRsToObject(ResultSet rs) {
        Tour tour = new Tour();
        try {
            tour.setId(rs.getInt("id"));
            tour.setName(rs.getString("name"));
            tour.setType(rs.getNString("type"));
            tour.setDepart(rs.getDate("depart"));
            tour.setArrival(rs.getDate("arrival"));
            tour.setPrice(rs.getDouble("price"));
            tour.setDiscount(rs.getInt("discount"));
            tour.setImage(rs.getString("image"));
        } catch (SQLException e) {
            log.error(e);
        }
        return tour;
    }

    @Override
    public PreparedStatement getSelectStatementById(int id) {
        PreparedStatement select = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            select = connection
                    .prepareStatement(SQLStatements.SELECT_FROM_TOURS + " WHERE id = ?");
            select.setInt(1, id);
        } catch (SQLException e) {
            log.error(e);
        }
        return select;
    }

    @Override
    public PreparedStatement fromObjectToInsertStatement(Tour tour) {
        PreparedStatement insert = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            insert = connection.
                    prepareStatement(SQLStatements.INSERT_INTO_TOURS, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, tour.getName());
            insert.setString(2, tour.getType());
            insert.setDate(3, new Date(tour.getDepart().getTime()));
            insert.setDate(4, new Date(tour.getArrival().getTime()));
            insert.setDouble(5, tour.getPrice());
            insert.setString(6, tour.getImage());
        } catch (SQLException e) {
            log.error(e);
        }

        return insert;
    }

    @Override
    public PreparedStatement fromObjectToUpdateStatement(Tour tour) {
        PreparedStatement update = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            update = connection
                    .prepareStatement(SQLStatements.UPDATE_TOURS + " WHERE id = ?");
            update.setString(1, tour.getName());
            update.setString(2, tour.getType());
            update.setDate(3, new Date(tour.getDepart().getTime()));
            update.setDate(4, new Date(tour.getArrival().getTime()));
            update.setDouble(5, tour.getPrice());
            update.setInt(6, tour.getDiscount());
            update.setString(7, tour.getImage());
            update.setInt(8, tour.getId());
        } catch (SQLException e) {
            log.error(e);
        }
        return update;
    }

    @Override
    public PreparedStatement fromObjectToDeleteStatement(Tour tour) {
        PreparedStatement delete = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            delete = connection
                    .prepareStatement(SQLStatements.DELETE_FROM_TOURS + " WHERE id = ?");
            delete.setInt(1, tour.getId());
        } catch (SQLException e) {
            log.error(e);
        }
        return delete;
    }

    @Override
    public PreparedStatement listOfObjects() {
        PreparedStatement select = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            select = connection
                    .prepareStatement(SQLStatements.SELECT_FROM_TOURS);
        } catch (SQLException e) {
            log.error(e);
        }
        return select;
    }
}
