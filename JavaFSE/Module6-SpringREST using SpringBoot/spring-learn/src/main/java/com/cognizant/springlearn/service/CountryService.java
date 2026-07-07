package com.cognizant.springlearn.service;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Country operations.
 * Loads country data from Spring XML configuration.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    /**
     * Returns India country details loaded from country.xml.
     */
    public Country getCountryIndia() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("in", Country.class);
        LOGGER.info("END");
        return country;
    }

    /**
     * Returns the full list of countries from country.xml.
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = context.getBean("countryList", List.class);
        LOGGER.debug("Countries loaded: {}", countries);
        LOGGER.info("END");
        return countries;
    }

    /**
     * Returns a country matching the given code (case-insensitive).
     * Throws CountryNotFoundException if not found.
     */
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("START");
        List<Country> countries = getAllCountries();

        Country result = countries.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(CountryNotFoundException::new);

        LOGGER.debug("Found country: {}", result);
        LOGGER.info("END");
        return result;
    }

    /**
     * Adds a country to the in-memory list (no persistent store in this module).
     */
    @SuppressWarnings("unchecked")
    public Country addCountry(Country country) {
        LOGGER.info("START");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = context.getBean("countryList", List.class);
        countries.add(country);
        LOGGER.debug("Added country: {}", country);
        LOGGER.info("END");
        return country;
    }
}
