package de.telekom.sea2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import de.telekom.sea2.persistence.PersonRepository;
import de.telekom.sea2.ui.*;

public class SeminarApp {

	private static SeminarApp theInstance; // theInstanz = null, wegen static
	private Connection connection;
	private PersonRepository personRepo;
		
	
	/**
	 * Singelton: SeminarApp darf nur einmal instanziiert sein
	 * 
	 * @return
	 */
	public static SeminarApp getRootApp() {
		if (theInstance == null) {
			theInstance = new SeminarApp();
		}
		return theInstance;
	}

	// constructor for new instance of SeminarApp
	private SeminarApp() {
	}

	private void dbConnenct() throws ClassNotFoundException, SQLException {
		final String DRIVER = "org.mariadb.jdbc.Driver";
		final String dbConfig = "jdbc:mariadb://localhost:3306/seadb?user=seauser&password=seapass";
		Class.forName(DRIVER);
		connection = DriverManager.getConnection(dbConfig);	
	}
	
	private void dbDisconnect() throws SQLException {
		if (connection != null && !connection.isClosed())  
			connection.close();
	}
	
	public void run(String[] args) throws Exception {

		try (Menu menu = new Menu()) {
			dbConnenct();
			personRepo = new PersonRepository(connection);
			menu.setRepository(personRepo);
			menu.keepAsking();
			dbDisconnect();
				
		} catch (SQLException sx) {
			System.out.println(" Fehler bei DB-Verbindung! ");
		  }			
		}
} // Ende Klasse SeminarApp
