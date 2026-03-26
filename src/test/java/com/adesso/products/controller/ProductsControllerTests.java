package com.adesso.products.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.adesso.products.AbstractIntegrationTest;
import com.adesso.products.config.security.jwt.JwtUtils;
import com.adesso.products.dto.JwtTokenResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration
@Sql(scripts = { "/sql/populate_sample_data.sql" }, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class ProductsControllerTests extends AbstractIntegrationTest {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	@LocalServerPort
	private Integer serverPort;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private JwtUtils jwtService;
	private String token;

	@BeforeEach
	void setupToken() {
		JwtTokenResponse jwtToken = jwtService.generateTokenResponse("test");
		token = jwtToken.getToken();
	}

	@Test
	void testGetAllProducts() throws Exception {

		mockMvc.perform(get("/").header("Authorization", "Bearer ".concat(token))).andExpect(status().isOk())
				.andExpect(jsonPath("$..id").value(1)).andDo(print());
	}

	@Test
	void testGetElementById() throws Exception {
		mockMvc.perform(get("/{id}", 1).header("Authorization", "Bearer ".concat(token))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andDo(print());
	}

	@Test
	void testCreateElement() throws Exception {
		mockMvc.perform(post("/").header("Authorization", "Bearer ".concat(token)).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Creation Test\", \"description\": \"Post Request Creation\", "
						+ "\"price\": 17.2, \"available\": true}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(2))
				.andExpect(jsonPath("$.name").value("Creation Test"))
				.andExpect(jsonPath("$.description").value("Post Request Creation"))
				.andExpect(jsonPath("$.price").value(17.2)).andExpect(jsonPath("$.available").value(true))
				.andExpect(jsonPath("$.created").value(DATE_FORMAT.format(new Date()))).andDo(print());
		mockMvc.perform(get("/").header("Authorization", "Bearer ".concat(token))).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2)).andDo(print());
	}

	@Test
	void testUpdateElement() throws Exception {
		mockMvc.perform(
				put("/{id}", 1).header("Authorization", "Bearer ".concat(token)).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content("{\"name\": \"Test\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1)).andDo(print());
	}

	@Test
	void testDeleteElement() throws Exception {
		mockMvc.perform(delete("/{id}", 1).header("Authorization", "Bearer ".concat(token))).andExpect(status().isOk())
				.andExpect(r -> {
					r.getResponse().equals(true);
				}).andDo(print());
		mockMvc.perform(get("/").header("Authorization", "Bearer ".concat(token))).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(0)).andDo(print());
	}

	@AfterAll
	static void afterAll() {
		postgres.stop();
	}
}
