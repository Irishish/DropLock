package com.miniprojet.droplock2.data;

public class MyGroupsData {

	private String idGroups;
	private String groupOwnerID;
	private String groupBoxID;
	private String groupName;

	public MyGroupsData(String idGroups, String groupOwnerID, String groupBoxID, String groupName) {
		super();
		this.idGroups = idGroups;
		this.groupOwnerID = groupOwnerID;
		this.groupBoxID = groupBoxID;
		this.groupName = groupName;
	}

	public String getIDGroups() {
		return idGroups;
	}

	public void setIDGroups(String idGroups) {
		this.idGroups = idGroups;
	}

	public String getGroupOwnerID() {
		return groupOwnerID;
	}

	public void setGroupOwnerID(String groupOwnerID) {
		this.groupOwnerID = groupOwnerID;
	}

	public String getGroupBoxID() {
		return groupBoxID;
	}

	public void setGroupBoxID(String groupBoxID) {
		this.groupBoxID = groupBoxID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}