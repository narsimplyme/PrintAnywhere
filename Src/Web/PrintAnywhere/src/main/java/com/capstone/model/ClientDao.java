package com.capstone.model;

import java.util.List;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.capstone.dto.Client;

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
}
