package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import ru.netology.javaConfig.JavaConfig;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    PostController postController;
    public static final String API_POSTS = "/api/posts";
    public static final String API_POSTS_ID = "/api/posts/\\d+";
    public static final String STR = "/";
    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String DELETE_METHOD = "DELETE";

    @Override
    public void init() {
        final var context = new AnnotationConfigApplicationContext(JavaConfig.class);
        postController = context.getBean(PostController.class);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        final var path = req.getRequestURI();
        final var method = req.getMethod();


        if (method.equals(GET_METHOD) && path.equals(API_POSTS)) {
            postController.all(response);
            return;
        }
        if (method.equals(GET_METHOD) && path.matches(API_POSTS_ID)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(STR) + 1));
            postController.getById(id, response);
            return;
        }
        if (method.equals(POST_METHOD) && path.equals(API_POSTS)) {
            postController.save(req.getReader(), response);
        }
        if (method.equals(DELETE_METHOD) && path.matches(API_POSTS_ID)) {
            final var id = Long.parseLong(path.substring(path.lastIndexOf(STR) + 1));
            postController.removeById(id, response);
        }

    }
}
