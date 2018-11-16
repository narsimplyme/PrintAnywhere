package com.capstone.dto;

public class Client {
	
	String clientId;
	String clientName;
	String clientLocation;
	String clientIp;
	String clientMac;
	String clientLastSeen;
	
	public Client() {
		super();
	}
	public Client(String clientId, String clientName, String clientLocation, String clientIp, String clientMac,
			String clientLastSeen) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientLocation = clientLocation;
		this.clientIp = clientIp;
		this.clientMac = clientMac;
		this.clientLastSeen = clientLastSeen;
	}
	
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientLocation() {
		return clientLocation;
	}
	public void setClientLocation(String clientLocation) {
		this.clientLocation = clientLocation;
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
		builder.append(", clientLocation=");
		builder.append(clientLocation);
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
