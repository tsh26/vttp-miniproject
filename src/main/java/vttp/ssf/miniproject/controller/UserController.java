package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.miniproject.model.BusArrival;
import vttp.ssf.miniproject.model.BusStop;
import vttp.ssf.miniproject.model.User;
import vttp.ssf.miniproject.service.BusApiService;
import vttp.ssf.miniproject.service.BusUserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private BusApiService busApiSvc;

    @Autowired
    private BusUserService busUserSvc;

    // Landing page
    @GetMapping({ "/", "/index" })
    public ModelAndView getIndex() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("user", new User());

        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid User user, BindingResult bindingResult, HttpSession sess) {
        ModelAndView mav = new ModelAndView("index");
        ModelAndView mavValid = new ModelAndView("redirect:/search");

        if (bindingResult.hasErrors()) {
            mav.addObject("user", user);
            return mav;
        }

        sess.setAttribute("user", user);
        return mavValid;
    }

    @GetMapping("/guest")
    public ModelAndView getGuestHomepage() {
        ModelAndView mav = new ModelAndView("guest");
        mav.addObject("code", busApiSvc.getBusStopCode());
        mav.addObject("busStop", new BusStop());

        return mav;
    }

    @PostMapping("/guest")
    public ModelAndView GuestHomepage() {
        ModelAndView mav = new ModelAndView("guest");
        mav.addObject("code", busApiSvc.getBusStopCode());
        mav.addObject("busStop", new BusStop());

        return mav;
    }

    @PostMapping("/guestResult")
    public ModelAndView searchGuestResult(@Valid BusStop busStop, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("guest");
        ModelAndView mavValid = new ModelAndView("guestResult");
        mav.addObject("busStop", busStop);

        if (bindingResult.hasErrors()) {
            return mav;
        }

        // Fetch bus information based on the bus stop code
        List<BusArrival> busInfo = busApiSvc.getBusStopInfo(Integer.parseInt(busStop.getBusStopCode()));
        mavValid.addObject("busInfo", busInfo);

        // Fetch additional information and add it to the ModelAndView
        BusStop additionalInfo = busApiSvc.fetchAdditionalInfo(busStop.getBusStopCode());
        if (additionalInfo != null) {
            busStop.setRoadName(additionalInfo.getRoadName());
            busStop.setDescription(additionalInfo.getDescription());
            mavValid.addObject("busStop", busStop);
        }

        return mavValid;
    }

    @GetMapping("/search")
    public ModelAndView getHomepage(HttpSession sess) {
        ModelAndView mav = new ModelAndView("search");
        User user = (User) sess.getAttribute("user");

        mav.addObject("code", busApiSvc.getBusStopCode());
        mav.addObject("busStop", new BusStop());

        if (user != null) {
            mav.addObject("username", user.getUsername());
        }

        return mav;
    }

    @PostMapping("/searchResult")
    public ModelAndView searchBusTimings(@Valid BusStop busStop, BindingResult bindingResult, HttpSession sess) {
        ModelAndView mav = new ModelAndView("search");
        ModelAndView mavValid = new ModelAndView("searchResult");
        User user = (User) sess.getAttribute("user");
        mav.addObject("busStop", busStop);

        if (user != null) {
            mav.addObject("username", user.getUsername());
        }

        if (bindingResult.hasErrors()) {
            return mav;
        }

        // Fetch bus information based on the bus stop code
        List<BusArrival> busInfo = busApiSvc.getBusStopInfo(Integer.parseInt(busStop.getBusStopCode()));
        mavValid.addObject("busInfo", busInfo);

        // Fetch additional information and add it to the ModelAndView
        BusStop additionalInfo = busApiSvc.fetchAdditionalInfo(busStop.getBusStopCode());
        if (additionalInfo != null) {
            busStop.setRoadName(additionalInfo.getRoadName());
            busStop.setDescription(additionalInfo.getDescription());
            mavValid.addObject("busStop", busStop);
        }

        if (user != null) {
            mavValid.addObject("username", user.getUsername());
        }

        return mavValid;
    }

    @PostMapping("/bookmark")
    public ModelAndView saveBusStop(@Valid BusStop busStop, BindingResult bindingResult, HttpSession sess) {
        ModelAndView mav = new ModelAndView("search");
        ModelAndView mavValid = new ModelAndView("bookmark");
        mav.addObject("busStop", busStop);
        User user = (User) sess.getAttribute("user");

        if (user != null) {
            mav.addObject("username", user.getUsername());
        }

        if (bindingResult.hasErrors()) {
            return mav;
        }

        // Fetch additional information and add it to the ModelAndView
        BusStop additionalInfo = busApiSvc.fetchAdditionalInfo(busStop.getBusStopCode());
        if (additionalInfo != null) {
            busStop.setRoadName(additionalInfo.getRoadName());
            busStop.setDescription(additionalInfo.getDescription());
            mavValid.addObject("busStop", busStop);
        }

        busUserSvc.addBusStop(user.getUsername(), busStop);
        mavValid.addObject("codes", busApiSvc.getBusStopCode());
        mavValid.addObject("busStopBookmark", busUserSvc.getAllBusStops(user.getUsername()));

       if (user != null) {
            mavValid.addObject("username", user.getUsername());
        }

        return mavValid;
    }

    @PostMapping("/retrieveBookmarks")
    public ModelAndView retrieveBookmarks(@ModelAttribute("busStop") BusStop busStop, HttpSession sess) {
        ModelAndView mav = new ModelAndView("bookmark");
        User user = (User) sess.getAttribute("user");

        mav.addObject("codes", busApiSvc.getBusStopCode());
        mav.addObject("busStopBookmark", busUserSvc.getAllBusStops(user.getUsername()));

        if (user != null) {
            mav.addObject("username", user.getUsername());
        }

        return mav;
    }

    @PostMapping("/delete")
    public ModelAndView deleteBusStop(@RequestParam String busStopCode, HttpSession sess) {
        ModelAndView mav = new ModelAndView("bookmark");
        User user = (User) sess.getAttribute("user");

        busUserSvc.removeBusStop(user.getUsername(), busStopCode);
        mav.addObject("codes", busApiSvc.getBusStopCode());
        mav.addObject("busStopBookmark", busUserSvc.getAllBusStops(user.getUsername()));

        if (user != null) {
            mav.addObject("username", user.getUsername());
        }

        return mav;
    }

    @GetMapping("/bookmark/api")
    public ModelAndView getAPI() {
        ModelAndView mav = new ModelAndView("restAPI");
        return mav;
    }
}