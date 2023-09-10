package web;

import java.io.IOException;

import dao.models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StudentService;
import service.StudentServiceMyBatisImpl;

public class EditHelperServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
                        throws ServletException, IOException {
        try {
            int personalId = Integer.parseInt(req.getParameter("id"));
            StudentService ss = new StudentServiceMyBatisImpl();
            Student result = ss.searchStudentById(personalId);
            req.setAttribute("student", result);
            req.setAttribute("recordList", result.getRecords());
            req.getRequestDispatcher("edit.jsp").forward(req, resp);
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            req.setAttribute("exceptionMessage", exceptionMessage);
            req.getRequestDispatcher("failure.jsp").forward(req, resp);
            resp.sendRedirect("failure.jsp");
        }
    }  

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        doGet(req, resp);
    }
}
