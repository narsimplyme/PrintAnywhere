package com.capstone.model;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.Client;
import com.capstone.util.Constants;

@Repository
public class ClientDao {
	@Autowired
	private DataSource dataSource;
	private SqlSession sqlSession;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<Client> selectClient() {
		try {
			
			List<Client> clientArray = sqlSession.selectList("client.selectClient");
			return clientArray;
		} catch (PersistenceException e) {
			return null;
		}
	}

	public int updateClient(int clientId) {
		try {
			int count = sqlSession.update("client.updateClient", clientId);
			if(count > 0) {
				return Constants.DB_RES_CODE_5;
			}
		} catch (PersistenceException e) {
			return Constants.DB_RES_CODE_9;
		}
		return Constants.DB_RES_CODE_6;
	}
}
