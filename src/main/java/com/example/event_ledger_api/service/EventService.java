package com.example.event_ledger_api.service;

import com.example.event_ledger_api.model.Event;
import com.example.event_ledger_api.model.EventType;
import com.example.event_ledger_api.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repo;
	
	public Event create(Event event)
	{
		validate(event);
		
		Optional<Event> existing = repo.findById(event.getEventId());
		if (existing.isPresent()) {
			return existing.get(); // This is for Idempotency.
		}
		return repo.save(event);
	}
	
	private void validate(Event event) {
		if (event.getAmount() <= 0) {
			throw new IllegalArgumentException("Amount must be > 0");
		}
		if (event.getType() == null) {
			throw new IllegalArgumentException("Invalid Type");
		}
		
	}

	public Event get(String id) {
		return repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Event not found"));
	}
	
	public List<Event> getByAccount(String accountId) {
		return repo.findByAccountIdOrderByEventTimeStampAsc(accountId);
	}
	
	public double getBalance(String accoutId) {
		return repo.findByAccountId(accoutId).stream()
				.mapToDouble(e -> e.getType() == EventType.CREDIT ? e.getAmount() : -e.getAmount())
				.sum();
	}

}
