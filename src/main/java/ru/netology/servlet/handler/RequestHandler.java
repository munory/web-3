package ru.netology.servlet.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface RequestHandler {

    void handle(HttpServletRequest req, HttpServletResponse resp);

}
