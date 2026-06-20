package com.ebouprime;


import io.javalin.Javalin;
import io.javalin.config.RoutesConfig;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();


        Javalin app = Javalin.create(config -> {
            RoutesConfig routes = config.routes;
            routes.get("/", ctx -> {
                ctx.result("REST API with Javalin");
            });
            routes.get("/users", ctx -> {
                StringBuilder builder = new StringBuilder();
                list.forEach(u -> builder.append(u.getName()).append("; "));
            });
            routes.get("/user/{id}", ctx -> {
                String id = ctx.pathParam("id");
                User user = new User(Integer.parseInt(id), "User " + id, "user" + id + "@example.com");
                ctx.json(user);
            });
        }).start(7070);

        System.out.println("Server started on port 7070 ...");
        System.out.println("Press Ctrl+C to stop the server.");
        System.out.println("Visit http://localhost:7070 to see the result.");
    }
}