package ru.netology.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CatchErrorRequestHandler implements RequestHandler {

    final private ExceptionErrorHandler requestHandler;

    public CatchErrorRequestHandler(ExceptionErrorHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        try {
            requestHandler.handle(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
