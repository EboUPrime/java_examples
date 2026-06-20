package com.ebouprime;


import io.javalin.Javalin;
import io.javalin.config.RoutesConfig;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "ebouprime", "info@ebouprime.com"));

        Javalin.create(config -> {
            // Serve static files from the classpath (src/main/resources)
            // With this, /example.html placed in src/main/resources will be served directly.
            try {
                config.staticFiles.add("/");
            } catch (Exception ignore) {
                // fallback: some Javalin versions accept addFolder or addResource; if this fails, app will still work with manual routes
            }
            RoutesConfig routes = config.routes;
            StringBuilder homepage = new StringBuilder();
            homepage.append("<!doctype html>");
            homepage.append("<html lang=\"de\">\n<head>\n<meta charset=\"utf-8\">\n<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n<title>REST API – Beispiele & HTTP‑Statuscodes</title>\n<style>");
            homepage.append("body{font-family:Inter,Segoe UI,Arial,sans-serif;margin:0;padding:24px;background:#f7f8fb;color:#222}");
            homepage.append(".wrap{max-width:1000px;margin:0 auto;background:#fff;padding:20px;border-radius:8px;box-shadow:0 6px 18px rgba(20,20,30,0.06)}");
            homepage.append("h1{margin:0 0 8px 0;color:#10316b}");
            homepage.append("p.lead{color:#555;margin-top:0}");
            homepage.append("ul{padding-left:1.1rem}");
            homepage.append("a.btn{display:inline-block;margin-top:8px;padding:8px 12px;border-radius:6px;background:#1066d5;color:#fff;text-decoration:none}");
            homepage.append(".meta{font-size:13px;color:#666;margin-top:12px}");
            homepage.append("</style>\n</head>\n<body>\n<div class=\"wrap\">\n<h1>REST API mit Javalin</h1>\n<p class=\"lead\">Demonstration von HTTP‑Statuscodes und einfachen Benutzer‑Beispielen. Interaktive Beispiele: <a class=\"btn\" href=\"/example.html\">Web‑Beispiele öffnen</a></p>\n<hr>");
            homepage.append("<h2>Kurzübersicht</h2>\n<ul>");
            homepage.append("<li><strong>GET /users</strong> — alle Benutzer (200). <a href=\"/users\">/users</a></li>");
            homepage.append("<li><strong>GET /user?id=1</strong> — Benutzer per Query (200) / 404 wenn nicht vorhanden</li>");
            homepage.append("<li><strong>GET /user/{id}</strong> — Benutzer per Pfad; JSON-Antwort (200) oder 404</li>");
            homepage.append("<li><strong>POST /user</strong> — Erstellen (201) oder 409 bei Konflikt</li>");
            homepage.append("<li><strong>PUT /user/{id}</strong> — Ersetzen (200) oder Erstellen (201)</li>");
            homepage.append("<li><strong>PATCH /user/{id}</strong> — Partielle Änderung (200) oder 404</li>");
            homepage.append("<li><strong>DELETE /user/{id}</strong> — Löschen (204) oder 404</li>");
            homepage.append("</ul>");
            homepage.append("<p class=\"meta\">Weitere Status‑Demos sind über die einzelnen Endpunkte (z. B. /status/200) erreichbar.</p>");
            homepage.append("</div>\n</body>\n</html>");

            routes.get("/", ctx -> {
                ctx.contentType("text/html; charset=utf-8");
                ctx.result(homepage.toString());
            });

            // List users (200)
            routes.get("/users", ctx -> {
                StringBuilder builder = new StringBuilder();
                list.forEach(u -> builder.append(u.getId()).append(" - ").append(u.getName()).append(" - ").append(u.getEmail()).append("\n"));
                ctx.contentType("text/plain");
                ctx.result(builder.toString());
            });

            // GET /user?id=... or redirects to /users
            routes.get("/user", ctx -> {
                String idQ = ctx.queryParam("id");
                if (idQ == null) {
                    // no id -> show same as /users
                    ctx.redirect("/users");
                    return;
                }
                try {
                    int iid = Integer.parseInt(idQ);
                    User found = list.stream().filter(u -> u.getId() == iid).findFirst().orElse(null);
                    if (found == null) {
                        ctx.status(404).result("User not found");
                    } else {
                        ctx.json(found);
                    }
                } catch (NumberFormatException nfe) {
                    ctx.status(400).result("Invalid id");
                }
            });

            // POST /user - create new user; returns 201 or 409 if id exists
            routes.post("/user", ctx -> {
                // Try JSON body first
                User bodyUser = null;
                try {
                    if (ctx.contentType() != null && ctx.contentType().contains("application/json")) {
                        bodyUser = ctx.bodyAsClass(User.class);
                    }
                } catch (Exception ignore) {
                }

                String idStr = null, name = null, email = null;
                if (bodyUser != null) {
                    idStr = bodyUser.getId() == null ? null : String.valueOf(bodyUser.getId());
                    name = bodyUser.getName();
                    email = bodyUser.getEmail();
                }

                if (idStr == null) idStr = ctx.formParam("id");
                if (name == null) name = ctx.formParam("name");
                if (email == null) email = ctx.formParam("email");

                if (idStr == null) idStr = ctx.queryParam("id");
                if (name == null) name = ctx.queryParam("name");
                if (email == null) email = ctx.queryParam("email");

                // Fallback: parse raw body like "id=2&name=foo&email=bar@x.com"
                if ((idStr == null || name == null || email == null) && ctx.body() != null && !ctx.body().isBlank()) {
                    Map<String, String> parsed = parseUrlEncodedBody(ctx.body());
                    if (idStr == null) idStr = parsed.get("id");
                    if (name == null) name = parsed.get("name");
                    if (email == null) email = parsed.get("email");
                }

                if (idStr == null || name == null || email == null) {
                    ctx.status(400).result("Missing parameters. Provide id, name, email as JSON, form or query parameters.");
                    return;
                }

                try {
                    int id = Integer.parseInt(idStr);
                    boolean exists = list.stream().anyMatch(u -> u.getId() == id);
                    if (exists) {
                        ctx.status(409).result("User with id " + id + " already exists");
                        return;
                    }
                    User user = new User(id, name, email);
                    list.add(user);
                    ctx.status(201);
                    ctx.header("Location", "/user/" + id);
                    ctx.json(user);
                } catch (NumberFormatException ex) {
                    ctx.status(400).result("Invalid id value");
                }
            });

            // PUT /user/{id} - replace or create
            routes.put("/user/{id}", ctx -> {
                String id = ctx.pathParam("id");
                try {
                    int iid = Integer.parseInt(id);
                    User bodyUser = null;
                    try { if (ctx.contentType() != null && ctx.contentType().contains("application/json")) bodyUser = ctx.bodyAsClass(User.class); } catch (Exception ignore) {}

                    String name = bodyUser != null ? bodyUser.getName() : ctx.formParam("name");
                    String email = bodyUser != null ? bodyUser.getEmail() : ctx.formParam("email");

                    if (name == null) name = ctx.queryParam("name");
                    if (email == null) email = ctx.queryParam("email");

                    if (name == null || email == null) {
                        ctx.status(400).result("Missing name or email for PUT");
                        return;
                    }

                    User found = list.stream().filter(u -> u.getId() == iid).findFirst().orElse(null);
                    if (found == null) {
                        User created = new User(iid, name, email);
                        list.add(created);
                        ctx.status(201).header("Location", "/user/" + iid).json(created);
                    } else {
                        found.setName(name);
                        found.setEmail(email);
                        ctx.status(200).json(found);
                    }

                } catch (NumberFormatException nfe) {
                    ctx.status(400).result("Invalid id");
                }
            });

            // PATCH /user/{id} - partial update
            routes.patch("/user/{id}", ctx -> {
                String id = ctx.pathParam("id");
                try {
                    int iid = Integer.parseInt(id);
                    User found = list.stream().filter(u -> u.getId() == iid).findFirst().orElse(null);
                    if (found == null) {
                        ctx.status(404).result("User not found");
                        return;
                    }

                    // try JSON
                    try {
                        User patch = ctx.bodyAsClass(User.class);
                        if (patch.getName() != null) found.setName(patch.getName());
                        if (patch.getEmail() != null) found.setEmail(patch.getEmail());
                    } catch (Exception ignore) {
                        // fallback to form/query params
                        String name = ctx.formParam("name");
                        String email = ctx.formParam("email");
                        if (name == null) name = ctx.queryParam("name");
                        if (email == null) email = ctx.queryParam("email");
                        if (name != null) found.setName(name);
                        if (email != null) found.setEmail(email);
                    }

                    ctx.status(200).json(found);

                } catch (NumberFormatException nfe) {
                    ctx.status(400).result("Invalid id");
                }
            });

            // DELETE /user/{id} - removes user
            routes.delete("/user/{id}", ctx -> {
                String id = ctx.pathParam("id");
                try {
                    int iid = Integer.parseInt(id);
                    User found = list.stream().filter(u -> u.getId() == iid).findFirst().orElse(null);
                    if (found == null) {
                        ctx.status(404).result("User not found");
                        return;
                    }
                    list.remove(found);
                    ctx.status(204); // No Content
                } catch (NumberFormatException nfe) {
                    ctx.status(400).result("Invalid id");
                }
            });

            // Extra user-related demo endpoints for various statuses
            routes.get("/user/unauthorized", ctx -> ctx.status(401).result("401 Unauthorized - please authenticate"));
            routes.get("/user/forbidden", ctx -> ctx.status(403).result("403 Forbidden - access denied"));
            routes.post("/user/invalid", ctx -> ctx.status(422).result("422 Unprocessable Entity - validation failed"));
            routes.post("/user/error", ctx -> ctx.status(500).result("500 Internal Server Error - simulated error"));

            // Simple endpoints to demonstrate status codes explicitly
            // 1xx: Informational (usually not used as final responses, shown here for demonstration)
            routes.get("/status/100", ctx -> ctx.status(100).result("100 Continue (demonstration)"));
            routes.get("/status/101", ctx -> ctx.status(101).result("101 Switching Protocols"));
            routes.get("/status/102", ctx -> ctx.status(102).result("102 Processing"));
            routes.get("/status/103", ctx -> ctx.status(103).result("103 Early Hints"));

            // 2xx: Success
            routes.get("/status/200", ctx -> ctx.status(200).result("200 OK"));
            routes.get("/status/201", ctx -> ctx.status(201).result("201 Created"));
            routes.get("/status/202", ctx -> ctx.status(202).result("202 Accepted"));
            routes.get("/status/204", ctx -> ctx.status(204)); // No Content

            // 3xx: Redirection (set Location header where appropriate)
            routes.get("/status/301", ctx -> ctx.header("Location", "/").status(301).result("301 Moved Permanently -> /") );
            routes.get("/status/302", ctx -> ctx.header("Location", "/").status(302).result("302 Found -> /") );
            routes.get("/status/303", ctx -> ctx.header("Location", "/").status(303).result("303 See Other -> /") );
            routes.get("/status/304", ctx -> ctx.status(304)); // Not Modified
            routes.get("/status/307", ctx -> ctx.header("Location", "/").status(307).result("307 Temporary Redirect -> /") );
            routes.get("/status/308", ctx -> ctx.header("Location", "/").status(308).result("308 Permanent Redirect -> /") );

            // 4xx: Client errors
            routes.get("/status/400", ctx -> ctx.status(400).result("400 Bad Request"));
            routes.get("/status/401", ctx -> ctx.status(401).result("401 Unauthorized"));
            routes.get("/status/403", ctx -> ctx.status(403).result("403 Forbidden"));
            routes.get("/status/404", ctx -> ctx.status(404).result("404 Not Found"));
            routes.get("/status/405", ctx -> ctx.status(405).result("405 Method Not Allowed"));
            routes.get("/status/409", ctx -> ctx.status(409).result("409 Conflict"));
            routes.get("/status/410", ctx -> ctx.status(410).result("410 Gone"));
            routes.get("/status/422", ctx -> ctx.status(422).result("422 Unprocessable Entity"));
            routes.get("/status/429", ctx -> ctx.status(429).result("429 Too Many Requests"));

            // 5xx: Server errors
            routes.get("/status/500", ctx -> ctx.status(500).result("500 Internal Server Error"));
            routes.get("/status/501", ctx -> ctx.status(501).result("501 Not Implemented"));
            routes.get("/status/502", ctx -> ctx.status(502).result("502 Bad Gateway"));
            routes.get("/status/503", ctx -> ctx.status(503).result("503 Service Unavailable"));
            routes.get("/status/504", ctx -> ctx.status(504).result("504 Gateway Timeout"));

        }).start(7070);

        System.out.println("Server started on port 7070 ...");
        System.out.println("Press Ctrl+C to stop the server.");
        System.out.println("Visit http://localhost:7070 to see the result.");
    }

    // Kleine Hilfsfunktion zum Parsen von x-www-form-urlencoded Body
    private static Map<String, String> parseUrlEncodedBody(String body) {
        Map<String, String> map = new HashMap<>();
        if (body == null || body.isEmpty()) return map;
        String[] parts = body.split("&");
        for (String p : parts) {
            String[] kv = p.split("=", 2);
            if (kv.length == 2) {
                String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                String val = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                map.put(key, val);
            }
        }
        return map;
    }
}
