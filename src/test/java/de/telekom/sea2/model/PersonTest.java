package de.telekom.sea2.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

	private Person cut;

    @BeforeEach
    void setup() {
        cut = new Person();
        //System.out.println("Vorher " + cut);
    }

    @Test
    void setFirstname_test() {
    	//fail();   // mit dieser Methode wird Fehl geschlagen!!    	
    	// Arrange (Vorbereitung)
    	cut.setName("TestName");
    	
    	// Act
    	var result = cut.getName();
    	
    	// Assert (sicherstellen)
    	assertEquals("TestName", result);
    	assertSame("TestName", result);
    	assertNotEquals("Name", result);
    }
    
    @Test
    void setFirstname_null_test() {
    	cut.setName(null);
    	var result = cut.getName();
    	assertEquals(null, result);
    }

    @AfterEach
    void teardown() {
        cut = null;
        System.out.println("Nachher " + cut);

    }
}
