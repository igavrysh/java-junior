package net.golovach.eshop.controller;

import net.golovach.eshop.dao.ProductDao;
import net.golovach.eshop.dao.exception.DaoSystemException;
import net.golovach.eshop.dao.exception.NoSuchEntityException;
import net.golovach.eshop.dao.impl.jdbc.tx.TransactionManager;
import net.golovach.eshop.entity.Product;
import net.golovach.eshop.inject.DependencyInjectionServlet;
import net.golovach.eshop.inject.Inject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Admin on 31.01.2017.
 */
public class ProductControllerExternalTx extends DependencyInjectionServlet {
    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "product.jsp";
    public static final String PAGE_ERROR = "error.jsp";


    @Inject("txManager")
    TransactionManager txManager;

    @Inject("productDao")
    ProductDao productDao;


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(PARAM_ID);
        if (idStr != null) {
            try {
                System.out.println("В try ProductController!!!!!!!!!!!!!!!!!!!!!");
                Integer id = Integer.valueOf(idStr);
                Product model  = txManager.doInTransaction(() -> productDao.selectById(id));
                request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                // OK
                System.out.println("В try ProductController 222!!!!!!!!!!!!!!!!!!!!!");

                RequestDispatcher dispatcher =  request.getRequestDispatcher(PAGE_OK);
                dispatcher.forward(request, response);
                return;
            } catch (Exception e/*| NumberFormatException | NoSuchEntityException | DaoSystemException e*/ ) {
                /*NOP*/
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }
}
