package de.telekom.sea2.model;

import de.telekom.sea2.lookup.Salutation;

public class Person {

	private static long idCounter = 1; // id for class without any instances
	private long id;                   // unique id for every person 
	private Salutation salu;
	private String name;
	private String lastname;

	/** constructor for this class:
	 * whenever this class is initiated unique id will be increased
	 * automatically. 
	 */
	public Person() {                        
		this.id = idCounter++;
	}
	
	public long getId() {
		return id;
	}
	
	public Salutation getSalutation() {
		return salu;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLastname() {
		return lastname;
	}
	
}
