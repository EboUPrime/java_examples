package com.ebouprime;


import io.javalin.Javalin;
import io.javalin.config.RoutesConfig;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "ebouprime", "info@ebouprime.com"));

        Javalin app = Javalin.create(config -> {
            RoutesConfig routes = config.routes;
            StringBuilder homepage = new StringBuilder();
            homepage.append("REST API with Javalin");
            // Beispiel des HTTP-Statuscodes 200
            homepage.append("\n");
            homepage.append("HTTP-Codes");
            homepage.append("\n");
            homepage.append("200 OK");
            homepage.append("\nhttp://localhost:7070/users");
            homepage.append("\nThis Example fetches all users from the API");
            homepage.append("\n");
            homepage.append("201 Created");
            homepage.append("\nhttp://localhost:7070/user?id=1&name=givara&email=test@ebouprime.com");

            routes.get("/", ctx -> {
                ctx.result(homepage.toString());
            });
            routes.get("/users", ctx -> {
                StringBuilder builder = new StringBuilder();
                list.forEach(u -> {
                    builder.append(u.getId()).append(" - ").append(u.getName()).append(" - ").append(u.getEmail()).append("\n");
                });
                ctx.result(builder.toString());
            });
            routes.get("/user/{id}", ctx -> {
                String id = ctx.pathParam("id");
                User user = new User(Integer.parseInt(id), "User " + id, "user" + id + "@example.com");
                ctx.json(user);
            });
            routes.post("/user", ctx -> {
                String body = ctx.body();
                String[] parts = body.split("&");
                String id = parts[0].split("=")[1];
                String name = parts[1].split("=")[1];
                String email = parts[2].split("=")[1];
                User user = new User(Integer.parseInt(id), name, email);
                list.add(user);
                ctx.status(201); // Status 201
                StringBuilder result = new StringBuilder();
                result.append("Created user with id ").append(id).append("\n");
                result.append("See all Users: http://localhost:7070/users");
                ctx.result(result.toString());
            });
        }).start(7070);

        System.out.println("Server started on port 7070 ...");
        System.out.println("Press Ctrl+C to stop the server.");
        System.out.println("Visit http://localhost:7070 to see the result.");
    }
}