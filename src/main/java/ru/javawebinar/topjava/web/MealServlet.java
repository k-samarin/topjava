package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.model.MemoryMealUpdater;
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
    private static final MemoryMealUpdater mmu = MemoryMealUpdater.getInstance();
    static final String MEALS_LIST = "/meals.jsp";
    static final String MEAL_FORM = "/meal.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardTo = MEALS_LIST;
        String action = request.getParameter("action");
        log.debug(String.format("redirect to list of meals with action %s", action));
        if (action != null) {
            switch (action.toLowerCase()) {
                case ("insert"): {
                    forwardTo = MEAL_FORM;
                    break;
                }
                case ("edit"): {
                    int id = Integer.parseInt(request.getParameter("id"));
                    Meal m = mmu.getMealById(id);
                    if (m != null) {
                        request.setAttribute("id", id);
                        request.setAttribute("datetime", m.getDateTime());
                        request.setAttribute("description", m.getDescription());
                        request.setAttribute("calories", m.getCalories());
                        forwardTo = MEAL_FORM;
                    } else {
                        log.debug(String.format("Meal with id %s not found", action));
                    }
                    break;
                }
                case ("delete"): {
                    int id = Integer.parseInt(request.getParameter("id"));
                    log.debug(String.format("remove Meal with id: %s ", id));
                    mmu.remove(id);
                    break;
                }
                default: {
                    // Do nothing for now.
                }
            }
        }

        if (forwardTo.equals(MEALS_LIST)) {
            List<MealTo> meals = MealsUtil.filteredByStreams(
                    mmu.getMeals(),
                    LocalTime.MIN,
                    LocalTime.MAX,
                    2000);

            request.setAttribute("meals", meals);
        }

        request.getRequestDispatcher(forwardTo).forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = request.getParameter("id") == null ? -1 : Integer.parseInt(request.getParameter("id"));
        LocalDateTime datetime = LocalDateTime.parse(request.getParameter("datetime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        if (id < 0) {
            mmu.add(datetime, description, calories);
        } else {
            mmu.update(id, datetime, description, calories);
        }

        List<MealTo> meals = MealsUtil.filteredByStreams(
                mmu.getMeals(),
                LocalTime.MIN,
                LocalTime.MAX,
                2000);

        request.setAttribute("meals", meals);
        request.getRequestDispatcher(MEALS_LIST).forward(request, response);
    }
}
