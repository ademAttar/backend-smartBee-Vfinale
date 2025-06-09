package com.mycompany.smart.beee.house.servlet;

import com.mycompany.smart.beee.house.dao.DatabaseConnection;
import com.mycompany.smart.beee.house.dao.MesureDAO;
import com.mycompany.smart.beee.house.dao.RucheDAO;
import com.mycompany.smart.beee.house.dao.UserDAO;
import com.mycompany.smart.beee.house.dao.SiteApicultureDAO;
import com.mycompany.smart.beee.house.entity.Mesure;
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

@WebServlet("/mesure/*")
public class MesureServlet extends HttpServlet {
    private MesureDAO mesureDAO;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            SiteApicultureDAO siteDAO = new SiteApicultureDAO(connection, null);
            RucheDAO rucheDAO = new RucheDAO(connection, userDAO, siteDAO);
            mesureDAO = new MesureDAO(connection, rucheDAO);
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
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"ID de ruche requis\"}");
            return;
        }

        try {
            Long rucheId = Long.parseLong(pathInfo.substring(1));
            List<Mesure> mesures = mesureDAO.findByRuche(rucheId);
            response.getWriter().write(gson.toJson(mesures));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"ID de ruche invalide\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la récupération des mesures\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Mesure mesure = gson.fromJson(request.getReader(), Mesure.class);
            mesureDAO.create(mesure);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(mesure));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la création de la mesure\"}");
        }
    }
}
