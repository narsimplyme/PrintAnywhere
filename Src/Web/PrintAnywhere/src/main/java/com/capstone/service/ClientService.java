package com.capstone.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capstone.dto.Client;
import com.capstone.model.ClientDao;
import com.capstone.util.Constants;
import com.capstone.util.DistMap;

@Service
public class ClientService {

	private ClientDao clientDao;
	
	@Autowired
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public List<Client> selectNearClient(float latitude, float longitude) {
		ArrayList<DistMap> distMapArray = new ArrayList<>();
		List<Client> clientArray = clientDao.selectClient();
		List<Client> tempArray = new ArrayList<Client>();
		for (int i = 0; i < clientArray.size(); i++) {
			double cLat = Double.parseDouble(clientArray.get(i).getClientLatitude());
			double cLon = Double.parseDouble(clientArray.get(i).getClientLongitude());
			double dist = getDistance(latitude, longitude, cLat, cLon);
			distMapArray.add(new DistMap(clientArray.get(i).getClientId(), dist));
		}
		clientArray = new ArrayList<Client>();
		distMapArray.sort(new Comparator<DistMap>() {

			@Override
			public int compare(DistMap o1, DistMap o2) {
				Double a = o1.getDist();
				Double b = o2.getDist();
				return a.compareTo(b);
			}
			
		});
		
		for (int i = 0; i < Constants.NEAR_CLIENT_CNT; i++) {
			if(i >= distMapArray.size())
				break;
			int clientId = distMapArray.get(i).getCustomerId();
			for (int j = 0; j < clientArray.size(); j++) {
				if(clientArray.get(j).getClientId() == clientId) {
					tempArray.add(clientArray.get(i));	
				}	
			}
		}
		return tempArray;
	}
	
	double getDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1) , 2));
	}
	
}
