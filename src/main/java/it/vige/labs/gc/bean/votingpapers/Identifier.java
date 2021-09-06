package it.vige.labs.gc.bean.votingpapers;

import it.vige.labs.gc.users.User;

public abstract class Identifier implements Cloneable {

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

	protected int generateId(VotingPapers votingPapers) {
		int result = votingPapers.getNextId() + 1;
		votingPapers.setNextId(result);
		return result;
	}

	protected abstract void addNewIds(VotingPapers allVotingPapers, VotingPapers remoteVotingPapers, User user);

	protected abstract void update(Identifier identifier, User user);
}
