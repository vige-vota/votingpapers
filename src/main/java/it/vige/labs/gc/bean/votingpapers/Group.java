package it.vige.labs.gc.bean.votingpapers;

import java.util.List;
import java.util.Map;

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
			List<Party> grParties = group.getParties();
			if (parties != null && grParties != null)
				parties.forEach(party -> {
					grParties.forEach(postParty -> {
						if (party.getId() == postParty.getId())
							party.update(postParty, user);
					});
				});
		}
	}

	@Override
	public boolean hasBlock(User user) {
		int block = user.getBlock();
		return block != -1 && (id == block
				|| (parties != null && parties.parallelStream().anyMatch(group -> group.hasBlock(user))));
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

	@Override
	protected int duplicate(int result, int id) {
		if (this.id == id)
			result++;
		List<Party> parties = getParties();
		if (parties != null)
			for (Party party : parties)
				result = party.duplicate(result, id);
		return result;
	}

	@Override
	protected boolean hasId(int block, int id, Map<Integer, Validation> validations) {
		VotingPaper matchedVotingPaper = (VotingPaper) validations.get(0);
		Group matchedGroup = (Group) validations.get(1);
		VotingPaper votingPaper = (VotingPaper) validations.get(3);
		if (this.id == block) {
			matchedGroup.setId(this.id);
			if (block == id)
				return true;
		}
		if (this.id == id && match(votingPaper, matchedVotingPaper))
			return true;
		if (parties != null)
			for (Party party : parties)
				if (party.hasId(block, id, validations))
					return true;
		return false;
	}

}
