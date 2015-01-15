package com.zasadnyy.task10.utils;

public class SQLStatements {
    public static String SELECT_FROM_USERS = "SELECT * FROM users";
    public static String INSERT_INTO_USERS = "INSERT INTO users(first_name, last_name, email, password, image) VALUES(?,?,?,?,?)";
    public static String UPDATE_USERS = "UPDATE users SET first_name=?, last_name=?, email=?, password=?, " +
            "discount=?, admin=?, image=?";
    public static String DELETE_FROM_USERS = "DELETE FROM users";

    public static String SELECT_FROM_TOURS = "SELECT * FROM tours";
    public static String INSERT_INTO_TOURS = "INSERT INTO tours(name, type, depart, arrival, price, image) VALUES(?,?,?,?,?,?)";
    public static String UPDATE_TOURS = "UPDATE tours SET name=?, type=?, depart=?, arrival=?, price=?, discount=?, image=?";
    public static String DELETE_FROM_TOURS = "DELETE FROM tours";

    public static String SELECT_FROM_PURCHASES = "SELECT * FROM purchases";
    public static String INSERT_INTO_PURCHASES = "INSERT INTO purchases(user_id, tour_id, price) VALUES(?,?,?)";
    public static String UPDATE_PURCHASES = "UPDATE purchases SET user_id=?, tour_id=?, price=?, checked=?";
    public static String DELETE_FROM_PURCHASES = "DELETE FROM purchases";
}
