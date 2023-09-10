package web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.models.Record;
import dao.models.Student;
import service.StudentService;
// import service.StudentServiceImpl;
import service.StudentServiceMyBatisImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// @WebServlet(name = "StudentServlet", urlPatterns = "/student_servlet")
public class StudentAddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("name") == null || req.getParameter("personal-id") == null) {
            throw new IllegalArgumentException();
        }
        try {
            resp.setCharacterEncoding("UTF-8");
            // 创建变量为了建立DAO.student
            String name = req.getParameter("name");
            int id = Integer.parseInt(req.getParameter("personal-id"));
            int age = (!req.getParameter("age").equals("")) ? Integer.parseInt(req.getParameter("age")) : 0;
            int gender = 0;
            if (req.getParameter("gender") != null) {
                String genderString = req.getParameter("gender");
                gender = (genderString.equals("male")) ? 1 : 2;
            }
            String address = (req.getParameter("address") != null) ? req.getParameter("address") : null;
            String pCourse = (req.getParameter("pCourse") != null) ? req.getParameter("pCourse") : null;
            // 将出生日期从string变成util.Date
            Date dob = getDate(req.getParameter("date"));
            Student st = new Student(name, id, age, dob, gender, address, pCourse);
            // 获得教育记录的所有信息
            String[] schoolNames = req.getParameterValues("schoolNames");
            String[] studentId = req.getParameterValues("studentIds");
            String[] startDate = req.getParameterValues("startDates");
            String[] endDate = req.getParameterValues("endDates");
            String[] levels = req.getParameterValues("level");
            List<Record> records = getRecords(id, schoolNames, studentId, startDate, endDate, levels);
            st.setRecords(records);
            StudentService ss = new StudentServiceMyBatisImpl();
            boolean result = ss.addStudent(st);
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

    private List<Record> getRecords(int id, String[] schoolNames, String[] studentId, 
                                    String[] startDate, String[] endDate, String[] levels) {
        List<Record> result = new ArrayList<>();
        for (int i = 1; i < studentId.length; i++) {
            Date start = getDate(startDate[i]);
            Date end = getDate(endDate[i]);
            int studentID = Integer.parseInt(studentId[i]);
            int level = 0;
            if (levels != null) {
                if (levels[i] != null && (!levels[i].equals(""))){
                    level = Integer.parseInt(levels[i]);
                }
            }
            Record curr = new Record(studentID, id, start, end, schoolNames[i], level);
            result.add(curr);
        }
        return result;
    }

    private Date getDate(String date) {
        if (date == null || date.equals("")) {
            return null;
        }
        Date result = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            result = sdf1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
