package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;

import it.vige.labs.gc.users.User;

public abstract class Validation extends Identifier {

	public static final int IMAGE_SIZE = 60000;

	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = false;
		if (user.hasRole(ADMIN_ROLE) || isInBlock(remoteVotingPapers, user)) {
			if (name != null && !name.isEmpty() && !duplicate(remoteVotingPapers))
				result = true;
		} else
			result = true;
		return result;
	};

	public boolean hasParent() {
		return true;
	}

	public abstract boolean hasBlock(User user);

	public boolean isInBlock(VotingPapers remoteVotingPapers, User user) {
		int block = user.getBlock();
		return block != -1 && hasId(block, remoteVotingPapers);
	}

	private boolean hasId(int block, VotingPapers remoteVotingPapers) {
		VotingPaper matchedVotingPaper = null;
		Group matchedGroup = null;
		Party matchedParty = null;
		for (VotingPaper votingPaper : remoteVotingPapers.getVotingPapers()) {
			if (votingPaper.getId() == block) {
				matchedVotingPaper = votingPaper;
				if (block == id)
					return true;
			}
			if (votingPaper.getParties() != null)
				for (Party party : votingPaper.getParties()) {
					if (party.getId() == block) {
						matchedParty = party;
						if (block == id)
							return true;
					}
					if (party.getId() == id && votingPaper == matchedVotingPaper)
						return true;
					if (party.getCandidates() != null)
						for (Candidate candidate : party.getCandidates()) {
							if (candidate.getId() == block)
								if (block == id)
									return true;
							if (candidate.getId() == id && (votingPaper == matchedVotingPaper || party == matchedParty))
								return true;
						}
				}
			if (votingPaper.getGroups() != null)
				for (Group group : votingPaper.getGroups()) {
					if (group.getId() == block) {
						matchedGroup = group;
						if (block == id)
							return true;
					}
					if (group.getId() == id && votingPaper == matchedVotingPaper)
						return true;
					if (group.getParties() != null)
						for (Party party : group.getParties()) {
							if (party.getId() == block) {
								matchedParty = party;
								if (block == id)
									return true;
							}
							if (party.getId() == id && (votingPaper == matchedVotingPaper || group == matchedGroup))
								return true;
							if (party.getCandidates() != null)
								for (Candidate candidate : party.getCandidates()) {
									if (candidate.getId() == block)
										if (block == id)
											return true;
									if (candidate.getId() == id && (votingPaper == matchedVotingPaper
											|| group == matchedGroup || party == matchedParty))
										return true;
								}
						}
				}
		}
		return false;
	}

	private boolean duplicate(VotingPapers remoteVotingPapers) {
		int result = 0;
		if (id < 0)
			return false;
		for (VotingPaper votingPaper : remoteVotingPapers.getVotingPapers()) {
			if (votingPaper.getId() == id)
				result++;
			if (votingPaper.getParties() != null)
				for (Party party : votingPaper.getParties()) {
					if (party.getId() == id)
						result++;
					if (party.getCandidates() != null)
						for (Candidate candidate : party.getCandidates())
							if (candidate.getId() == id)
								result++;
				}
			if (votingPaper.getGroups() != null)
				for (Group group : votingPaper.getGroups()) {
					if (group.getId() == id)
						result++;
					if (group.getParties() != null)
						for (Party party : group.getParties()) {
							if (party.getId() == id)
								result++;
							if (party.getCandidates() != null)
								for (Candidate candidate : party.getCandidates())
									if (candidate.getId() == id)
										result++;
						}
				}
		}
		return result > 1;
	}
}
