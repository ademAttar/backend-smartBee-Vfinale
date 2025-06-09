package com.mycompany.smart.beee.house.servlet;

import com.mycompany.smart.beee.house.dao.DatabaseConnection;
import com.mycompany.smart.beee.house.dao.SiteApicultureDAO;
import com.mycompany.smart.beee.house.entity.SiteApiculture;
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

@WebServlet("/site/*")
public class SiteApicultureServlet extends HttpServlet {
    private SiteApicultureDAO siteDAO;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            siteDAO = new SiteApicultureDAO(connection, null);
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
            response.getWriter().write("{\"error\": \"ID de ferme requis\"}");
            return;
        }

        try {
            Long fermeId = Long.parseLong(pathInfo.substring(1));
            List<SiteApiculture> sites = siteDAO.findByFerme(fermeId);
            response.getWriter().write(gson.toJson(sites));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"ID de ferme invalide\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la récupération des sites\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            SiteApiculture site = gson.fromJson(request.getReader(), SiteApiculture.class);
            siteDAO.create(site);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(site));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la création du site\"}");
        }
    }
}
