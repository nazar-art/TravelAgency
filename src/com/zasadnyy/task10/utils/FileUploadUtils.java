package com.zasadnyy.task10.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.Part;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUploadUtils {
    private static Logger log = Logger.getLogger(FileUploadUtils.class);
    private static final int DEFAULT_BUFFER_SIZE = 10240;

    public static File uploadFile(HttpServlet servlet, String directory, FileItem item)
            throws Exception {
        String path = servlet.getServletContext().getRealPath("")
                + File.separator + directory;
        File uploadFolder = new File(path);
        String filename = FilenameUtils.getName(item.getName()).replaceAll("\\s", "_");
        String fileNamePrefix = FilenameUtils.getBaseName(filename) + "_";
        String fileNameSuffix = "." + FilenameUtils.getExtension(filename);
        File file = File.createTempFile(fileNamePrefix, fileNameSuffix, uploadFolder);
        item.write(file);
        return file;
    }

    public static File uploadFile(HttpServlet servlet, String directory, Part filePart)
            throws Exception {
        String path = servlet.getServletContext().getRealPath("")
                + File.separator + directory;
        String filename = getFilename(filePart)
                .substring(getFilename(filePart).lastIndexOf('/') + 1)
                .substring(getFilename(filePart).lastIndexOf('\\') + 1)
                .replaceAll("\\s", "_");

        String prefix = filename;
        String suffix = "";
        if (filename.contains(".")) {
            prefix = filename.substring(0, filename.lastIndexOf('.'));
            suffix = filename.substring(filename.lastIndexOf('.'));
        }

        File file = File.createTempFile(prefix + "_", suffix, new File(path));
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new BufferedInputStream(filePart.getInputStream(), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(new FileOutputStream(file), DEFAULT_BUFFER_SIZE);
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            for (int length = 0; ((length = input.read(buffer)) > 0); ) {
                output.write(buffer, 0, length);
            }
        } finally {
            if (output != null) try {
                output.close();
            } catch (IOException e) {
                log.error(e);
            }
            if (input != null) try {
                input.close();
            } catch (IOException e) {
                log.error(e);
            }
        }
        return file;
    }

    public static String getFilename(File file) {
        Pattern pattern = Pattern.compile("([^\\\\]+)$");
        Matcher matcher = pattern.matcher(file.toString());
        String filename = "";
        while (matcher.find()) {
            filename = matcher.group(1);
        }
        return filename;
    }

    public static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}
