package net.golovach.eshop.controller;

import net.golovach.eshop.dao.ProductDao;
import net.golovach.eshop.dao.exception.DaoSystemException;
import net.golovach.eshop.dao.exception.NoSuchEntityException;
import net.golovach.eshop.entity.Product;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static net.golovach.eshop.controller.ProductController.PARAM_ID;
import static net.golovach.eshop.controller.ProductController.PAGE_ERROR;
import static net.golovach.eshop.controller.ProductController.PAGE_OK;
import static net.golovach.eshop.controller.ProductController.ATTRIBUTE_MODEL_TO_VIEW;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class ProductControllerTest {

    private ProductController controller;

    @Before
    public void setUp() {
        this.controller  = new ProductController();
    }

    @Test
    public void test_no_param() throws ServletException, IOException {
        // init
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter(ProductController.PARAM_ID)).thenReturn(null);
        HttpServletResponse response = mock(HttpServletResponse.class);

        // use
        controller.doGet(request, response);

        // check
        verify(request).getParameter(PARAM_ID);
        verify(response).sendRedirect(PAGE_ERROR);
        verifyNoMoreInteractions(request, response);
    }

    @Test
    public void test_bad_param() throws ServletException, IOException {
        // init
        HttpServletRequest request = mock(HttpServletRequest.class);
        // product.do?id=abc
        when(request.getParameter(ProductController.PARAM_ID)).thenReturn("abc");
        HttpServletResponse response = mock(HttpServletResponse.class);

        // use
        controller.doGet(request, response);

        // check
        verify(request).getParameter(PARAM_ID);
        verify(response).sendRedirect(PAGE_ERROR);
        verifyNoMoreInteractions(request, response);
    }

    @Test
    public void test_no_in_dao() throws ServletException, IOException, NoSuchEntityException, DaoSystemException {
        // init
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        ProductDao dao = mock(ProductDao.class);

        // product.do?id=abc
        when(dao.selectById(anyInt())).thenThrow(new NoSuchEntityException(""));
        when(request.getParameter(ProductController.PARAM_ID)).thenReturn("123");
        controller.productDao = dao;

        // use
        controller.doGet(request, response);

        // check
        verify(request).getParameter(PARAM_ID);
        verify(dao).selectById(123);
        verify(response).sendRedirect(PAGE_ERROR);
        verifyNoMoreInteractions(request, response, dispatcher, dao);
    }

    @Test
    public void test_dao_crashed() throws ServletException, IOException, NoSuchEntityException, DaoSystemException {
        // init
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        ProductDao dao = mock(ProductDao.class);

        when(request.getParameter(ProductController.PARAM_ID)).thenReturn("123");
        when(dao.selectById(anyInt())).thenThrow(new DaoSystemException(""));
        controller.productDao = dao;

        // use
        controller.doGet(request, response);

        // check
        verify(request).getParameter(PARAM_ID);
        verify(dao).selectById(123);
        verify(response).sendRedirect(PAGE_ERROR);
        verifyNoMoreInteractions(request, response, dispatcher, dao);
    }

    @Test
    public void test_ok() throws ServletException, IOException, NoSuchEntityException, DaoSystemException {
        // init
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        ProductDao dao = mock(ProductDao.class);
        Product product = new Product(123, "Paper");

        // product.do?id=abc
        when(request.getParameter(ProductController.PARAM_ID)).thenReturn("123");
        when(dao.selectById(anyInt())).thenReturn(product);
        when(request.getRequestDispatcher(PAGE_OK)).thenReturn(dispatcher);
        controller.productDao = dao;

        // use
        controller.doGet(request, response);

        // check
        verify(request).getParameter(PARAM_ID);
        verify(dao).selectById(123);
        verify(request).setAttribute(ATTRIBUTE_MODEL_TO_VIEW, product);
        verify(request).getRequestDispatcher(PAGE_OK);
        verify(dispatcher).forward(request, response);
        verifyNoMoreInteractions(request, response, dispatcher, dao);
    }
}
