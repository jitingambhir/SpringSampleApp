package io.spring.start.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Chat.
 */
@Entity
@Table(name = "chat")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "chat")
public class Chat implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "message", nullable = false)
	private String message;

	@NotNull
	@Column(name = "message_date", nullable = false)
	private ZonedDateTime messageDate;

	@ManyToOne
	@NotNull
	@JsonIgnore
	private ChatSession chatSession;

	@ManyToOne
	@NotNull
	private User messageFrom;

	@ManyToOne
	@NotNull
	private User messageTo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public Chat message(String message) {
		this.message = message;
		return this;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ZonedDateTime getMessageDate() {
		return messageDate;
	}

	public Chat messageDate(ZonedDateTime messageDate) {
		this.messageDate = messageDate;
		return this;
	}

	public void setMessageDate(ZonedDateTime messageDate) {
		this.messageDate = messageDate;
	}

	public ChatSession getChatSession() {
		return chatSession;
	}

	public Chat chatSession(ChatSession chatSession) {
		this.chatSession = chatSession;
		return this;
	}

	public void setChatSession(ChatSession chatSession) {
		this.chatSession = chatSession;
	}

	public User getMessageFrom() {
		return messageFrom;
	}

	public Chat messageFrom(User user) {
		this.messageFrom = user;
		return this;
	}

	public void setMessageFrom(User user) {
		this.messageFrom = user;
	}

	public User getMessageTo() {
		return messageTo;
	}

	public Chat messageTo(User user) {
		this.messageTo = user;
		return this;
	}

	public void setMessageTo(User user) {
		this.messageTo = user;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Chat chat = (Chat) o;
		if (chat.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, chat.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Chat{" + "id=" + id + ", message='" + message + "'" + ", messageDate='" + messageDate + "'" + '}';
	}
}
