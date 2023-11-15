package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {
    public JspMealController(MealService service) {
        super(service);
    }

    @PostMapping(value = "/meals")
    public String createUpdate(@ModelAttribute("meal") Meal meal) {
        if (meal.getId() == null) {
            super.create(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return "redirect:meals";
    }

    @GetMapping("/meals")
    public String getAll(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @RequestMapping(value = "/meals", params = "action=delete")
    public String delete(Model model, @RequestParam(value = "id") int id) {
        super.delete(id);
        getAll(model);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", params = "action=create")
    public ModelAndView create() {
        log.info("create");
        Meal meal = new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        ModelAndView modelAndView = new ModelAndView("mealForm");
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }

    @RequestMapping(value = "/meals", params = "action=update")
    public ModelAndView update(@RequestParam(value = "id") int id) {
        log.info("update {}", id);
        Meal meal = super.get(id);
        ModelAndView modelAndView = new ModelAndView("mealForm");
        modelAndView.addObject("meal", meal);
        return modelAndView;
    }

    @GetMapping(value = "/meals", params = "action=filter")
    public String getFiltered(
            Model model,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime) {
        int userId = SecurityUtil.authUserId();

        log.info("get filtered meals for {}", userId);
        LocalDate startD = parseLocalDate(startDate);
        LocalDate endD = parseLocalDate(endDate);
        LocalTime startT = parseLocalTime(startTime);
        LocalTime endT = parseLocalTime(endTime);

        model.addAttribute("meals", super.getBetween(startD, startT, endD, endT));
        return "meals";
    }
}