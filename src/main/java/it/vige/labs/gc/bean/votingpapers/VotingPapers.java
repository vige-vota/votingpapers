package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.bean.votingpapers.State.PREPARE;

import java.util.ArrayList;
import java.util.List;

import it.vige.labs.gc.users.User;

public class VotingPapers {

	private State state;

	private int nextId;

	private List<VotingPaper> votingPapers = new ArrayList<VotingPaper>();

	public List<VotingPaper> getVotingPapers() {
		return votingPapers;
	}

	public void setVotingPapers(List<VotingPaper> votingPapers) {
		this.votingPapers = votingPapers;
	}

	public void setVotingPapers(List<VotingPaper> votingPapers, User user) {
		this.votingPapers.forEach(votingPaper -> {
			votingPapers.forEach(postVotingPaper -> {
				if (votingPaper.getId() == postVotingPaper.getId())
					votingPaper.update(postVotingPaper, user);
			});
		});
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = false;
		if (state == PREPARE) {
			if (remoteVotingPapers != null)
				result = remoteVotingPapers.getVotingPapers().parallelStream()
						.allMatch(votingPaper -> votingPaper.validate(remoteVotingPapers, user));
			else
				result = true;
		}
		return result;
	}

	public void addNewIds(VotingPapers votingPapers, User user) {
		if (state == PREPARE)
			getVotingPapers().forEach(votingPaper -> votingPaper.addNewIds(votingPapers, user));
	}
}
