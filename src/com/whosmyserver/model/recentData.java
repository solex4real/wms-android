package com.whosmyserver.model;

public class recentData {

	private String id, type, res_id, name, address, date, imagePath;

	public recentData() {
	}

	public recentData(String id, String type, String res_id, String name,
			 String address, String imagePath, String date) {
		super();
		this.id = id;
		this.type = type;
		this.res_id = res_id;
		this.name = name;
		this.date = date;
		this.imagePath = imagePath;
		this.address = address;
	}

	// getters & setters

	@Override
	public String toString() {
		return "recentData [id=" + id + ", type=" + type + ", res_id="
				+ res_id + ", name=" + name +  ", " + "address=" + address
				+ ", imagePath=" + imagePath + ", date=" + date + "]";
	}

	public void setId(String id) {

		this.id = id;
	}

	public String getId() {

		return id;
	}

	public void setType(String type) {

		this.type = type;
	}

	public String getType() {

		return type;
	}

	public void setRestaurantId(String res_id) {

		this.res_id = res_id;
	}

	public String getRestaurantId() {

		return res_id;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getName() {

		return name;
	}

	public void setDate(String date) {

		this.date = date;
	}

	public String getDate() {

		return date;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getAddress() {

		return address;
	}
	public void setImagePath(String imagePath) {

		this.imagePath = imagePath;
	}

	public String getImagePath() {

		return imagePath;
	}
}
