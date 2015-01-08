package com.zasadnyy.task10.controller.application;

import com.zasadnyy.task10.controller.service.UserService;
import com.zasadnyy.task10.model.User;

/**
 * @author nlelyak
 * @version 1.00 2014-03-13
 */
public class Appl {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user = userService.getByEmail("lelyak@gmail.com");
        user.setAdmin(true);
    }
}
