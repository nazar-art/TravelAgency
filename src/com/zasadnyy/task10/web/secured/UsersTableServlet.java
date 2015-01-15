package com.zasadnyy.task10.web.secured;

import com.zasadnyy.task10.controller.service.UserService;
import com.zasadnyy.task10.model.User;
import com.zasadnyy.task10.utils.FileUploadUtils;
import com.zasadnyy.task10.utils.ValidationUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/userstable")
@MultipartConfig
public class UsersTableServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(UsersTableServlet.class);
    private static final String DATA_DIRECTORY = "img\\users";
    private UserService userService;
    private List<User> users;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
        updateTable();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateTable();
        request.setAttribute("users", users);
        request.getRequestDispatcher("/pages/secured/usersTable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (request.getParameter("delete") != null) {
            deleteUser(request);
        } else if (request.getParameter("update") != null) {
            updateUser(request);
        }
        doGet(request, response);
    }

    public void updateTable() {
        users = userService.getListOfObjects();
    }

    public void deleteUser(HttpServletRequest request) throws ServletException, IOException {
        String[] users = request.getParameterValues("checkedUsers");
        for (String userId : users) {
            userService.delete(Integer.parseInt(userId), this);
        }
    }

    public void updateUser(HttpServletRequest request) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("update"));
        User user = userService.find(id);

        String firstName = (!ValidationUtils.isNullOrEmpty(request.getParameter("first_name-" + id)))
                ? request.getParameter("first_name-" + id) : user.getFirstName();
        String lastName = (!ValidationUtils.isNullOrEmpty(request.getParameter("last_name-" + id)))
                ? request.getParameter("last_name-" + id) : user.getLastName();
        String email = (!ValidationUtils.isNullOrEmpty(request.getParameter("email-" + id))
                && ValidationUtils.validEmail(request.getParameter("email-" + id)))
                ? request.getParameter("email-" + id) : user.getEmail();
        if (!(userService.getByEmail(email) == null)) {
            email = user.getEmail();
        }
        int discount = (!ValidationUtils.isNumber(request.getParameter("discount-" + id))
                && ValidationUtils.isNumber(request.getParameter("discount-" + id)))
                ? Integer.parseInt(request.getParameter("discount-" + id)) : user.getDiscount();
        if (discount < 0 || discount > 100) {
            discount = user.getDiscount();
        }
        boolean admin = (request.getParameter("admin-" + id).equals("true"));

        String imageName = user.getImage();
        Part filePart = request.getPart("userImage-" + id);
        try {
            String contentType = filePart.getContentType();
            if (contentType.startsWith("image")) {
                File image = FileUploadUtils.uploadFile(this, DATA_DIRECTORY, filePart);
                imageName = FileUploadUtils.getFilename(image);
            }
        } catch (Exception e) {
            log.error(e);
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDiscount(discount);
        user.setAdmin(admin);
        user.setImage(imageName);

        userService.update(user);
    }
}
