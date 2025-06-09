package com.mycompany.smart.beee.house.servlet;

import com.mycompany.smart.beee.house.dao.DatabaseConnection;
import com.mycompany.smart.beee.house.dao.PlanningVisiteDAO;
import com.mycompany.smart.beee.house.dao.RucheDAO;
import com.mycompany.smart.beee.house.dao.UserDAO;
import com.mycompany.smart.beee.house.dao.SiteApicultureDAO;
import com.mycompany.smart.beee.house.entity.PlanningVisite;
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

@WebServlet("/planning/*")
public class PlanningVisiteServlet extends HttpServlet {
    private PlanningVisiteDAO planningDAO;
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        try {
            Connection connection = DatabaseConnection.getConnection();
            UserDAO userDAO = new UserDAO(connection);
            SiteApicultureDAO siteDAO = new SiteApicultureDAO(connection, null);
            RucheDAO rucheDAO = new RucheDAO(connection, userDAO, siteDAO);
            planningDAO = new PlanningVisiteDAO(connection, rucheDAO, userDAO);
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
            response.getWriter().write("{\"error\": \"ID d'agent requis\"}");
            return;
        }

        try {
            Long agentId = Long.parseLong(pathInfo.substring(1));
            List<PlanningVisite> plannings = planningDAO.findByAgent(agentId);
            response.getWriter().write(gson.toJson(plannings));
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"ID d'agent invalide\"}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la récupération des plannings\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            PlanningVisite planning = gson.fromJson(request.getReader(), PlanningVisite.class);
            planningDAO.create(planning);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write(gson.toJson(planning));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Erreur lors de la création du planning\"}");
        }
    }
}
