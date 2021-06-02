package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;

import java.util.List;

import it.vige.labs.gc.users.User;

public abstract class Validation extends Identifier {

	public static final int IMAGE_SIZE = 60000;

	protected boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = false;
		if (user.hasRole(ADMIN_ROLE) || isInBlock(remoteVotingPapers, user)) {
			if (name != null && !name.isEmpty() && !duplicate(remoteVotingPapers))
				result = true;
		} else
			result = true;
		return result;
	};

	protected abstract boolean hasBlock(User user);

	protected boolean isInBlock(VotingPapers remoteVotingPapers, User user) {
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
			List<Party> parties = votingPaper.getParties();
			if (parties != null)
				for (Party party : parties) {
					if (party.getId() == block) {
						matchedParty = party;
						if (block == id)
							return true;
					}
					if (party.getId() == id && votingPaper == matchedVotingPaper)
						return true;
					List<Candidate> candidates = party.getCandidates();
					if (candidates != null)
						for (Candidate candidate : candidates) {
							if (candidate.getId() == block)
								if (block == id)
									return true;
							if (candidate.getId() == id && (votingPaper == matchedVotingPaper || party == matchedParty))
								return true;
						}
				}
			List<Group> groups = votingPaper.getGroups();
			if (groups != null)
				for (Group group : groups) {
					if (group.getId() == block) {
						matchedGroup = group;
						if (block == id)
							return true;
					}
					if (group.getId() == id && votingPaper == matchedVotingPaper)
						return true;
					List<Party> grParties = group.getParties();
					if (grParties != null)
						for (Party party : grParties) {
							if (party.getId() == block) {
								matchedParty = party;
								if (block == id)
									return true;
							}
							if (party.getId() == id && (votingPaper == matchedVotingPaper || group == matchedGroup))
								return true;
							List<Candidate> candidates = party.getCandidates();
							if (candidates != null)
								for (Candidate candidate : candidates) {
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

	protected abstract int duplicate(int result, int id);

	private boolean duplicate(VotingPapers remoteVotingPapers) {
		int result = 0;
		if (id < 0)
			return false;
		for (VotingPaper votingPaper : remoteVotingPapers.getVotingPapers())
			result = votingPaper.duplicate(result, id);
		return result > 1;
	}
}
