package de.telekom.sea2.ui;

import java.util.Scanner;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonRepository;
import java.io.Closeable;
import java.io.IOException;
import java.sql.SQLException;

public class Menu implements Closeable {

	private PersonRepository personRepo;
	private Scanner scanner = new Scanner(System.in);

	public void setRepository(PersonRepository repo) {
		personRepo = repo;
	}

	@Override 
	public void close() {
		scanner.close();
		System.out.println("Das Programm ist jetzt beendet. Goodbye!!");
	}

	public void keepAsking() throws IOException, SQLException {
		String choice;  
		do {
			showMenu();
			choice = this.inputMenu();
			checkMenu(choice);
		} while (!choice.toUpperCase().equals("Q"));
	}

	public void keepSearch() throws IOException, SQLException {
		String choice;
		do {
			showSearchMenu();
			choice = this.inputMenu();
			checkSearchMenu(choice);
		} while (!choice.toUpperCase().equals("Q"));
	}

	private void showMenu() {

		System.out.println();
		System.out.println("***************************************");
		System.out.println("*  Hauptmenü - bitte Auswahl treffen  *");
		System.out.println("* ----------------------------------- *");
		System.out.println("*   1. Person anlegen                 *");
		System.out.println("*   2. Person löschen                 *");
		System.out.println("*   3. Personenliste zeigen           *");
		System.out.println("*   4. Personenliste löschen          *");
		System.out.println("*   5. Anzahl freie Plätze zeigen     *");
		System.out.println("*   6. Suche Personen                 *");
		System.out.println("* ----------------------------------- *");
		System.out.println("*   Q. Programm beenden               *");
		System.out.println("***************************************");
		System.out.print(">>");
	}
	
	private void showSearchMenu() {

		System.out.println();
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.println("|  Submenü Suchen - Auswahl 1,2,3     |");
		System.out.println("| ----------------------------------- |");
		System.out.println("|   1. Suche nach Vorname             |");
		System.out.println("|   2. Suche nach Nachname            |");
		System.out.println("|   3. Suche nach Id                  |");
		System.out.println("| ----------------------------------- |");
		System.out.println("|   Q. Zurück                         |");
		System.out.println("|~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~|");
		System.out.print(">>");
	}

	// nimmt die Usereingabe entgegen
	private String inputMenu() {
		var input = scanner.nextLine();
		return input;
	}

	/** case Evaluierung und Ausführungsaufruf
	 * diese Methode bietet Optionen aus Hauptmenü für einen Teilnehmer oder Teilnehmerliste
	 * 
	 * @param eingabe
	 * @throws MyException
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void checkMenu(String eingabe) throws IOException, SQLException {
		switch (eingabe.toUpperCase()) {
		case "1":
			inputPerson();
			break;
		case "2":
			removePerson();
			break;
		case "3":
			listAllPersons();
			break;
		case "4":
			removeAll();
			break;
		case "5":
			freeCapacity();
			break;
		case "6":
			searchPerson();
			break;
		case "Q":
			break;
		default:
			System.out.println("falsche Eingabe!!!");
			break;
		}
	}
	
	/** case Evaluierung und Ausführungsaufruf
	 * diese Methode bietet Optionen in Sub-Menü für Suche nach einer Person aus der Teilnehmerliste
	 * oder suche nach alle Personen die eine Suchkriterium entsprechen.
	 * Die Suchkriterien sind Suchen nach Vorname/Nachname/Teilnehmer-Id.
	 * Es werden alle ähnliche Namen (Vorname und Nachname) aus der Teilnehmerliste zurückgeliefert.
	 * 
	 * @param eingabe
	 * @throws MyException
	 * @throws IOException
	 * @throws SQLException 
	 */
	private void checkSearchMenu(String eingabe) throws IOException, SQLException {
		switch (eingabe.toUpperCase()) {
		case "1":
			searchPersonByName();
			break;
		case "2":
			searchPersonBySurname();
			break;
		case "3":
			searchPersonById();
			break;
		case "Q":
			break;
		default:
			System.out.println("falsche Eingabe!!!");
			break;
		}
	}

	// 1. Person anlegen
	private void inputPerson() throws IOException, SQLException {
		Person person = new Person();
		System.out.println("Anrede eingeben:");
		String salu = scanner.nextLine().trim();
		try {
			person.setSalutation(Salutation.fromString(salu));
		} catch (Exception e) {
			System.out.println("Anrede darf nur Mrs/Frau, Mr/Herr, Others/Sonstiges sein!");
			return;
		}
		System.out.println("Vorname eingeben:");
		String name = scanner.nextLine().trim();
		person.setName(name);

		System.out.println("Nachname eingeben:");
		String lastname = scanner.nextLine().trim();
		person.setLastname(lastname);

		if ((name != "") || (lastname != "")) {
			personRepo.create(person);
		} else
			System.out.println("Name und Nachname sind leer. Anmeldung konnte nicht erfolgen!\n"
					+ "bitte nochmal mit Auswahl '1' versuchen:");

	}

	// 2. Person löschen
	private void removePerson() throws SQLException {
		System.out.print("ID zum löschen: ");
		long l = scanner.nextLong();
		scanner.nextLine();
		if (!personRepo.delete(l)) {
			System.out.println("=> Id: " + l + " nicht gefunden!");
		}
	}

	// 3. Personenliste zeigen
	private void listAllPersons() throws SQLException {
		personRepo.printRecords();		
	}

	// 4. Personenliste löschen
	private void removeAll() throws SQLException {
		personRepo.deleteAll();
	}

	// 5. Anzahl freien Plätze zeigen
	private void freeCapacity() {
		System.out.println(" under construction " );
	}

	// 6. Suche Personen
	private void searchPerson() throws IOException, SQLException {
		keepSearch();
	}
	
	// 6.1 Suche Personen nach Vorname
	private void searchPersonByName() {
		System.out.println(" under construction " );
	}

	// 6.2 Suche Personen nach Nachname
	private void searchPersonBySurname() {
		System.out.println(" under construction " );
	}

	// 6.3 Suche Personen nach Teilnehmer-Id
	private void searchPersonById() throws SQLException {

		System.out.print("Id zum Suchen: ");
		long l = scanner.nextLong();
		scanner.nextLine();
		if (personRepo.get(l) != null) {
		System.out.println("ID: " + personRepo.get(l).getId() 
				+ "  Anrede: " + personRepo.get(l).getSalutation()
				+ "  Vorname: " + personRepo.get(l).getName() 
				+ "  Nachname: " + personRepo.get(l).getLastname());
		}
		else System.out.println("Person mit Id " + l + " nicht gefunden!");
	}

} // Ende Class Menu