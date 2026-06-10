package com.haushekmiva.controller;

import com.haushekmiva.annotation.CurrentUser;
import com.haushekmiva.dto.*;
import com.haushekmiva.service.LocationService;
import com.haushekmiva.utils.ValidUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String showSearchResult(Model model, @RequestParam(defaultValue = "", value="name") String name, @CurrentUser UserDto user) {

        if (ValidUtils.isStringEmpty(name)) {
            return "redirect:/";
        }

        List<SearchedLocationDto> searchedLocations = locationService.searchLocations(name);
        model.addAttribute("user", user);
        model.addAttribute("searchResultDto", new SearchResultDto(searchedLocations));
        return "search-result";
    }

    @PostMapping("/locations")
    public String addLocation(@ModelAttribute FoundLocationDto location, @CurrentUser UserDto user) {

        if (!ValidUtils.isCoordinatesValid(location.lat(), location.lon())) {
            return "redirect:/";
        }

        locationService.addLocation(location, user);
        return "redirect:/";
    }

    @DeleteMapping("/locations/{id}")
    public String deleteLocation(@PathVariable(name = "id") int id, @CurrentUser UserDto user) {

        locationService.removeLocation(id, user);
        return "redirect:/";
    }

}
