package com.ebouprime;

import io.javalin.config.RoutesConfig;
import java.util.Map;

public class UserController {
    public static void register(RoutesConfig routes) {

        routes.get("/users", ctx -> ctx.json(UserService.getAll()));

        routes.get("/users/{id}", ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("error", "Invalid id"));
                return;
            }
            User user = UserService.getById(id);
            if (user == null) {
                ctx.status(404).json(Map.of("error", "User not found"));
            } else {
                ctx.json(user);
            }
        });

        routes.post("/users", ctx -> {
            UserCreateDto dto = ctx.bodyAsClass(UserCreateDto.class);
            if (dto == null || dto.getName() == null || dto.getEmail() == null) {
                ctx.status(400).json(Map.of("error", "name and email required"));
                return;
            }
            User user = new User(null, dto.getName(), dto.getEmail());
            User created = UserService.create(user);
            ctx.status(201).json(created);
        });

        routes.delete("/users/{id}", ctx -> {
            int id;
            try {
                id = Integer.parseInt(ctx.pathParam("id"));
            } catch (NumberFormatException e) {
                ctx.status(400).json(Map.of("error", "Invalid id"));
                return;
            }
            boolean removed = UserService.delete(id);
            if (removed) {
                ctx.status(204);
            } else {
                ctx.status(404).json(Map.of("error", "User not found"));
            }
        });
    }
}

