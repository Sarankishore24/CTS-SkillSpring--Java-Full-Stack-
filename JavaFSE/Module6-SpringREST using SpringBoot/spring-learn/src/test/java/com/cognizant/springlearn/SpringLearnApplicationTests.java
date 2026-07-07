package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.CountryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * MockMVC Tests — Hands-on 2 (REST doc).
 *
 * Covers:
 *  - CountryController context loads
 *  - GET /country returns code=IN and name=India
 *  - GET /countries/{code} with invalid code returns 404
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private CountryController countryController;

    @Autowired
    private MockMvc mvc;

    /**
     * Verifies that the Spring context loads and CountryController is wired correctly.
     */
    @Test
    void contextLoads() {
        assertNotNull(countryController, "CountryController should be loaded in context");
    }

    /**
     * GET /country → expects HTTP 200 with code=IN and name=India.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/country"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    /**
     * GET /countries → expects HTTP 200 and a non-empty JSON array.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$[0].code").exists());
    }

    /**
     * GET /countries/in → expects HTTP 200 with code=IN.
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCountryByCode() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/in"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").value("IN"));
    }

    /**
     * GET /countries/az → expects HTTP 404 (CountryNotFoundException).
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testGetCountryException() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/az"));
        actions.andExpect(status().isNotFound());
        actions.andExpect(status().reason("Country not found"));
    }
}
