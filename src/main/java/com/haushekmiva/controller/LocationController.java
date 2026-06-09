package com.haushekmiva.controller;

import com.haushekmiva.annotation.CurrentUser;
import com.haushekmiva.dto.*;
import com.haushekmiva.exception.custom.ValidationException;
import com.haushekmiva.service.LocationService;
import com.haushekmiva.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @GetMapping("/")
    public String showIndexPage(Model model, @CurrentUser UserDto user) {
        List<LocationWeatherDto> locationWeathers = locationService.getUserLocationsWeather(user.id());
        model.addAttribute("user", user);
        model.addAttribute("indexPageDto", new IndexPageDto(locationWeathers));
        return "index";
    }

    @GetMapping("/search")
    public String showSearchResult(Model model, @RequestParam("name") String name, @CurrentUser UserDto user) {

        if (ValidUtils.isStringEmpty(name)) {
            return "redirect:/";
        }

        List<SearchedLocationDto> searchedLocations = locationService.searchLocations(name);
        model.addAttribute("user", user);
        model.addAttribute("searchResultDto", new SearchResultDto(searchedLocations));
        return "search-result";
    }

}
