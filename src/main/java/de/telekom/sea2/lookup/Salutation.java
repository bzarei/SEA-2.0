package de.telekom.sea2.lookup;

public enum Salutation {

	FRAU, HERR, SONSTIGES;

	public static Salutation fromString(String salu) {
		switch (salu.toUpperCase()) {
		case "FRAU":
		case "F":
		case "FR":
			return FRAU;
		case "HERR":
		case "M":
		case "HR":
		case "H":
			return HERR;
		case "SONSTIGES":
		case "S":
		case "DIVERS":
		case "D":
			return SONSTIGES;
		default:
			throw new IllegalArgumentException(
					"Unexpected value: " + salu + " - only Frau, Herr, Sonstiges are allowed");
		}
	}

	@Override
	public String toString() {
		switch (this) {
		case FRAU:
			return "Frau";
		case HERR:
			return "Herr";
		case SONSTIGES:
			return "Sonstiges";
		default:
			throw new RuntimeException("Unexpected case! ");
		}
	}
}
