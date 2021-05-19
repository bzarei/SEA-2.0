package de.telekom.sea2;

import java.sql.SQLException;

public class Main {
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
        
		SeminarApp app = SeminarApp.getRootApp();
		app.run(args);
	}
}
