package pl.codeschool.controller;

import pl.codeschool.dao.SolutionDao;
import pl.codeschool.model.Solution;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        int numberOfRows = Integer.parseInt(getServletContext().getInitParameter("numberOfRows"));

        List<Solution> recentSolutions = SolutionDao.findRecent(numberOfRows);
        request.setAttribute("solutions", recentSolutions);

        request.getRequestDispatcher("/WEB-INF/index.jsp")
                .forward(request, response);
    }
}
