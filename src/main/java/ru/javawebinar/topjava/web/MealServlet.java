package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealRepository mealRepository = new MemoryMealRepository();
    private static final String MEALS_LIST_JSP = "/meals.jsp";
    private static final String MEAL_FORM_JSP = "/meal.jsp";
    private static final String MEALS_LIST_URL = "/meals";
    private static final int CALORIES_PER_DAY_LIMIT = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "noAction" : request.getParameter("action");
        log.debug("redirect to list of meals with action {}", action);
        switch (action) {
            case ("insert"): {
                request.getRequestDispatcher(MEAL_FORM_JSP).forward(request, response);
                break;
            }
            case ("edit"): {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal m = mealRepository.getById(id);
                if (m != null) {
                    request.setAttribute("meal", m);
                } else {
                    log.debug("Meal with id {} not found", id);
                }
                request.getRequestDispatcher(MEAL_FORM_JSP).forward(request, response);
                break;
            }
            case ("delete"): {
                int id = Integer.parseInt(request.getParameter("id"));
                log.debug("remove Meal with id: {} ", id);
                mealRepository.remove(id);
                response.sendRedirect(request.getContextPath() + MEALS_LIST_URL);
                break;
            }
            default: {
                List<MealTo> meals = MealsUtil.filteredByStreams(
                        mealRepository.getAll(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        CALORIES_PER_DAY_LIMIT);

                request.setAttribute("meals", meals);
                request.getRequestDispatcher(MEALS_LIST_JSP).forward(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = request.getParameter("id") == null ? null : Integer.parseInt(request.getParameter("id"));
        LocalDateTime datetime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id == null) {
            log.debug("redirect to add meal");
            mealRepository.add(new Meal(null, datetime, description, calories));
        } else {
            log.debug("redirect to update meal id: {}", id);
            if (null == mealRepository.update(new Meal(id, datetime, description, calories))) {
                log.debug("update is failed as of meal id: {} not found", id);
            }
        }

        response.sendRedirect(request.getContextPath() + MEALS_LIST_URL);
    }
}
