package de.telekom.sea2.ui;

import java.util.Scanner;

import de.telekom.sea2.lookup.Salutation;
import de.telekom.sea2.model.Person;
import de.telekom.sea2.persistence.PersonRepository;
import java.io.Closeable;
import java.io.IOException;

public class Menu implements Closeable {

	private PersonRepository list;
	private Scanner scanner = new Scanner(System.in);

	public void setMyList(PersonRepository myList) {
		list = myList;
	}

	@Override 
	public void close() {
		scanner.close();
		System.out.println("Das Programm ist jetzt beendet. Goodbye!!");
	}

	public void keepAsking() throws IOException {
		String choice;  
		do {
			showMenu();
			choice = this.inputMenu();
			checkMenu(choice);
		} while (!choice.toUpperCase().equals("Q"));
	}

	public void keepSearch() throws IOException {
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
	 */
	private void checkMenu(String eingabe) throws IOException {
		switch (eingabe.toUpperCase()) {
		case "1":
			inputPerson();
			break;
		case "2":
			removePerson();
			break;
		case "3":
//			if (list.size() > 0) {
				listAllPersons();
//			} else
//				System.out.println("Die Liste der Teilnehmer ist leer!");
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
	 */
	private void checkSearchMenu(String eingabe) throws IOException {
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
	private void inputPerson() throws IOException {
		Person person = new Person();
		System.out.println("Anrede eingeben:");
		String salu = scanner.nextLine().trim();
		try {
			//person.setAnrede(Salutation.fromString(salu));
		} catch (Exception e) {
			System.out.println("Anrede darf nur Frau,Herr,Sonstiges sein!");
			return;
		}
		System.out.println("Vorname eingeben:");
		String name = scanner.nextLine().trim();
		//person.setVorname(name);

		System.out.println("Nachname eingeben:");
		String lastname = scanner.nextLine().trim();
		//person.setNachname(lastname);

		if ((name != "") || (lastname != "")) {
			list.create(person);
		} else
			System.out.println("Name und Nachname sind leer. Anmeldung konnte nicht erfolgen!\n"
					+ "bitte nochmal mit Auswahl '1' versuchen:");

	}

	// 2. Person löschen
	private void removePerson() {
		System.out.print("ID zum löschen: ");
		long l = scanner.nextLong();
		scanner.nextLine();

		if (!list.delete(l)) {
			System.out.println("=> Id: " + l + " nicht gefunden!");
		}
	}

	// 3. Personenliste zeigen
	private void listAllPersons() {

		System.out.println("---------------------------------------");
		System.out.println("  Inhalt der Liste  ");
		System.out.println("---------------------------------------");
		printList(list);
		freeCapacity();
	}

	// 4. Personenliste löschen
	private void removeAll() {
		list.deleteAll();
	}

	// 5. Anzahl freien Plätze zeigen
	private void freeCapacity() {
//		System.out.println("Freie Plätze: " + (list.getMaxsize() - list.size())
//				+ " - belegt: " + list.size());
		System.out.println(" in construction " );
		
	}

	// 6. Suche Personen
	private void searchPerson() throws IOException {
		keepSearch();
	}
	
	// 6.1 Suche Personen nach Vorname
	private void searchPersonByName() {

		System.out.print("Vorname zum Suchen: ");
		String lastname = scanner.nextLine();
//		PersonRepository subset = list.get(lastname);
//		printLable(subset);
	}

	// 6.2 Suche Personen nach Nachname
	private void searchPersonBySurname() {

		System.out.print("Nachname zum Suchen: ");
		String lastname = scanner.nextLine();
//		PersonRepository subset = list.get(lastname);
//		printLable(subset);
	}

	// 6.3 Suche Personen nach Teilnehmer-Id
	private void searchPersonById() {

		System.out.print("Id zum Suchen: ");
		long l = scanner.nextLong();
		scanner.nextLine();
//		PersonRepository subset = list.get(l);
//		printLable(subset);
	}

	// Header für die ausgegebene Trefferliste
	private void printLable(Object subset) {
		System.out.println("---------------------------------------");
		System.out.println("             Such-Ergebnis             ");
		System.out.println("---------------------------------------");
//		printList((MyList) subset);
	}

	// Ausgabe der Array-Elemente bzw. Teilnehmerliste
	private void printList(PersonRepository list) {
//		if (list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				if (list.get(i) != null)
//					System.out.println("Id: " + ((Person) list.get(i)).getId() + " - "
//							+ ((Person) list.get(i)).getAnrede().toString() + " " + ((Person) list.get(i)).getVorname()
//							+ " " + ((Person) list.get(i)).getNachname());
//			}
//		}
//		else 
			System.out.println("keinen Treffer!!");
		System.out.println();
	}

} // Ende Class Menu