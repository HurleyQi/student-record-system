package web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.models.Student;
import dao.models.Record;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.StudentService;
// import service.StudentServiceImpl;
import service.StudentServiceMyBatisImpl;

public class StudentEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (req.getParameter("name") == null) {
            throw new IllegalArgumentException();
        }
        try {
            resp.setCharacterEncoding("UTF-8");
            // 创建变量为了建立DAO.student
            String name = req.getParameter("name");
            int id = Integer.parseInt(req.getParameter("personalId"));
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

            String[] schoolNames = req.getParameterValues("schoolNames");
            String[] studentIds = req.getParameterValues("studentIds");
            String[] startDate = req.getParameterValues("startDates");
            String[] endDate = req.getParameterValues("endDates");
            String[] levels = req.getParameterValues("level");

            Student st = new Student(name, id, age, dob, gender, address, pCourse);
            st.setRecords(getEditedRecords(schoolNames, studentIds, startDate, endDate, levels, id));
            StudentService ss = new StudentServiceMyBatisImpl();
            boolean result;
            result = ss.editStudent(st);
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

    private List<Record> getEditedRecords(String[] schoolNames, String[] studentIds, 
                        String[] startDate, String[] endDate, String[] levels, int id) {
        List<Record> result = new ArrayList<>();
        for (int i = 0; i < studentIds.length; i++) {
            if (studentIds[i] != null && !studentIds[i].equals("")) {
                int currStudentId = Integer.parseInt(studentIds[i]);
                int currLevel = Integer.parseInt(levels[i]);
                Date currStart = getDate(startDate[i]);
                Date currEnd = getDate(endDate[i]);
                Record curr = 
                    new Record(currStudentId, id, currStart, currEnd, schoolNames[i], currLevel);
                result.add(curr);
            }
        }
        return result;
    }    

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
            doPost(req, resp);
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
