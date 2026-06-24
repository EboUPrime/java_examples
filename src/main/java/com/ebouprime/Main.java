package com.ebouprime;


import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.config.RoutesConfig;

import java.util.ArrayList;
import java.util.List;


class HttpResponse {
    private int status;
    private Object data;

    public HttpResponse(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}

class HttpErrorResponse {
    private int status;
    private String message;

    public HttpErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

public class Main {
    List<User> list = new ArrayList<>();
    {
        list.add(new User(1, "ebouprime", "info@ebouprime.com"));
    }

    public String initializeHomePage() {
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
        homepage.append("</style>\n</head>\n<body>\n<div class=\"wrap\">\n<h1>REST API mit Javalin</h1>");
        // Get Examples
        homepage.append("<p class=\"lead\">Beispiele für REST API Endpunkte und HTTP‑Statuscodes.</p>");
        homepage.append("<h2>GET Endpunkte</h2>");


        homepage.append("<section class=\"meta\">");
        homepage.append("<h3>Beispiele für erfolgreiche GET-Anfragen</h3>");
        homepage.append("<h4>Query-Parameter</h4>");
        homepage.append("<ul>");
        homepage.append("<li>");
        homepage.append("Liste aller Benutzer (200)");
        homepage.append("<br>");
        homepage.append("<a target=_blank href=\"/users\">GET /users</a>");
        homepage.append("</li>");
        homepage.append("<li>");
        homepage.append("Benutzer nach ID abrufen (200)");
        homepage.append("<br>");
        homepage.append("<a target=_blank href=\"/user?id=1\">GET /user?id=1</a>");
        homepage.append("</li>");
        homepage.append("</ul>");

        homepage.append("<h4>Path-Parameter</h4>");
        homepage.append("<ul>");
        homepage.append("<li>");
        homepage.append("Benutzer nach ID abrufen (200)");
        homepage.append("<br>");
        homepage.append("<a target=_blank href=\"/user/1\">GET /user/1</a>");
        homepage.append("</li>");
        homepage.append("</ul>");

        homepage.append("</section>");


        homepage.append("<section class=\"meta\">");
        homepage.append("<h3>Beispiele für fehlerhafte GET-Anfragen</h3>");
        homepage.append("<ul>");
        homepage.append("<li>");
        homepage.append("Benutzer nach ID abrufen (404)");
        homepage.append("<br>");
        homepage.append("<a target=_blank href=\"/user?id=999\">GET /user?id=999</a></li>");
        homepage.append("<li>");
        homepage.append("Benutzer nach ID abrufen (400)");
        homepage.append("<br>");
        homepage.append("<a target=_blank href=\"/user\">GET /user</a></li>");
        homepage.append("</ul>");
        homepage.append("</section>");



        homepage.append("</div>\n</body>\n</html>");

        return homepage.toString();
    }

    public void initializeGetEndpoint(RoutesConfig routes) {

        routes.get("/", ctx -> {
            ctx.contentType("text/html; charset=utf-8");
            ctx.result(initializeHomePage());
        });

        // List users (200)
        routes.get("/users", ctx -> {
            ctx.contentType("application/json; charset=utf-8");
            HttpResponse response = new HttpResponse(200, list);
            ctx.status(200).json(response);
        });

        // GET /user?id=... or redirects to /users
        routes.get("/user", ctx -> {
            String idQ = ctx.queryParam("id");
            if (idQ == null) {
                HttpErrorResponse errorResponse = new HttpErrorResponse(400, "Missing id parameter");
                ctx.status(400).json(errorResponse);
                return;
            }
            try {
                int iid = Integer.parseInt(idQ);
                User found = list.stream().filter(u -> u.getId() == iid).findFirst().orElse(null);
                if (found == null) {
                    HttpErrorResponse errorResponse = new HttpErrorResponse(404, "User not found");
                    ctx.status(404).json(errorResponse);
                } else {
                    ctx.contentType("application/json; charset=utf-8");
                    HttpResponse response = new HttpResponse(200, found);
                    ctx.status(200).json(response);
                }
            } catch (NumberFormatException nfe) {
                HttpErrorResponse errorResponse = new HttpErrorResponse(400, "Invalid id parameter");
                ctx.status(400).json(errorResponse);
            }
        });

        // GET /user/{id} (Path Parameter)
        routes.get("/user/{id}", ctx -> {
            String idPath = ctx.pathParam("id");
            try {
                int iid = Integer.parseInt(idPath);
                User found = list.stream().filter(u -> u.getId() == iid).findFirst().orElse(null);
                if (found == null) {
                    HttpErrorResponse errorResponse = new HttpErrorResponse(404, "User not found");
                    ctx.status(404).json(errorResponse);
                } else {
                    ctx.contentType("application/json; charset=utf-8");
                    HttpResponse response = new HttpResponse(200, found);
                    ctx.status(200).json(response);
                }
            } catch (NumberFormatException nfe) {
                HttpErrorResponse errorResponse = new HttpErrorResponse(400, "Invalid id parameter");
                ctx.status(400).json(errorResponse);
            }
        });
    }


    public void configuration(JavalinConfig config) {
        // Hier können Sie die Logik für die Konfiguration der Routen implementieren
        System.out.println("Routen konfiguriert.");            // Serve static files from the classpath (src/main/resources)
        try {
            config.staticFiles.add("/");
        } catch (Exception ignore) {
            // fallback: some Javalin versions accept addFolder or addResource; if this fails, app will still work with manual routes
        }
        RoutesConfig routes = config.routes;
        initializeGetEndpoint(routes);
    }

    public static void main(String[] args) {
        Main app = new Main();
        Javalin.create(app::configuration).start(7070);
        System.out.println("Server started on port 7070 ...");
        System.out.println("Press Ctrl+C to stop the server.");
        System.out.println("Visit http://localhost:7070 to see the result.");
    }
}
