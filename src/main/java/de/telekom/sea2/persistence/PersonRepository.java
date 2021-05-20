package de.telekom.sea2.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.lookup.Salutation;

public class PersonRepository {

	private Connection connection;
	private String query;
	private ResultSet result;

	
	// constructor
	public PersonRepository(Connection co) {	
		this.connection = co;
	}
	
	public boolean create(Person p) throws SQLException {
	
		if (p == null) 
			return false;
		
		query = "INSERT INTO personen (ID, SALUTATION, NAME, LASTNAME) VALUES ( ?, ?, ?, ? )";		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, p.getId());
			ps.setByte(2, p.getSalutation().toByte());
			ps.setString(3, p.getName());
			ps.setString(4, p.getLastname());
			ps.execute();
		} catch (SQLException ex) {
			System.out.println(" Ein Fehler beim INSERT in DB aufgetretten! ");
			System.out.println(ex.getLocalizedMessage());
			System.out.println(ex.getSQLState());
		  }
		return true;
	}
	
	public Person get(long id) throws SQLException {
		
		query = "SELECT * FROM personen WHERE id=?";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setLong(1, id);
		result = ps.executeQuery();
		if (result.next()) {
			Person person = new Person();
			person.setId(result.getLong(1));
			person.setSalutation(Salutation.fromByte(result.getByte(2)));
			person.setName(result.getString(3));
			person.setLastname(result.getString(4));
			return person;
		}
		return null;
	}
	
	public boolean update(Person p) throws SQLException {

		if (p == null)                // person points of null 
			return false;
		if (get(p.getId()) == null)   // person doesn't exist in DB
			return false;
		
		query = "UPDATE personen SET name=?, lastname=? WHERE id=?"; 
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, p.getName());
			ps.setString(2, p.getLastname());
			ps.setLong(3, p.getId());
			ps.execute();
		} catch (SQLException ex) {
			System.out.println(" Ein Fehler beim UPDATE in DB aufgetretten! ");
			System.out.println(ex.getSQLState());
			System.out.println(ex.getLocalizedMessage());
		  }	
		return true;
	}
		
	public boolean delete(Person p) throws SQLException {
		
		if (p == null)                // person points of null 
			return false;
		if (get(p.getId()) == null)   // person doesn't exist in DB
			return false;
		
		query = "DELETE FROM personen WHERE id=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, p.getId());
			ps.execute();
		} catch (SQLException ex) {
			System.out.println(" Fehler beim DELETE in DB aufgetretten! ");
			System.out.println(ex.getSQLState());
			System.out.println(ex.getLocalizedMessage());
			ex.fillInStackTrace();
		  }
		return true;
	}
	
	public boolean delete(long id) throws SQLException {
		
		if (get(id) == null)   // person doesn't exist in DB
			return false;
		
		query = "DELETE FROM personen WHERE id=?";	
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException ex) {
			System.out.println("Ein Fehler beim DELETE mit ID aufgetretten! ");
			System.out.println(ex.getSQLState());
			System.out.println(ex.getLocalizedMessage());
		  }
		return true;
	}
	
	public boolean deleteAll() throws SQLException {
	
		query = "SELECT COUNT(*) FROM personen";
		try (Statement ps = connection.createStatement()) { 
			result = ps.executeQuery(query); 
			result.next();
			if (result.getInt(1) == 0) 
				return false;
			query = "DELETE FROM personen";
			ps.execute(query);
		} catch (SQLException ex) {
			System.out.println("Ein Fehler innerhalb Methode deleteAll() aufgetretten! ");
			ex.fillInStackTrace();
		  }
		return true; 
	}
		
	public void printRecord(ResultSet result) throws SQLException {	
		while (result.next()) {
			System.out.println("|  " + result.getLong(1) + " |      " + result.getByte(2)
				+ " | " + result.getString(3) + " | " + result.getString(4));
		}		
	}
	
	public void printRecords() throws SQLException {
		query = "SELECT * FROM personen";
		Statement st = connection.createStatement();
		result = st.executeQuery(query);
		while (result.next()) {
			System.out.println("|  " + result.getLong(1) + " |      " + result.getByte(2)
				+ " | " + result.getString(3) + " | " + result.getString(4));
		}		
	}
	
	public Person[] getAll() throws SQLException {
		
			Person[] list = new Person[size()];
			result.beforeFirst();                    // move cursur to the beginning of first record
			int index = 0;
			while (result.next()) {
				Person person = new Person();
				person.setId(result.getLong(1));
				person.setSalutation(Salutation.fromByte(result.getByte(2)));
				person.setName(result.getString(3));
				person.setLastname(result.getString(4));
				list[index++] = person;
			}
			printList(list);
			return list;
	}
	
	public int size() throws SQLException {
		
		query = "SELECT * FROM personen";
		try (Statement st = connection.createStatement()) {
			result = st.executeQuery(query);
			if (result.first()) {
				result.last();
				return result.getRow(); 
			}
		} catch (SQLException ex) {
			System.out.println("Ein Fehler bei der Ermittlung von size aufgetretten! ");
			ex.fillInStackTrace();
		  }
		return 0;
	}
	
	private void printList(Person[] list) throws SQLException {

		System.out.println("---------------------------------------");
		System.out.println("  Inhalt der Liste mit " + size() + " Personen:");
		System.out.println("---------------------------------------");
		
		if (size() > 0) {
			for (int i = 0; i < size(); i++) {
				if (list[i] != null)
					System.out.println("Id: " + list[i].getId() + " - "
							+ list[i].getSalutation() + " " + list[i].getName()
							+ " " + list[i].getLastname());
			}
		}
		else 
			System.out.println("Liste ist leer!!");
			System.out.println();
	}

	
} // Ende Klasse PersonRepository
