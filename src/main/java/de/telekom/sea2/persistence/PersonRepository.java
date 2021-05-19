package de.telekom.sea2.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.telekom.sea2.model.Person;

public class PersonRepository {

	private Connection connection;
	private String query;
	
	// constructor
	public PersonRepository(Connection co) {
		
		this.connection = co;
	}
	
	public boolean create(Person p) throws SQLException {
	
		query = "INSERT INTO personen (ID, ANREDE, VORNAME, NACHNAME) VALUES ( ?, ?, ?, ? )";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, p.getId());
		ps.setShort(2, (short) 2);
		ps.setString(3, p.getName());
		ps.setString(4, p.getLastname());
		ps.execute();
		return true;
	}
	
	public boolean update(Person p) throws SQLException {
		
		query = "UPDATE personen SET nachname=" + p.getLastname() + ", vorname=" 
		+ p.getName() + " WHERE id=" + p.getId();
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
		return true;
	}
		
	public boolean delete(Person p) throws SQLException {
		
		query = "DELETE FROM personen WHERE id=" + p.getId();
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
		return true;
	}
	
	public boolean delete(long id) throws SQLException {
		
		query = "DELETE FROM personen WHERE id=" + id;
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
		return true;
	}
	
	public boolean deleteAll() throws SQLException {
		
		query = "DELETE FROM personen WHERE id IN (SELECT * FROM personen)";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.execute();
		return true;
	}
	
	public void get(long id) throws SQLException {
		
		query = "SELECT * FROM personen WHERE id=" + id;
		Statement st = connection.createStatement();
		ResultSet rSet = st.executeQuery(query); 
	}
	
	public void get(String lastname) throws SQLException {
		
		query = "SELECT * FROM personen WHERE nachname=" + lastname;
		Statement st = connection.createStatement();
		ResultSet rSet = st.executeQuery(query); 
	}

	public void getAll() throws SQLException {
		
		query = "SELECT * FROM personen";
		Statement st = connection.createStatement();
		ResultSet rSet = st.executeQuery(query); 
	}
} // Ende Klasse PersonRepository
