package com.zasadnyy.task10.web;

import com.zasadnyy.task10.controller.service.UserService;
import com.zasadnyy.task10.model.User;
import com.zasadnyy.task10.utils.ValidationErrors;
import com.zasadnyy.task10.utils.FileUploadUtils;
import com.zasadnyy.task10.utils.ValidationUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("/registration2")
public class RegistrationServletVer2 extends HttpServlet {
    private static Logger log = Logger.getLogger(RegistrationServletVer2.class);
    private UserService userService = new UserService();
    ;

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
        String firstName = "";
        String lastName = "";
        String email = "";
        String password = "";
        String imageName = "default.png";

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();

                    if (fieldName.equals("first_name")) {
                        firstName = fieldValue;
                    } else if (fieldName.equals("last_name")) {
                        lastName = fieldValue;
                    } else if (fieldName.equals("email")) {
                        email = fieldValue;
                    } else if (fieldName.equals("password")) {
                        password = fieldValue;
                    }
                    if (!item.isFormField() && !fieldValue.equals("")) {
                        String filename = FilenameUtils.getName(item.getName());
                        String contentType = getServletContext().getMimeType(filename);
                        if (contentType.startsWith("image")) {
                            File file = FileUploadUtils.uploadFile(this, "img\\users", item);
                            imageName = FileUploadUtils.getFilename(file);
                        }
                    }
                }
            } catch (FileUploadException e) {
                log.error(e);
            } catch (Exception e) {
                log.error(e);
            }
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
