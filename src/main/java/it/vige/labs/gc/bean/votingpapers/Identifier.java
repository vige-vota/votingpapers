package it.vige.labs.gc.bean.votingpapers;

import it.vige.labs.gc.users.User;

public abstract class Identifier {

	protected int id;

	protected String name;

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

	public abstract void update(Identifier identifier, User user);

}
