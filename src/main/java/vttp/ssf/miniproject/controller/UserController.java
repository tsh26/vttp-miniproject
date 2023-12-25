package vttp.ssf.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vttp.ssf.miniproject.model.BusArrival;
import vttp.ssf.miniproject.model.BusStopCode;
import vttp.ssf.miniproject.service.BusApiService;
import vttp.ssf.miniproject.service.BusUserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private BusApiService busApiSvc;

    @Autowired
    private BusUserService busUserSvc;

    // landing page (User Login - index.html)
    @GetMapping({ "/", "/index" })
    public String getIndex() {
        // username
        // password

        return "index";
    }

    // goes to search page
    @GetMapping("/search")
    public ModelAndView getHomepage() {
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("code", busApiSvc.getBusStopCode());
        mav.addObject("busStopCode", new BusStopCode());

        return mav;
    }

    // what happens when you press search button
    @PostMapping("/searchResult")
    public ModelAndView searchBusTimings(@Valid BusStopCode busStopCode, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("search");
        ModelAndView mavValid = new ModelAndView("searchResult");

        if (bindingResult.hasErrors()) {
            return mav;
        }

        // Fetch bus information based on the bus stop code
        List<BusArrival> busInfo = busApiSvc.getBusStopInfo(Integer.parseInt(busStopCode.getBusStopCode()));
        mavValid.addObject("busInfo", busInfo);

        return mavValid;
    }

    // what happens when you press bookmark button
    @PostMapping("/bookmark")
    public ModelAndView saveBusStop(@Valid BusStopCode busStopCode, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("search");
        ModelAndView mavValid = new ModelAndView("bookmark");

        if(bindingResult.hasErrors()) {
            return mav;
        }

        busUserSvc.addBusStop(busStopCode);
        mavValid.addObject("codes", busApiSvc.getBusStopCode());
        mavValid.addObject("busStopBookmark", busUserSvc.getAllBusStops());
        return mavValid;        
    }


    @PostMapping("/delete")
    public ModelAndView deleteBusStop(@RequestParam String busStopCode) {
        ModelAndView mav = new ModelAndView("bookmark");

        busUserSvc.removeBusStop(busStopCode);
        mav.addObject("codes", busApiSvc.getBusStopCode());
        mav.addObject("busStopBookmark", busUserSvc.getAllBusStops());
        return mav;
    }
}