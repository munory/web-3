package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;
import ru.netology.servlet.handler.CatchErrorRequestHandler;
import ru.netology.servlet.handler.HttpMethod;
import ru.netology.servlet.handler.ServerRequestHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends HttpServlet {

    final static String POST_PATH = "/api/posts";

    private PostController controller;

    final private List<ServerRequestHandler> serverRequestHandlers = new ArrayList<>();

    @Override
    public void init() {

        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);

        serverRequestHandlers.add(new ServerRequestHandler(
                HttpMethod.GET,
                POST_PATH,
                false,
                new CatchErrorRequestHandler((req, resp) -> {
                    controller.all(resp);
                })));

        serverRequestHandlers.add(new ServerRequestHandler(
                HttpMethod.GET,
                POST_PATH + "/\\d+",
                true,
                new CatchErrorRequestHandler((req, resp) -> {
                    final var id = Long.parseLong(req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1));
                    controller.getById(id, resp);
                })));

        serverRequestHandlers.add(new ServerRequestHandler(
                HttpMethod.POST,
                POST_PATH,
                false,
                new CatchErrorRequestHandler((req, resp) -> {
                    controller.save(req.getReader(), resp);
                })));

        serverRequestHandlers.add(new ServerRequestHandler(
                HttpMethod.DELETE,
                POST_PATH + "/\\d+",
                true,
                new CatchErrorRequestHandler((req, resp) -> {
                    final var id = Long.parseLong(req.getRequestURI().substring(req.getRequestURI().lastIndexOf("/") + 1));
                    controller.removeById(id, resp);
                })));

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // если деплоились в root context, то достаточно этого

        boolean find = false;

        for (var handler : serverRequestHandlers) {
            if (
                    handler.getHttpMethod().name().equals(req.getMethod())
                            && (
                            !handler.isMatches() && handler.getPath().equals(req.getRequestURI()) ||
                                    handler.isMatches() && req.getRequestURI().matches(handler.getPath())
                    )
            ) {
                handler.getRequestHandler().handle(req, resp);
                find = true;
                break;
            }
        }

        if (!find) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}

