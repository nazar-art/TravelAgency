package com.zasadnyy.task10.controller.dao;

import java.util.List;

public interface IBaseDao<T> {
    T getByID(int id);
    T save(T type);
    T update(T type);
    void delete(T type);
    List<T> getListOfObjects();
}
