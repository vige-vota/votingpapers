package it.vige.labs.gc.bean.votingpapers;

import com.fasterxml.jackson.annotation.JsonIgnore;

import it.vige.labs.gc.users.User;

public abstract class Identifier implements Cloneable {

	protected int id;

	protected String name;
	
	@JsonIgnore
	protected Identifier parent;

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

	public Identifier getParent() {
		return parent;
	}

	public void setParent(Identifier parent) {
		this.parent = parent;
	}

	protected abstract void addNewIds(VotingPapers allVotingPapers, VotingPapers remoteVotingPapers, User user);

	protected abstract void addParents();

	protected abstract void update(Identifier identifier, User user);
}
