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
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealRepository mealRepository = new MemoryMealRepository();
    private static final String MEALS_LIST = "/meals.jsp";
    private static final String MEAL_FORM = "/meal.jsp";
    private static final int CALORIES_PER_DAY_LIMIT = 2000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardTo = MEALS_LIST;
        String action = request.getParameter("action") != null ? request.getParameter("action") : "unknown";
        log.debug("redirect to list of meals with action {}", action);
        switch (action.toLowerCase()) {
            case ("insert"): {
                forwardTo = MEAL_FORM;
                break;
            }
            case ("edit"): {
                int id = Integer.parseInt(request.getParameter("id"));
                Meal m = mealRepository.getById(id);
                if (m != null) {
                    request.setAttribute("meal", m);
                    forwardTo = MEAL_FORM;
                } else {
                    log.debug("Meal with id {} not found", id);
                }
                break;
            }
            case ("delete"): {
                int id = Integer.parseInt(request.getParameter("id"));
                log.debug("remove Meal with id: {} ", id);
                mealRepository.remove(id);
            }
            default: {
                List<MealTo> meals = MealsUtil.filteredByStreams(
                        mealRepository.getAll(),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        CALORIES_PER_DAY_LIMIT);

                request.setAttribute("meals", meals);
            }
        }

        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            mealRepository.update(new Meal(id, datetime, description, calories));
        }

        List<MealTo> meals =
                MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY_LIMIT);

        request.setAttribute("meals", meals);
        request.getRequestDispatcher(MEALS_LIST).forward(request, response);
    }
}
