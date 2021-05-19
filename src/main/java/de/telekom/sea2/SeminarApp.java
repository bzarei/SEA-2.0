package de.telekom.sea2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import de.telekom.sea2.persistence.PersonRepository;

public class SeminarApp {
	
	private static SeminarApp theInstance; // theInstanz = null, wegen static
	private final String DRIVER = "org.mariadb.jdbc.Driver";
	private PersonRepository personRepo;
	
	// constructor for new instance of SeminarApp
	private SeminarApp() {
	}

	public void run(String[] args) throws ClassNotFoundException, SQLException {

		Class.forName(DRIVER);
		try (Connection connection = DriverManager
				.getConnection("jdbc:mariadb://localhost:3306/seadb?user=seauser&password=seapass");) 
		{
			personRepo = new PersonRepository(connection);
		} catch (SQLException sx) {
			System.out.println(" Fehler bei Herstellung der DB-Verbindung! ");
			
		  }
	}

	/** Singelton: SeminarApp darf nur einmal instanziiert sein
	 * @return
	 */
	public static SeminarApp getRootApp() {
		if (theInstance == null) {
			theInstance = new SeminarApp();
		}
		return theInstance;
	}
} // Ende Klasse SeminarApp

