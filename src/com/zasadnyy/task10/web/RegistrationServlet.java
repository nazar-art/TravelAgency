package com.zasadnyy.task10.web;

import com.zasadnyy.task10.controller.service.UserService;
import com.zasadnyy.task10.model.User;
import com.zasadnyy.task10.utils.ValidationErrors;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/registration")
@MultipartConfig
public class RegistrationServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(RegistrationServlet.class);
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        processRegistration(request, response);
    }

    public void processRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String imageName = "default.png";

        Part filePart = request.getPart("userImage");
        try {
            String contentType = filePart.getContentType();
            if (contentType.startsWith("image")) {
                File image = FileUploadUtils.uploadFile(this, "img/users", filePart);
                imageName = FileUploadUtils.getFilename(image);
            }
        } catch (Exception e) {
            log.error(e);
        }

        List<String> registrationErrors = validateInputs(firstName, lastName, email, password);

        if (registrationErrors.size() > 0) {
            request.setAttribute("registrationErrors", registrationErrors);
            request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
        } else {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setEncryptedPassword(password);
            user.setImage(imageName);

            userService.save(user);
            response.sendRedirect("/login");
        }
    }

    public List<String> validateInputs(String firstName, String lastName, String email, String password) {
        List<String> registrationErrors = new ArrayList<>();
        if (ValidationUtils.isNullOrEmpty(firstName)) {
            registrationErrors.add(ValidationErrors.FIRST_NAME);
        }
        if (ValidationUtils.isNullOrEmpty(lastName)) {
            registrationErrors.add(ValidationErrors.LAST_NAME);
        }
        if (!ValidationUtils.validEmail(email)) {
            registrationErrors.add(ValidationErrors.EMAIL);
        }
        if (userService.getByEmail(email).getId() != 0) {
            registrationErrors.add(ValidationErrors.EMAIL_ALREADY_PRESENT);
        }
        if (ValidationUtils.isNullOrEmpty(password)) {
            registrationErrors.add(ValidationErrors.PASSWORD);
        }
        return registrationErrors;
    }
}
