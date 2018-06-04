package com.whosmyserver.model;

public class userData {

	private String id, username, password, name, status, email, imagePath;

	public userData() {
	}

	public userData(String id, String username, String password, String name,
			 String email, String imagePath, String status) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.imagePath = imagePath;
		this.status = status;
	}

	// getters & setters

	@Override
	public String toString() {
		return "userData [id=" + id + ", username=" + username + ", password="
				+ password + ", name=" + name +  ", " + "email=" + email
				+ ", imagePath=" + imagePath + ", status=" + status + "]";
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getId() {

		return id;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getUsername() {

		return username;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public String getPassword() {

		return password;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public String getStatus() {

		return status;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getEmail() {

		return email;
	}
	public void setImagePath(String imagePath) {

		this.imagePath = imagePath;
	}

	public String getImagePath() {

		return imagePath;
	}
}
