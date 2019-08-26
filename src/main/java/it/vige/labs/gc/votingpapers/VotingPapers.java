package it.vige.labs.gc.votingpapers;

import java.util.ArrayList;
import java.util.List;

public class VotingPapers {

	private boolean admin;

	private List<VotingPaper> votingPapers = new ArrayList<VotingPaper>();

	public List<VotingPaper> getVotingPapers() {
		return votingPapers;
	}

	public void setVotingPapers(List<VotingPaper> votingPapers) {
		this.votingPapers = votingPapers;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = false;
		if (admin) {
			if (remoteVotingPapers != null)
				result = remoteVotingPapers.getVotingPapers().parallelStream()
						.allMatch(votingPaper -> votingPaper.validate(remoteVotingPapers));
			else result = true;
		}
		return result;
	}
}
