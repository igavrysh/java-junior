package net.golovach.eshop.controller.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher page = req.getRequestDispatcher("/a/b/c.jsp");
        page.forward(req, resp);

        page.include(req, resp);
    }
}
