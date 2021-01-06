package it.vige.labs.gc.bean.votingpapers;

import java.util.List;

public class Group extends Validation {

	private String image;

	private String subtitle;

	private List<Party> parties;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Party> getParties() {
		return parties;
	}

	public void setParties(List<Party> parties) {
		this.parties = parties;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = super.validate(remoteVotingPapers);
		if (result && parties != null)
			result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers));
		if (result && image != null)
			result = image.length() <= IMAGE_SIZE;
		return result;
	}

}
