package it.vige.labs.gc.rest;

public enum Type {
	BIGGER("bigger"), LITTLE("little"), LITTLE_NOGROUP("little-nogroup"), BIGGER_PARTYGROUP("bigger-partygroup");

	public String asString() {
		return asString;
	}

	private final String asString;

	Type(String asString) {
		this.asString = asString;
	}
}
