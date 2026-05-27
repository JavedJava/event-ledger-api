package com.example.event_ledger_api.repository;

import com.example.event_ledger_api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
	
	List<Event> findByAccountId(String accountId);
	
	List<Event> findByAccountIdOrderByEventTimeStampAsc(String accountId);

}
