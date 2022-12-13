package com.igavrysh.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DisplayEmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DisplayEmployeeServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        int success = Integer.parseInt(request.getParameter("s"));
        if (success == 1) {
            request.setAttribute("result", "Employee Successfully Inserted");
        } else {
            request.setAttribute("result",
                    "Employee Not Inserted: " + request.getAttribute("error"));
        }
        RequestDispatcher view = request
                .getRequestDispatcher("displayEmployee.jsp");
        view.forward(request, response);
    }
}