package com.capstone.util;

public class DistMap {
	
	int customerId;
	double dist;
	
	public DistMap(int customerId, double dist) {
		super();
		this.customerId = customerId;
		this.dist = dist;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public double getDist() {
		return dist;
	}
	public void setDist(double dist) {
		this.dist = dist;
	}	
}
