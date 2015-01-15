package com.zasadnyy.task10.controller.dao;

import com.zasadnyy.task10.controller.transformer.UserTransformer;
import com.zasadnyy.task10.model.User;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IBaseDao<User> {
    private static Logger log = Logger.getLogger(UserDao.class);

    @Override
    public User find(int id) {
        User user = new User();
        try {
            PreparedStatement select = UserTransformer.getInstance().getSelectStatementById(id);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                user = UserTransformer.getInstance().fromRsToObject(rs);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return user;
    }

    @Override
    public User save(User user) {
        try {
            PreparedStatement insert = UserTransformer.getInstance().fromObjectToInsertStatement(user);
            insert.executeUpdate();
            ResultSet keys = insert.getGeneratedKeys();
            keys.next();
            user = find(keys.getInt(1));
        } catch (SQLException e) {
            log.error(e);
        }
        return user;
    }

    @Override
    public User update(User user) {
        try {
            PreparedStatement update = UserTransformer.getInstance().fromObjectToUpdateStatement(user);
            update.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
        return user;
    }

    @Override
    public void delete(User user) {
        try {
            PreparedStatement delete = UserTransformer.getInstance().fromObjectToDeleteStatement(user);
            delete.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
        }
    }

    @Override
    public List<User> getListOfObjects() {
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement select = UserTransformer.getInstance().listOfObjects();
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                User user = UserTransformer.getInstance().fromRsToObject(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return users;
    }

    public User getByEmail(String email) {
        User user = new User();
        try {
            PreparedStatement select = UserTransformer.getInstance().getSelectStatementByEmail(email);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                user = UserTransformer.getInstance().fromRsToObject(rs);
            }
        } catch (SQLException e) {
            log.error(e);
        }
        return user;
    }
}
