package com.mycompany.smart.beee.house.servlet;

import com.mycompany.smart.beee.house.dao.DatabaseConnection;
import com.mycompany.smart.beee.house.dao.UserDAO;
import com.mycompany.smart.beee.house.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Servlet handling user authentication (login).
 * This servlet receives login requests, verifies credentials, and returns user information.
 */
@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UserDAO userDAO;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            userDAO = new UserDAO(connection);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'initialisation de la connexion à la base de données", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Action non spécifiée\"}");
            return;
        }

        switch (pathInfo) {
            case "/login":
                handleLogin(request, response);
                break;
            case "/register":
                handleRegister(request, response);
                break;
            default:
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Action non reconnue\"}");
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            JsonObject loginData = gson.fromJson(request.getReader(), JsonObject.class);
            String email = loginData.get("email").getAsString();
            String password = loginData.get("password").getAsString();

            User user = userDAO.authenticate(email, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                
                JsonObject responseJson = new JsonObject();
                responseJson.addProperty("success", true);
                responseJson.addProperty("message", "Connexion réussie");
                responseJson.add("user", gson.toJsonTree(user));
                
                response.getWriter().write(gson.toJson(responseJson));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"Email ou mot de passe incorrect\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la connexion\"}");
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        try {
            User newUser = gson.fromJson(request.getReader(), User.class);
            
            // Vérifier si l'utilisateur existe déjà en essayant de l'authentifier
            if (userDAO.authenticate(newUser.getEmail(), newUser.getPassword()) != null) {
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                response.getWriter().write("{\"error\": \"Cet email est déjà utilisé\"}");
                return;
            }

            userDAO.create(newUser);
            
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("success", true);
            responseJson.addProperty("message", "Inscription réussie");
            responseJson.add("user", gson.toJsonTree(newUser));
            
            response.getWriter().write(gson.toJson(responseJson));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de l'inscription\"}");
        }
    }

    /**
     * Handle GET requests to check if user is authenticated
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        JsonObject jsonResponse = new JsonObject();

        if (session != null && session.getAttribute("userId") != null) {
            Long userId = (Long) session.getAttribute("userId");
            String userRole = (String) session.getAttribute("userRole");

            jsonResponse.addProperty("authenticated", true);
            jsonResponse.addProperty("userId", userId);
            jsonResponse.addProperty("userRole", userRole);
        } else {
            jsonResponse.addProperty("authenticated", false);
        }

        response.getWriter().write(jsonResponse.toString());
    }
}