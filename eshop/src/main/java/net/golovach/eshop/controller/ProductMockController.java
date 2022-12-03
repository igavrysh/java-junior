package net.golovach.eshop.controller;

import net.golovach.eshop.dao.ProductDao;
import net.golovach.eshop.dao.exception.DaoSystemException;
import net.golovach.eshop.dao.exception.NoSuchEntityException;
import net.golovach.eshop.dao.impl.mock.ProductDaoMock;
import net.golovach.eshop.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductMockController extends HttpServlet {

    public static final String PARAM_ID = "id";
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "product";
    public static final String PAGE_OK = "product.jsp";
    public static final String PAGE_ERROR = "error.jsp";
    private ProductDao productDao = new ProductDaoMock();

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter(PARAM_ID);
        if (idStr != null) {
            try {
                Integer id = Integer.valueOf(idStr);
                Product model = productDao.selectById(id);
                request.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, model);
                request.getRequestDispatcher(PAGE_OK).forward(request, response);
                return;
            } catch (NumberFormatException | NoSuchEntityException | DaoSystemException e) {
                /*NOP*/
            }
        }
        //FAIL
        response.sendRedirect(PAGE_ERROR);
    }}
