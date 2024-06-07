package com.forniturear.forniturear;

import com.forniturear.forniturear.controllers.FurnitureController;
import com.forniturear.forniturear.models.Furniture;
import com.forniturear.forniturear.repositories.IFurnitureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ForniturearApplicationTests {

	private MockMvc mockMvc;

	@Mock
	private IFurnitureRepository furnitureRepository;

	@InjectMocks
	private FurnitureController furnitureController;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.standaloneSetup(furnitureController).build();
	}

	@Test
	void shouldGetFurniture() throws Exception {
		Furniture furniture = new Furniture(1L, "Chair", "http://example.com/chair.jpg", 120, 80, 20, 59.99, 5);

		given(furnitureRepository.findById(1L)).willReturn(Optional.of(furniture));

		mockMvc.perform(get("/api/furniture/{id}", 1L)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Chair"))
				.andExpect(jsonPath("$.price").value(59.99));
	}
}
