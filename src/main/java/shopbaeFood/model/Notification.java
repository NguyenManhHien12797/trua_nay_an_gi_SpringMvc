package shopbaeFood.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private MessageType type;
	private String sender;

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}

	public Notification() {
	}

	public Notification(Long id, MessageType type, String sender) {
		super();
		this.id = id;
		this.type = type;
		this.sender = sender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
