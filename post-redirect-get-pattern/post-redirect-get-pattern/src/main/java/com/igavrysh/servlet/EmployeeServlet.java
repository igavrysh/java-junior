package com.igavrysh.servlet;

import com.igavrysh.exception.ApplicationException;
import com.igavrysh.to.Employee;
import com.igavrysh.dao.EmployeeDAO;
import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.DataSource;
import java.io.IOException;


public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name="jdbc/testDB")
    DataSource ds;

    public EmployeeServlet() {
        super();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        RequestDispatcher view = request.getRequestDispatcher("displayEmployee.jsp");
        view.forward(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {

        //Get Request parameters from form
        String empName = request.getParameter("employeeName");
        String deptName = request.getParameter("deptName");
        double salary = Double.parseDouble(request.getParameter("salary"));

        //Create Employee Object
        Employee employee = new Employee();
        employee.setEmployeeName(empName);
        employee.setSalary(salary);
        employee.setDeptName(deptName);

        //Invoke method in DAO class passing employee object
        EmployeeDAO empDAO = new EmployeeDAO(ds);

        int rows;
        int success = 0;
        try {
            rows = empDAO.addEmployee(employee);
            /*Using PRG Pattern.
             * Instead of forwarding from doPost() method, we are doing a
             * redirection to avoid duplicate form submission.
             */
            if(rows > 0) {
                success = 1;
            }
        } catch (ApplicationException e) {
            //Log the error
            request.setAttribute("error", e.getMessage());
        }

        response.sendRedirect("displayEmployee.do?s=" +
                success);
    }
}
