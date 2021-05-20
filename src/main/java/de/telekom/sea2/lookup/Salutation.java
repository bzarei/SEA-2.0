package de.telekom.sea2.lookup;

public enum Salutation {

	MRS, MR, OTHERS;

	public static Salutation fromString(String salu) {
		switch (salu.toUpperCase()) {
		case "MRS":
		case "FRAU":
		case "F":
		case "FR":
			return MRS;
		case "MR":
		case "HERR":
		case "M":
		case "HR":
		case "H":
			return MR;
		case "OTHERS":
		case "SONSTIGES":
		case "S":
		case "DIVERS":
		case "D":
			return OTHERS;
		default:
			throw new IllegalArgumentException(
					"Unexpected value: " + salu + " - only Frau/Mrs, Herr/Mr., Sonstiges/Others are allowed");
		}
	}

	@Override
	public String toString() {
		switch (this) {
		case MRS:
			return "Mrs";
		case MR:
			return "Mr";
		case OTHERS:
			return "Others";
		default:
			throw new RuntimeException("Unexpected case! ");
		}
	}

	public Byte toByte() {
		switch (this) {
		case MRS:
			return 1;
		case MR:
			return 2;
		case OTHERS:
			return 3;
		default:
			throw new RuntimeException("Unexpected case! ");
		}
	}

	public static Salutation fromByte(byte b) {
		switch (b) {
		case 1:
			return MRS;
		case 2:
			return MR;
		case 3:
			return OTHERS;
		default:
			throw new RuntimeException("Unexpected case! ");
		}
	}
}
