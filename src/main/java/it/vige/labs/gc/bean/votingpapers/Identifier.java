package it.vige.labs.gc.bean.votingpapers;

import java.util.List;

import it.vige.labs.gc.users.User;

public abstract class Identifier {

	protected int id;

	protected String name;

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

	protected int generateId(VotingPapers votingPapers) {
		int result = votingPapers.getNextId() + 1;
		votingPapers.setNextId(result);
		return result;
	}

	protected void addNewIds(VotingPapers votingPapers, VotingPapers allVotingPapers, User user) {

	}

	protected void addNewIds(VotingPapers votingPapers, VotingPapers allVotingPapers) {
		for (VotingPaper votingPaper : votingPapers.getVotingPapers()) {
			if (votingPaper.getId() < 0)
				votingPaper.setId(generateId(allVotingPapers));
			List<Party> parties = votingPaper.getParties();
			if (parties != null)
				for (Party party : parties) {
					if (party.getId() < 0)
						party.setId(generateId(allVotingPapers));
					List<Candidate> candidates = party.getCandidates();
					if (candidates != null)
						for (Candidate candidate : candidates)
							if (candidate.getId() < 0)
								candidate.setId(generateId(allVotingPapers));
				}
			List<Group> groups = votingPaper.getGroups();
			if (groups != null)
				for (Group group : groups) {
					if (group.getId() < 0)
						group.setId(generateId(allVotingPapers));
					List<Party> grParties = group.getParties();
					if (grParties != null)
						for (Party party : grParties) {
							if (party.getId() < 0)
								party.setId(generateId(allVotingPapers));
							List<Candidate> candidates = party.getCandidates();
							if (candidates != null)
								for (Candidate candidate : candidates)
									if (candidate.getId() < 0)
										candidate.setId(generateId(allVotingPapers));
						}
				}
		}
	}

	protected abstract void update(Identifier identifier, User user);

}
