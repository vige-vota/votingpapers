package it.vige.labs.gc.bean.votingpapers;

public enum Sex {
	M('M'), F('F');

	public char asChar() {
		return asChar;
	}

	private final char asChar;

	Sex(char asChar) {
		this.asChar = asChar;
	}
}
