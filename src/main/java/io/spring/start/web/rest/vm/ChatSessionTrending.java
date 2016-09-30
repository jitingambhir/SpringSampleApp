package io.spring.start.web.rest.vm;

public class ChatSessionTrending {

	private String topic;
	private Long count;

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

}
