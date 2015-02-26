package com.miniprojet.droplock2.data;

public class MyBoxesData {
	
	private String boxID;
	private String ownerID;
	private String status;
	private String boxKey;
	
	public MyBoxesData (String idBoxes, String ownerID, String status, String boxKey){
		super();
		this.boxID = idBoxes;
		this.ownerID = ownerID;
		this.status = status;
		this.boxKey = boxKey;
	}

	public String getBoxID() {
		return boxID;
	}

	public void setbBoxID(String boxID) {
		this.boxID = boxID;
	}

	public String getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBoxKey() {
		return boxKey;
	}

	public void setBoxKey(String boxKey) {
		this.boxKey = boxKey;
	}

}
