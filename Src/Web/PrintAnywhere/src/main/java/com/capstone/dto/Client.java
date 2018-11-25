package com.capstone.dto;

public class Client {
	
	private int clientId;
	private String clientName;
	private String clientLatitude;
	private String clientLongitude;
	private String clientIp;
	private String clientMac;
	private String clientLastSeen;
	
	public Client() {
		super();
	}
	public Client(int clientId, String clientName, String clientLatitude, String clientLongitude, String clientIp, String clientMac,
			String clientLastSeen) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientLatitude = clientLatitude;
		this.clientLongitude = clientLongitude;
		this.clientIp = clientIp;
		this.clientMac = clientMac;
		this.clientLastSeen = clientLastSeen;
	}
	
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientLatitude() {
		return clientLatitude;
	}
	public void setClientLatitude(String clientLatitude) {
		this.clientLatitude = clientLatitude;
	}
	public String getClientLongitude() {
		return clientLongitude;
	}
	public void setClientLongitude(String clientLongitude) {
		this.clientLongitude = clientLongitude;
	}
	public String getClientIp() {
		return clientIp;
	}
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	public String getClientMac() {
		return clientMac;
	}
	public void setClientMac(String clientMac) {
		this.clientMac = clientMac;
	}
	public String getClientLastSeen() {
		return clientLastSeen;
	}
	public void setClientLastSeen(String clientLastSeen) {
		this.clientLastSeen = clientLastSeen;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Client [clientId=");
		builder.append(clientId);
		builder.append(", clientName=");
		builder.append(clientName);
		builder.append(", clientLatitude=");
		builder.append(clientLatitude);
		builder.append(", clientLongitude=");
		builder.append(clientLongitude);
		builder.append(", clientIp=");
		builder.append(clientIp);
		builder.append(", clientMac=");
		builder.append(clientMac);
		builder.append(", clientLastSeen=");
		builder.append(clientLastSeen);
		builder.append("]");
		return builder.toString();
	}
}
