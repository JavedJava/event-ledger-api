package com.example.event_ledger_api.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Event {

	@Id
	private String eventId;
	
	private String accountId;
	
	@Enumerated(EnumType.STRING)
	private EventType type;
	
	private Double amount;
	private String currency;
	
	private Instant eventTimeStamp;
	
	@Column(columnDefinition = "TEXT")
	private String metadata;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Instant getEvenTimeStamp() {
		return eventTimeStamp;
	}

	public void setEvenTimeStamp(Instant eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	
	
	
}
