package com.zasadnyy.task10.web.secured;

import com.zasadnyy.task10.controller.service.TourService;
import com.zasadnyy.task10.model.Tour;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/tourstable")
@MultipartConfig
public class ToursTableServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(ToursTableServlet.class);
    private static final String DATA_DIRECTORY = "img\\tours";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private TourService tourService;
    private List<Tour> tours;

    @Override
    public void init() throws ServletException {
        tourService = new TourService();
        updateTable();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateTable();
        request.setAttribute("toursTable", tours);
        request.getRequestDispatcher("/pages/secured/toursTable.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("add-tour") != null) {
            processRegistration(request, response);
        } else if (request.getParameter("delete") != null) {
            deleteTour(request);
        } else if (request.getParameter("update") != null) {
            try {
                updateTour(request);
            } catch (ParseException e) {
                log.error(e);
            }
        }
        doGet(request, response);
    }

    public void updateTable() {
        tours = tourService.getListOfObjects();
    }

    public void deleteTour(HttpServletRequest request) throws ServletException, IOException {
        String[] tours = request.getParameterValues("checkedTour");
        for (String tourId : tours) {
            tourService.delete(Integer.parseInt(tourId), this);
        }
    }

    public void updateTour(HttpServletRequest request) throws ServletException, IOException, ParseException {
        int id = Integer.parseInt(request.getParameter("update"));
        Tour tour = tourService.find(id);

        String name = (!ValidationUtils.isNullOrEmpty(request.getParameter("name-" + id)))
                ? request.getParameter("name-" + id) : tour.getName();
        String type = request.getParameter("typeslist-" + id);
        Date depart = (request.getParameter("depart-" + id) != null)
                ? new SimpleDateFormat(DATE_FORMAT).parse(request.getParameter("depart-" + id))
                : tour.getDepart();
        Date arrival = (request.getParameter("arrival-" + id) != null)
                ? new SimpleDateFormat(DATE_FORMAT).parse(request.getParameter("arrival-" + id))
                : tour.getDepart();
        if (ValidationUtils.isDateBigger(request.getParameter("depart-" + id), request.getParameter("arrival-" + id), DATE_FORMAT)) {
            depart = tour.getDepart();
            arrival = tour.getArrival();
        }
        double price = (!ValidationUtils.isNullOrEmpty(request.getParameter("price-" + id))
                && ValidationUtils.isNumber(request.getParameter("price-" + id)))
                ? Double.parseDouble(request.getParameter("price-" + id)) : tour.getPrice();
        int discount = (!ValidationUtils.isNumber(request.getParameter("discount-" + id))
                && ValidationUtils.isNumber(request.getParameter("discount-" + id)))
                ? Integer.parseInt(request.getParameter("discount-" + id)) : tour.getDiscount();
        if (discount < 0 || discount > 100) {
            discount = tour.getDiscount();
        }
        String imageName = tour.getImage();
        Part filePart = request.getPart("tour-image-" + id);
        try {
            String contentType = filePart.getContentType();
            if (contentType.startsWith("image")) {
                File image = FileUploadUtils.uploadFile(this, DATA_DIRECTORY, filePart);
                imageName = FileUploadUtils.getFilename(image);
            }
        } catch (Exception e) {
            log.error(e);
        }

        tour.setName(name);
        tour.setImage(imageName);
        tour.setType(type);
        tour.setDepart(depart);
        tour.setArrival(arrival);
        tour.setPrice(price);
        tour.setDiscount(discount);

        tourService.update(tour);
    }

    public void processRegistration(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            String type = request.getParameter("typeslist");
            String depart = request.getParameter("depart");
            String arrival = request.getParameter("arrival");
            String price = request.getParameter("price");
            String imageName = "default_" + type + ".jpg";
            Part filePart = request.getPart("tour-image");
            try {
                String contentType = filePart.getContentType();
                if (contentType.startsWith("image")) {
                    File image = FileUploadUtils.uploadFile(this, DATA_DIRECTORY, filePart);
                    imageName = FileUploadUtils.getFilename(image);
                }
            } catch (Exception e) {
                log.error(e);
            }

            List<String> registrationErrors = validateInputs(name, depart, arrival, price);

            if (registrationErrors.size() > 0) {
                request.setAttribute("registrationTourErrors", registrationErrors);
                request.getRequestDispatcher("/pages/secured/toursTable.jsp").forward(request, response);
            } else {
                Tour tour = new Tour();
                tour.setName(name);
                tour.setType(type);
                Date departDate = new SimpleDateFormat(DATE_FORMAT).parse(depart);
                tour.setDepart(departDate);
                Date arrivalDate = new SimpleDateFormat(DATE_FORMAT).parse(arrival);
                tour.setArrival(arrivalDate);
                tour.setPrice(Double.parseDouble(price));
                tour.setImage(imageName);
                tourService.save(tour);
            }
        } catch (ParseException e) {
            log.error(e);
        }
    }

    public List<String> validateInputs(String name, String depart, String arrival, String price) {
        List<String> registrationErrors = new ArrayList<>();
        if (ValidationUtils.isNullOrEmpty(name)) {
            registrationErrors.add(ValidationErrors.TOUR_NAME);
        }
        if (!ValidationUtils.isThisDateValid(depart, DATE_FORMAT)
                || !ValidationUtils.isThisDateValid(arrival, DATE_FORMAT)) {
            registrationErrors.add(ValidationErrors.DATE_FORMAT);
        }
        if (ValidationUtils.isDateBigger(depart, arrival, DATE_FORMAT)) {
            registrationErrors.add(ValidationErrors.DATE_SEQUENCE);
        }
        if (!ValidationUtils.isNumber(price)) {
            registrationErrors.add(ValidationErrors.PRICE);
        }
        return registrationErrors;
    }
}
