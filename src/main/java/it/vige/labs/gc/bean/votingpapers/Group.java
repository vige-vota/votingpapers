package it.vige.labs.gc.bean.votingpapers;

import java.util.List;

import it.vige.labs.gc.users.User;

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
	public void update(Identifier identifier, User user) {
		Group group = (Group) identifier;
		if (user.getBlock() == getId()) {
			setImage(group.getImage());
			setName(group.getName());
			setParties(group.getParties());
			setSubtitle(group.getSubtitle());
		} else {
			if (parties != null && group.getParties() != null)
				parties.forEach(party -> {
					group.getParties().forEach(postParty -> {
						if (party.getId() == postParty.getId())
							party.update(postParty, user);
					});
				});
		}
	}

	@Override
	public boolean hasBlock(User user) {
		int block = user.getBlock();
		return block == -1 || id == block
				|| (parties != null && parties.parallelStream().anyMatch(group -> group.hasBlock(user)));
	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = super.validate(remoteVotingPapers, user);
		if (result && parties != null)
			result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers, user));
		if (result && image != null)
			result = image.length() <= IMAGE_SIZE;
		return result;
	}

}
