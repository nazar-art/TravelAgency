package com.zasadnyy.task10.controller.transformer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface IBaseTranformer<T> {
    T fromRsToObject(ResultSet rs);

    PreparedStatement getSelectStatementById(int id);

    PreparedStatement fromObjectToInsertStatement(T object);

    PreparedStatement fromObjectToUpdateStatement(T object);

    PreparedStatement fromObjectToDeleteStatement(T object);

    PreparedStatement listOfObjects();
}
