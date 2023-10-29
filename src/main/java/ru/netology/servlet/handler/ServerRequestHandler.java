package ru.netology.servlet.handler;

import ru.netology.servlet.handler.HttpMethod;
import ru.netology.servlet.handler.RequestHandler;

public class ServerRequestHandler {

    final private HttpMethod httpMethod;

    final private String path;

    final private boolean matches;

    final private RequestHandler requestHandler;

    public ServerRequestHandler(HttpMethod httpMethod, String path, boolean matches, RequestHandler requestHandler) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.matches = matches;
        this.requestHandler = requestHandler;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public RequestHandler getRequestHandler() {
        return requestHandler;
    }

    public boolean isMatches() {
        return matches;
    }

}
