package web;

import java.io.IOException;
import java.util.List;

import dao.models.Student;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StudentService;
import service.StudentServiceMyBatisImpl;
// import service.StudentServiceImpl;
import web.dto.StudentSearchDTO;

public class StudentSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
                        throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String course = req.getParameter("course");
            StudentSearchDTO ssd = new StudentSearchDTO(name, course);
            List<Student> studentList = getStudentList(ssd);
            req.setAttribute("studentList", studentList);
            req.getRequestDispatcher("search-result.jsp");
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

    private List<Student> getStudentList(StudentSearchDTO ssd) {
        StudentService ss = new StudentServiceMyBatisImpl();
        List<Student> result = ss.searchStudent(ssd);
        return result;
    }
}
