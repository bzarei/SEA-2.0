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
	
	/**
	 * this method is used for adding a new person into table personen.
	 * if reference address of given person is null nothing to do and
	 * the method returns false otherwise inserts person into the table 
	 * and returns true. 
	 * Id for each person is calculated from the size(): next free id = size()+1
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public boolean create(Person p) throws SQLException {
	
		if (p == null) 
			return false;
		
		query = "INSERT INTO personen (ID, SALUTATION, NAME, LASTNAME) VALUES ( ?, ?, ?, ? )";		
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, size()+1);
			ps.setByte(2, p.getSalutation().toByte());
			ps.setString(3, p.getName());
			ps.setString(4, p.getLastname());
			ps.execute();
		} catch (SQLException ex) {
			System.out.println(" Ein Fehler beim INSERT in DB aufgetretten! ");
			System.out.println(ex.getLocalizedMessage());
			System.out.println(ex.getSQLState());
			return false;
		  }
		return true;
	}
	
	/**
	 * this method looks for given id in the table personen.
	 * if id is found method returns all attributes of this person in form  of 
	 * class Person.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public Person get(long id) throws SQLException {
		
		query = "SELECT * FROM personen WHERE id=?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			result = ps.executeQuery();
			if (result.next()) {
				Person person = new Person();
				person.setId(result.getLong(1));
				person.setSalutation(Salutation.fromByte(result.getByte(2)));
				person.setName(result.getString(3));
				person.setLastname(result.getString(4));
				result.close();
				return person;
			} 
			result.close();
			return null;
		} catch (SQLException ex) {
			System.out.println(" Ein Fehler in Methode get(id) aufgetretten! ");
			ex.getSQLState();
			ex.getLocalizedMessage();
			return null;
		  }
	}
	
	/**
	 * in this method will be checked if the person who should be changed exists in
	 * the table. If the reference address of this given person is null or id of
	 * given person doesn't exists in the table nothing to do and the method returns false
	 * otherwise the row for this id will be updated and method returns true. 
	 * @param p
	 * @return
	 * @throws SQLException
	 */
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
			return false;
		  }	
		return true;
	}
	
	/**
	 * in this method it will be checked if the given person p exists in the table.
	 * if reference address of given person is null or the person doesn't exist in the table
	 * nothing to do and the method returns false, otherwise found person 
	 * will be deleted from the table. 
	 * @param p
	 * @return
	 * @throws SQLException
	 */
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
			return false;
		  }
		return true;
	}
	
	/**
	 * in this method it will be checked if the given id exists in the table.
	 * if no nothing to do and the method returns false, otherwise found id 
	 * will be deleted from the table. 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(long id) throws SQLException {
		
		if (get(id) == null)   // person doesn't exist in DB
			return false;
		
		query = "DELETE FROM personen WHERE id=?";	
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setLong(1, id);
			ps.execute();
		} catch (SQLException ex) {
			System.out.println("Ein Fehler beim DELETE mit ID aufgetretten! ");
			ex.getSQLState();
			ex.getLocalizedMessage();
			return false;
		  }
		return true;
	}
	
	/**
	 * At first it will be checked in this method if the table is empty.
	 * is not so all persons or lines will be deleted from table personen.
	 * @return
	 * @throws SQLException
	 */
	public boolean deleteAll() throws SQLException {
	
		query = "SELECT COUNT(*) FROM personen";
		try (Statement ps = connection.createStatement()) { 
			result = ps.executeQuery(query); 
			result.next();
			if (result.getInt(1) == 0) {
				result.close();
				return false;
			}	
			query = "DELETE FROM personen";
			ps.execute(query);
		} catch (SQLException ex) {
			System.out.println("Ein Fehler innerhalb Methode deleteAll() aufgetretten! ");
			ex.fillInStackTrace();
		  }
		result.close();
		return true; 
	}
	
	/**
	 * this method prints all attributes of a person from table personen on demand.
	 * @param result
	 * @throws SQLException
	 */
	private void printRecord(ResultSet result) throws SQLException {	
		while (result.next()) {
			System.out.println("|  " + result.getLong(1) + " |      " + result.getByte(2)
				+ " | " + result.getString(3) + " | " + result.getString(4));
		}		
	}
	
	/**
	 * in this method all records or persons will be printed from table personen.
	 * @throws SQLException
	 */
	public void printRecords() throws SQLException {
		query = "SELECT * FROM personen";
		try (Statement st = connection.createStatement()) {
			result = st.executeQuery(query);
			while (result.next()) {
				System.out.println("|  " + result.getLong(1) + " |      " + result.getByte(2)
					+ " | " + result.getString(3) + " | " + result.getString(4));
			}
		} catch (SQLException ex) {	result.close(); }
		result.close();
	}
	
	/**
	 * this method goes through the lines in table and stores in a list of Persons.
	 * Address of each line will be stored in an element of array list Person[].
	 * finally list of persons will be printed by using method printList().   
	 * @return
	 * @throws Exception
	 */
	public Person[] getAll() throws Exception {
			Person[] list = new Person[size()];
			result.beforeFirst();                   // move cursur to the beginning of first record
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
			result.close();
			return list;
	}
	
	/**
	 * this method returns all similar names. ctl is a control parameter 
	 * used to check whether a search should be carried out for first name or surname.
     * With ctrl = 1 (comming from Menu) will be found all similar first names 
     * and with ctl = 2 all similar last names will be found.  
	 * @param name
	 * @param ctl 
	 * @return list of persons if list not empty otherweise null!
	 * @throws SQLException
	 */
	public Person[] getSimilarNames(String name, String ctl) throws SQLException {
				
		Person[] list = new Person[size()];
		switch (ctl) { 
			case "1":
				query = "SELECT * FROM personen WHERE name like ?";
				break;
			case "2":
				query = "SELECT * FROM personen WHERE lastname like ?";
				break;
		}
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, name + "%");
			result = ps.executeQuery();
			if (result.first()) {
				result.beforeFirst();
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
			}
			result.close();
			return list;
		} catch (Exception ex) {
			System.out.println(" Ein Fehler in Methode getSimilarNames aufgetretten! ");
			ex.getLocalizedMessage();
			ex.fillInStackTrace();
		  }
		result.close();
		return list;
	}
	
	/**
	 * this method returns number of records in DB.
	 * This would be count of persons who are inserted in table personen.
	 * result.last() returns address of the last line in the table. 
	 * result.getRow() returns id of the last line. 
	 * @return number of the lines as integer
	 * @throws SQLException
	 */
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
	
	/**
	 * this is a private method known only for this Repository to print 
	 * all array elements of Person list: Person[].
	 * @param list
	 * @throws SQLException
	 */
	private void printList(Person[] list) throws Exception {
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
