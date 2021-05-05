package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;

import it.vige.labs.gc.users.User;

public class Validation extends Identifier {

	public static final int IMAGE_SIZE = 60000;

	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = false;
		if (user.hasRole(ADMIN_ROLE) || user.getBlock() == id) {
			if (name != null && !name.isEmpty() && !duplicate(id, remoteVotingPapers))
				result = true;
		} else
			result = true;
		return result;
	};

	private boolean duplicate(int id, VotingPapers remoteVotingPapers) {
		int result = 0;
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
