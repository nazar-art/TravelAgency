package com.zasadnyy.task10.controller.transformer;

import com.zasadnyy.task10.controller.dao.TourDao;
import com.zasadnyy.task10.controller.dao.UserDao;
import com.zasadnyy.task10.controller.persistance.ConnectionManager;
import com.zasadnyy.task10.model.Purchase;
import com.zasadnyy.task10.utils.SQLStatements;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseTransformer implements IBaseTranformer<Purchase> {
    private static Logger log = Logger.getLogger(PurchaseTransformer.class);
    private static PurchaseTransformer instance = null;

    protected PurchaseTransformer() {
    }

    public static PurchaseTransformer getInstance() {
        if (instance == null) {
            instance = new PurchaseTransformer();
        }
        return instance;
    }


    @Override
    public Purchase fromRsToObject(ResultSet rs) {
        Purchase purchase = new Purchase();
        UserDao userDao = new UserDao();
        TourDao tourDao = new TourDao();
        try {
            purchase.setId(rs.getInt("id"));
            purchase.setUser(userDao.find(rs.getInt("user_id")));
            purchase.setTour(tourDao.find(rs.getInt("tour_id")));
            purchase.setTimeOfPurchase(rs.getTimestamp("time_of_purchase"));
            purchase.setPrice(rs.getDouble("price"));
            purchase.setChecked(rs.getBoolean("checked"));
        } catch (SQLException e) {
            log.error(e);
        }

        return purchase;
    }

    @Override
    public PreparedStatement getSelectStatementById(int id) {
        PreparedStatement select = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            select = connection.
                    prepareStatement(SQLStatements.SELECT_FROM_PURCHASES + " WHERE id=?");
            select.setInt(1, id);
        } catch (SQLException e) {
            log.error(e);
        }
        return select;
    }

    @Override
    public PreparedStatement fromObjectToInsertStatement(Purchase purchase) {
        PreparedStatement insert = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            insert = connection.
                    prepareStatement(SQLStatements.INSERT_INTO_PURCHASES, java.sql.Statement.RETURN_GENERATED_KEYS);
            insert.setInt(1, purchase.getUser().getId());
            insert.setInt(2, purchase.getTour().getId());
            insert.setDouble(3, purchase.getPrice());
        } catch (SQLException e) {
            log.error(e);
        }
        return insert;
    }

    @Override
    public PreparedStatement fromObjectToUpdateStatement(Purchase purchase) {
        PreparedStatement update = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            update = connection.
                    prepareStatement(SQLStatements.UPDATE_PURCHASES + " WHERE id=?");
            update.setInt(1, purchase.getUser().getId());
            update.setInt(2, purchase.getTour().getId());
            update.setDouble(3, purchase.getPrice());
            update.setBoolean(4, purchase.isChecked());
            update.setInt(5, purchase.getId());
        } catch (SQLException e) {
            log.error(e);
        }
        return update;
    }

    @Override
    public PreparedStatement fromObjectToDeleteStatement(Purchase purchase) {
        PreparedStatement delete = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            delete = connection.
                    prepareStatement(SQLStatements.DELETE_FROM_PURCHASES + " WHERE id=?");
            delete.setInt(1, purchase.getId());
        } catch (SQLException e) {
            log.error(e);
        }
        return delete;
    }

    @Override
    public PreparedStatement listOfObjects() {
        PreparedStatement select = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            select = connection.
                    prepareStatement(SQLStatements.SELECT_FROM_PURCHASES);
        } catch (SQLException e) {
            log.error(e);
        }
        return select;
    }
}
