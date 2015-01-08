package com.zasadnyy.task10.controller.service;

import javax.servlet.http.HttpServlet;
import java.util.List;

public interface IBaseService<T> {
    T getByID(int id);
    T save(T type);
    T update(T type);
    void delete(T type, HttpServlet servlet);
    List<T> getListOfObjects();
}
