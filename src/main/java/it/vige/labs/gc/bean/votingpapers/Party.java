package it.vige.labs.gc.bean.votingpapers;

import java.util.List;

public class Party extends Validation {

	private String image;

	private List<Candidate> candidates;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = super.validate(remoteVotingPapers);
		if (result && candidates != null) {
			result = candidates.parallelStream().allMatch(candidate -> candidate.validate(remoteVotingPapers));
			VotingPaper remoteVotingPaper = findById(remoteVotingPapers);
			if (remoteVotingPaper.getMaxCandidates() > candidates.size())
				result = false;
		}
		if (result && image != null)
			result = image.length() <= IMAGE_SIZE;
		return result;
	}

	private VotingPaper findById(VotingPapers remoteVotingPapers) {
		VotingPaper result = null;
		if (remoteVotingPapers.getVotingPapers() != null) {
			for (VotingPaper votingPaper : remoteVotingPapers.getVotingPapers()) {
				if (votingPaper.getParties() != null)
					for (Party party : votingPaper.getParties())
						if (party.getId() == id)
							result = votingPaper;
				if (votingPaper.getGroups() != null)
					for (Group group : votingPaper.getGroups())
						if (group.getParties() != null)
							for (Party party : group.getParties())
								if (party.getId() == id)
									result = votingPaper;
			}
		}
		return result;
	}
}
