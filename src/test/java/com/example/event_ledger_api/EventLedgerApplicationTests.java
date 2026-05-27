package com.example.event_ledger_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventLedgerApplicationTests {

	@Autowired
	private MockMvc mvc;


	@Test
	public void testIdempotency() throws Exception {
		String json = "{\"eventId\":\"e1\",\"accountId\":\"a1\",\"type\":\"CREDIT\",\"amount\":100,\"currency\":\"USD\",\"eventTimeStamp\":\"2026-01-01T00:00:00Z\"}";

		mvc.perform(post("/events").contentType("application/json").content(json))
		.andExpect(status().isOk());

		mvc.perform(post("/events").contentType("application/json").content(json))
		.andExpect(status().isOk()); //Should not duplicate
	}

	@Test
	public void testOutOfOrder() throws Exception {

		mvc.perform(post("/events").contentType("application/json")
				.content("{\"eventId\":\"e2\",\"accountId\":\"a1\",\"type\":\"CREDIT\",\"amount\":100,\"currency\":\"USD\",\"eventTimeStamp\":\"2026-05-10T00:00:00Z\"}"));

		mvc.perform(post("/events").contentType("application/json")
				.content("{\"eventId\":\"e3\",\"accountId\":\"a1\",\"type\":\"CREDIT\",\"amount\":200,\"currency\":\"USD\",\"eventTimeStamp\":\"2026-05-01T00:00:00Z\"}"));

		mvc.perform(get("/events?account=a1"))
		.andExpect(jsonPath("$[0].eventId").value("e2"));

	}

	@Test
	public void testBalance() throws Exception {

		mvc.perform(post("/events").contentType("application/json")
				.content("{\"eventId\":\"e4\",\"accountId\":\"a2\",\"type\":\"CREDIT\",\"amount\":200,\"currency\":\"USD\",\"eventTimeStamp\":\"2026-01-01T00:00:00Z\"}"));

		mvc.perform(post("/events").contentType("application/json")
				.content("{\"eventId\":\"e5\",\"accountId\":\"a2\",\"type\":\"DEBIT\",\"amount\":50,\"currency\":\"USD\",\"eventTimeStamp\":\"2026-01-02T00:00:00Z\"}"));

		mvc.perform(get("/accounts/a2/balance"))
		.andExpect(jsonPath("$.balance").value(150));

	}

	@Test
	public void testValidation() throws Exception {

		mvc.perform(post("/events").contentType("application/json")
				.content("{\"eventId\":\"e6\",\"accountId\":\"a3\",\"type\":\"DEBIT\",\"amount\":-10,\"currency\":\"USD\",\"eventTimeStamp\":\"2026-01-01T00:00:00Z\"}"))
		.andExpect(status().isBadRequest());

	}

}
