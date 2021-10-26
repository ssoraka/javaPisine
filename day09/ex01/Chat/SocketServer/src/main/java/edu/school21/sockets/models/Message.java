package edu.school21.sockets.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
	private Long id;
	private User author;
	private String text;
	private LocalDateTime dateTime;

	public Message(Long id, User author, String text, LocalDateTime dateTime) {
		this.id = id;
		this.author = author;
		this.text = text;
		this.dateTime = dateTime;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public String toString() {
		String result = "Message: {\n\tid=" + id +
				",\n\tauthor=" + author +
				",\n\ttext=\"" + text + "\"" +
				",\n\tdateTime=" + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) { this.text = text; }

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
