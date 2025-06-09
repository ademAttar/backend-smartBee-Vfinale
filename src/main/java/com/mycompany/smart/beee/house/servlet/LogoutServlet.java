package com.mycompany.smart.beee.house.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet handling user logout
 * This servlet invalidates the user session and returns a success response
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private Gson gson = new Gson();
    
    /**
     * Handle POST requests for user logout
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        // Get the current session
        HttpSession session = request.getSession(false);
        JsonObject responseJson = new JsonObject();
        
        // If session exists, invalidate it
        if (session != null) {
            session.invalidate();
            responseJson.addProperty("success", true);
            responseJson.addProperty("message", "Déconnexion réussie");
        } else {
            // No active session found
            responseJson.addProperty("success", false);
            responseJson.addProperty("message", "Aucune session active");
        }
        
        // Send response
        response.getWriter().write(gson.toJson(responseJson));
    }
}