package com.mycompany.smart.beee.house.servlet;

import com.mycompany.smart.beee.house.dao.DatabaseConnection;
import com.mycompany.smart.beee.house.dao.UserDAO;
import com.mycompany.smart.beee.house.entity.User;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            // Récupérer tous les utilisateurs
            try {
                List<User> users = userDAO.findAll();
                response.getWriter().write(gson.toJson(users));
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Erreur lors de la récupération des utilisateurs\"}");
            }
        } else {
            // Récupérer un utilisateur spécifique
            try {
                Long id = Long.parseLong(pathInfo.substring(1));
                User user = userDAO.findById(id);
                if (user != null) {
                    response.getWriter().write(gson.toJson(user));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write("{\"error\": \"Utilisateur non trouvé\"}");
                }
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"ID d'utilisateur invalide\"}");
            } catch (SQLException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Erreur lors de la récupération de l'utilisateur\"}");
            }
        }
    }
}
