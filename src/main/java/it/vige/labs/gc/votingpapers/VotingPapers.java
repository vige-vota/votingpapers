package it.vige.labs.gc.votingpapers;

import java.util.ArrayList;
import java.util.List;

public class VotingPapers {

	private State state;

	private List<VotingPaper> votingPapers = new ArrayList<VotingPaper>();

	public List<VotingPaper> getVotingPapers() {
		return votingPapers;
	}

	public void setVotingPapers(List<VotingPaper> votingPapers) {
		this.votingPapers = votingPapers;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = false;
		if (state == State.PREPARE) {
			if (remoteVotingPapers != null)
				result = remoteVotingPapers.getVotingPapers().parallelStream()
						.allMatch(votingPaper -> votingPaper.validate(remoteVotingPapers));
			else
				result = true;
		}
		return result;
	}
}
