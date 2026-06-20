package com.ebouprime;


import io.javalin.Javalin;
import io.javalin.config.RoutesConfig;


public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            RoutesConfig routes = config.routes;
            routes.get("/", ctx -> {
                ctx.result("Hello, World!");
            });
            UserController.register(routes);
        }).start(7070);

        System.out.println("Server started on port 7070 ...");
        System.out.println("Press Ctrl+C to stop the server.");
        System.out.println("Visit http://localhost:7070 to see the result.");
    }
}