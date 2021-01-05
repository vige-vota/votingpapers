package it.vige.labs.gc.bean.votingpapers;

public abstract class Identifier {

	protected int id;

	protected String name;

	public Identifier() {
	}
	
	public Identifier(int id, String name) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
