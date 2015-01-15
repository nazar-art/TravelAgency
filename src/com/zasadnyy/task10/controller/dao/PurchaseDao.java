package com.zasadnyy.task10.controller.dao;

import com.zasadnyy.task10.controller.transformer.PurchaseTransformer;
import com.zasadnyy.task10.model.Purchase;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDao implements IBaseDao<Purchase> {
    private static Logger log = Logger.getLogger(PurchaseDao.class);

    @Override
    public Purchase find(int id) {
        Purchase purchase = new Purchase();
        try {
            PreparedStatement select = PurchaseTransformer.getInstance().getSelectStatementById(id);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                purchase = PurchaseTransformer.getInstance().fromRsToObject(rs);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return purchase;
    }

    @Override
    public Purchase save(Purchase purchase) {
        try {
            PreparedStatement insert = PurchaseTransformer.getInstance().fromObjectToInsertStatement(purchase);
            insert.executeUpdate();
            ResultSet keys = insert.getGeneratedKeys();
            keys.next();
            purchase = find(keys.getInt(1));
        } catch (SQLException e) {
            log.error(e);
        }
        return purchase;
    }

    @Override
    public Purchase update(Purchase purchase) {
        try {
            PreparedStatement update = PurchaseTransformer.getInstance().fromObjectToUpdateStatement(purchase);
            update.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        return purchase;
    }

    @Override
    public void delete(Purchase purchase) {
        try {
            PreparedStatement delete = PurchaseTransformer.getInstance().fromObjectToDeleteStatement(purchase);
            delete.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    @Override
    public List<Purchase> getListOfObjects() {
        List<Purchase> purchases = new ArrayList<>();
        try {
            PreparedStatement select = PurchaseTransformer.getInstance().listOfObjects();
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                Purchase purchase = PurchaseTransformer.getInstance().fromRsToObject(rs);
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return purchases;
    }
}
