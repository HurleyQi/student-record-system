package web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StudentService;
// import service.StudentServiceImpl;
import service.StudentServiceMyBatisImpl;

public class StudentDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            resp.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(req.getParameter("id"));
            StudentService ss = new StudentServiceMyBatisImpl();
            boolean result = ss.deleteStudent(id);
            if (result) {
            resp.sendRedirect("success.jsp");
            } else {
            resp.sendRedirect("failure.jsp");
            }
        } catch (Exception e) {
            String exceptionMessage = e.getMessage();
            req.setAttribute("exceptionMessage", exceptionMessage);
            req.getRequestDispatcher("failure.jsp").forward(req, resp);
            resp.sendRedirect("failure.jsp");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
        doPost(req, resp);
    }

}
