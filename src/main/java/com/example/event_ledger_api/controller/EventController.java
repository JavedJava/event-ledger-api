package com.example.event_ledger_api.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.event_ledger_api.model.Event;
import com.example.event_ledger_api.service.EventService;

@RestController
public class EventController {

    @Autowired
    private EventService service;

    @PostMapping("/events")
    public ResponseEntity<?> create(@RequestBody Event event) {
        Event saved = service.create(event);
        return ResponseEntity.ok(saved);

    }

    @GetMapping("/events/{id}")
    public Event get(@PathVariable String id) {
        return service.get(id);
    }

    @GetMapping("/events")
    public List<Event> getByAccount(@RequestParam String account) {
        return service.getByAccount(account);
    }

    @GetMapping("/accounts/{accountId}/balance")
    public Map<String, Double> balance(@PathVariable String accountId) {
        return Collections.singletonMap("balance", service.getBalance(accountId));
    }
}
