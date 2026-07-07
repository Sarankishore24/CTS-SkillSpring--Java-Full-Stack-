package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CountryController — REST endpoints for country resource.
 *
 * URL design follows REST naming guidelines:
 *   GET    /country          → get India (legacy endpoint)
 *   GET    /countries        → get all countries
 *   GET    /countries/{code} → get country by code
 *   POST   /countries        → add a new country
 */
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    public CountryController() {
        LOGGER.debug("Inside CountryController Constructor.");
    }

    /**
     * Hands-on 2 (REST): Returns India country details.
     * GET /country
     */
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("START");
        Country country = countryService.getCountryIndia();
        LOGGER.info("END");
        return country;
    }

    /**
     * Hands-on: Returns all countries.
     * GET /countries
     */
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("Returning {} countries", countries.size());
        LOGGER.info("END");
        return countries;
    }

    /**
     * Hands-on: Returns a country by code (case-insensitive).
     * GET /countries/{code}
     */
    @GetMapping("/countries/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("START");
        Country country = countryService.getCountry(code);
        LOGGER.info("END");
        return country;
    }

    /**
     * Hands-on POST: Adds a new country from JSON payload.
     * POST /countries
     * Validates that code is exactly 2 characters and not null.
     */
    @PostMapping("/countries")
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("START");
        LOGGER.debug("Received country: {}", country);
        Country saved = countryService.addCountry(country);
        LOGGER.info("END");
        return saved;
    }
}
