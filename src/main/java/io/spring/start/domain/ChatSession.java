package io.spring.start.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * A ChatSession.
 */
@Entity
@Table(name = "chat_session")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "chatsession")
public class ChatSession implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "start_date", nullable = false)
	private ZonedDateTime startDate;

	@NotNull
	@Column(name = "source_url", nullable = false)
	private String sourceUrl;

	@Column(name = "email_flag")
	private Boolean emailFlag;

	@Column(name = "callback_flag")
	private Boolean callbackFlag;

	@Column(name = "questions_count")
	private Long questionsCount;

	@Column(name = "answers_count")
	private Long answersCount;

	@NotNull
	@Column(name = "topic", nullable = false)
	private String topic;

	@OneToMany(mappedBy = "chatSession")
	@Fetch(FetchMode.JOIN)
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Chat> chats = new HashSet<>();

	@Formula("(SELECT 1 FROM chat_session cs where cs.id = id)")
	private boolean activeStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public ChatSession startDate(ZonedDateTime startDate) {
		this.startDate = startDate;
		return this;
	}

	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public ChatSession sourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
		return this;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Boolean isEmailFlag() {
		return emailFlag;
	}

	public ChatSession emailFlag(Boolean emailFlag) {
		this.emailFlag = emailFlag;
		return this;
	}

	public void setEmailFlag(Boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public Boolean isCallbackFlag() {
		return callbackFlag;
	}

	public ChatSession callbackFlag(Boolean callbackFlag) {
		this.callbackFlag = callbackFlag;
		return this;
	}

	public void setCallbackFlag(Boolean callbackFlag) {
		this.callbackFlag = callbackFlag;
	}

	public Long getQuestionsCount() {
		return questionsCount;
	}

	public ChatSession questionsCount(Long questionsCount) {
		this.questionsCount = questionsCount;
		return this;
	}

	public void setQuestionsCount(Long questionsCount) {
		this.questionsCount = questionsCount;
	}

	public Long getAnswersCount() {
		return answersCount;
	}

	public ChatSession answersCount(Long answersCount) {
		this.answersCount = answersCount;
		return this;
	}

	public void setAnswersCount(Long answersCount) {
		this.answersCount = answersCount;
	}

	public String getTopic() {
		return topic;
	}

	public ChatSession topic(String topic) {
		this.topic = topic;
		return this;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Set<Chat> getChats() {
		return chats;
	}

	public ChatSession chats(Set<Chat> chats) {
		this.chats = chats;
		return this;
	}

	public ChatSession addChat(Chat chat) {
		chats.add(chat);
		chat.setChatSession(this);
		return this;
	}

	public ChatSession removeChat(Chat chat) {
		chats.remove(chat);
		chat.setChatSession(null);
		return this;
	}

	public void setChats(Set<Chat> chats) {
		this.chats = chats;
	}

	public boolean isActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ChatSession chatSession = (ChatSession) o;
		if (chatSession.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, chatSession.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "ChatSession{" + "id=" + id + ", startDate='" + startDate + "'" + ", sourceUrl='" + sourceUrl + "'"
				+ ", emailFlag='" + emailFlag + "'" + ", callbackFlag='" + callbackFlag + "'" + ", questionsCount='"
				+ questionsCount + "'" + ", answersCount='" + answersCount + "'" + ", topic='" + topic + "'" + '}';
	}
}
