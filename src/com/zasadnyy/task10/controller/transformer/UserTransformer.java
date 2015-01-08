package com.zasadnyy.task10.controller.transformer;

import com.zasadnyy.task10.controller.persistance.ConnectionManager;
import com.zasadnyy.task10.model.SQLStatements;
import com.zasadnyy.task10.model.User;
import org.apache.log4j.Logger;

import java.sql.*;

public class UserTransformer implements IBaseTranformer<User> {
    private static Logger log = Logger.getLogger(UserTransformer.class);
    private static UserTransformer instance = null;

    protected UserTransformer() {
    }

    public static UserTransformer getInstance() {
        if (instance == null) {
            instance = new UserTransformer();
        }
        return instance;
    }

    @Override
    public User fromRsToObject(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setLastName(rs.getString("last_name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setDiscount(rs.getInt("discount"));
            user.setAdmin(rs.getBoolean("admin"));
            user.setImage(rs.getString("image"));
        } catch (SQLException e) {
            log.error(e);
        }
        return user;
    }

    @Override
    public PreparedStatement getSelectStatementById(int id) {
        PreparedStatement select = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            select = connection
                    .prepareStatement(SQLStatements.SELECT_FROM_USERS + " WHERE id = ?");
            select.setInt(1, id);
        } catch (SQLException e) {
            log.error(e);
        }
        return select;
    }

    @Override
    public PreparedStatement fromObjectToInsertStatement(User user) {
        PreparedStatement insert = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            insert = connection.
                    prepareStatement(SQLStatements.INSERT_INTO_USERS, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, user.getFirstName());
            insert.setString(2, user.getLastName());
            insert.setString(3, user.getEmail());
            insert.setString(4, user.getPassword());
            insert.setString(5,user.getImage());
        } catch (SQLException e) {
            log.error(e);
        }

        return insert;
    }

    @Override
    public PreparedStatement fromObjectToUpdateStatement(User user) {
        PreparedStatement update = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            update = connection
                    .prepareStatement(SQLStatements.UPDATE_USERS + " WHERE id = ?");

            update.setString(1, user.getFirstName());
            update.setString(2, user.getLastName());
            update.setString(3, user.getEmail());
            update.setString(4, user.getPassword());
            update.setInt(5, user.getDiscount());
            update.setBoolean(6, user.isAdmin());
            update.setString(7, user.getImage());
            update.setInt(8, user.getId());
        } catch (SQLException e) {
            log.error(e);
        }
        return update;
    }

    @Override
    public PreparedStatement fromObjectToDeleteStatement(User user) {
        PreparedStatement delete = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            delete = connection
                    .prepareStatement(SQLStatements.DELETE_FROM_USERS + " WHERE id = ?");
            delete.setInt(1, user.getId());
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
                .prepareStatement(SQLStatements.SELECT_FROM_USERS);
        }catch (SQLException e) {
            log.error(e);
        }
        return select;
    }

    public PreparedStatement getSelectStatementByEmail(String email) {
        PreparedStatement select = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            select = connection
                    .prepareStatement(SQLStatements.SELECT_FROM_USERS + " WHERE email = ?");
            select.setString(1, email);
        } catch (SQLException e) {
            log.error(e);
        }
        return select;
    }
}
