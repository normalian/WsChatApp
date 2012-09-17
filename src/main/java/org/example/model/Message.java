package org.example.model;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873241139234603875L;
	private String data;
	private String type;

	public Message() {
		type = "message";
	}

	public Message(String message) {
		type = "message";
		data = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
