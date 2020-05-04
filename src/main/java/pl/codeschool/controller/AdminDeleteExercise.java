package pl.codeschool.controller;

import pl.codeschool.dao.ExerciseDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminDeleteExercise")
public class AdminDeleteExercise extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String paramExerciseId = request.getParameter("exerciseId");

        if (paramExerciseId != null && !"".equals(paramExerciseId)) {
            try {
                int exerciseId = Integer.parseInt(paramExerciseId);
                ExerciseDao.delete(exerciseId);
//                response.sendRedirect("/adminExercises");
                request.getRequestDispatcher("/adminExercises").forward(request, response);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
//        request.getRequestDispatcher("/adminExercises")
//                .forward(request, response);
    }
}
