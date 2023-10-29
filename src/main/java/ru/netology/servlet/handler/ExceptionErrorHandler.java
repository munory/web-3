package ru.netology.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExceptionErrorHandler {

    void handle(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
