package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.bean.votingpapers.Type.REFERENDUM;
import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;
import static java.util.stream.Collectors.toList;

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
		if (user.getBlock() == id) {
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
		if (result && parties != null) {
			result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers, user));
			if (result && parties.size() > 2) {
				VotingPaper remoteVotingPaper = findVotingPaper(this);
				if (remoteVotingPaper.getType().equals(REFERENDUM.asString()))
					result = false;
			}
		}
		if (result && image != null)
			result = image.length() <= IMAGE_SIZE;
		return result;
	}

	@Override
	protected int duplicate(int result, int id) {
		if (getId() == id)
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
		if (getId() == block) {
			matchedGroup.setId(getId());
			if (block == id)
				return true;
		}
		if (getId() == id && match(votingPaper, matchedVotingPaper))
			return true;
		if (parties != null)
			for (Party party : parties)
				if (party.hasId(block, id, validations))
					return true;
		return false;
	}

	@Override
	protected void addNewIds(VotingPapers allVotingPapers, VotingPapers remoteVotingPapers, User user) {
		if (getId() < 0 && (user.hasRole(ADMIN_ROLE) || isInBlock(allVotingPapers, user)))
			setId(remoteVotingPapers.incrementNextId());
		List<Party> grParties = getParties();
		if (grParties != null)
			grParties.parallelStream().forEach(party -> {
				party.addNewIds(allVotingPapers, remoteVotingPapers, user);
			});
	}

	@Override
	protected void addParents() {
		List<Party> grParties = getParties();
		if (grParties != null)
			grParties.parallelStream().forEach(party -> {
				party.setParent(this);
				party.addParents();
			});
	}

	@Override
	public Object clone() {
		try {
			Group group = (Group) super.clone();
			List<Party> parties = group.getParties();
			if (parties != null)
				group.setParties(parties.parallelStream().map(p -> (Party) p.clone()).collect(toList()));
			return group;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}

}
