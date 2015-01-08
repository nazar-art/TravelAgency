package com.zasadnyy.task10.controller.service;

import com.zasadnyy.task10.controller.dao.UserDao;
import com.zasadnyy.task10.model.User;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.util.List;

public class UserService implements IBaseService<User> {
    private UserDao dao = new UserDao();

    @Override
    public User getByID(int id) {
        return dao.getByID(id);
    }

    @Override
    public User save(User user) {
        return dao.save(user);
    }

    @Override
    public User update(User user) {
        return dao.update(user);
    }

    @Override
    public void delete(User user, HttpServlet servlet) {
        if (!user.getImage().equals("default.png")) {
            String path = servlet.getServletContext().getRealPath("") + "\\img\\users\\" + user.getImage();
            new File(path).delete();
        }
        dao.delete(user);
    }

    public void delete(int userId, HttpServlet servlet) {
        User user = getByID(userId);
        delete(user, servlet);
    }

    @Override
    public List<User> getListOfObjects() {
        return dao.getListOfObjects();
    }

    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }
}
