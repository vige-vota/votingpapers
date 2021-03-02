package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.rest.Sex.F;
import static it.vige.labs.gc.rest.Sex.M;
import static java.util.stream.Collectors.toList;

import java.util.List;

public class Candidate extends Validation {

	private String image;

	private char sex;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public static Candidate findCandidate(VotingPapers votingPapers) {
		List<Candidate> candidates = votingPapers.getVotingPapers().parallelStream()
				.flatMap(votingPaper -> votingPaper.getGroups().stream()).flatMap(group -> group.getParties().stream())
				.flatMap(party -> party.getCandidates().stream()).collect(toList());
		return candidates.parallelStream().filter(e -> e.getId() == 0).findFirst().get();
	}

	public void update(VotingPapers votingPapers) {

	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = super.validate(remoteVotingPapers);
		if (result && !name.trim().contains(" "))
			result = false;
		if (result && sex != M.asChar() && sex != F.asChar())
			result = false;
		if (result && image != null)
			result = image.length() <= Validation.IMAGE_SIZE;
		return result;
	}

}
