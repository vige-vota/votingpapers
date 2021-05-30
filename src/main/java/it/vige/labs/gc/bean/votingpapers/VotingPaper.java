package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.rest.Type.BIGGER;
import static it.vige.labs.gc.rest.Type.BIGGER_PARTYGROUP;
import static it.vige.labs.gc.rest.Type.LITTLE;
import static it.vige.labs.gc.rest.Type.LITTLE_NOGROUP;

import java.util.List;

import it.vige.labs.gc.users.User;

public class VotingPaper extends Validation {

	private int maxCandidates;

	private String color;

	private String type;

	private boolean disjointed;

	private int zone = -1;

	private List<Group> groups;

	private List<Party> parties;

	public int getMaxCandidates() {
		return maxCandidates;
	}

	public void setMaxCandidates(int maxCandidates) {
		this.maxCandidates = maxCandidates;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isDisjointed() {
		return disjointed;
	}

	public void setDisjointed(boolean disjointed) {
		this.disjointed = disjointed;
	}

	public int getZone() {
		return zone;
	}

	public void setZone(int zone) {
		this.zone = zone;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Party> getParties() {
		return parties;
	}

	public void setParties(List<Party> parties) {
		this.parties = parties;
	}

	@Override
	public void update(Identifier identifier, User user) {
		VotingPaper votingPaper = (VotingPaper) identifier;
		if (user.getBlock() == getId()) {
			setColor(votingPaper.getColor());
			setDisjointed(votingPaper.isDisjointed());
			setGroups(votingPaper.getGroups());
			setMaxCandidates(votingPaper.getMaxCandidates());
			setName(votingPaper.getName());
			setParties(votingPaper.getParties());
			setType(votingPaper.getType());
			setZone(votingPaper.getZone());
		} else {
			StringBuilder found = new StringBuilder();
			if (groups != null && votingPaper.getGroups() != null)
				groups.forEach(group -> {
					votingPaper.getGroups().forEach(postGroup -> {
						if (group.getId() == postGroup.getId()) {
							if (user.getBlock() == group.getId()) {
								group.setImage(postGroup.getImage());
								group.setName(postGroup.getName());
								group.setParties(postGroup.getParties());
								group.setSubtitle(postGroup.getSubtitle());
							} else
								group.update(postGroup, user);
							found.append(true);
						}
					});
				});
			if (found.length() == 0 && parties != null && votingPaper.getParties() != null)
				parties.forEach(party -> {
					votingPaper.getParties().forEach(postParty -> {
						if (party.getId() == postParty.getId())
							if (user.getBlock() == party.getId())
								parties.add(postParty);
							else
								party.update(postParty, user);
					});
				});
		}
	}

	@Override
	public boolean hasBlock(User user) {
		int block = user.getBlock();
		return block != -1
				&& (id == block || (groups != null && groups.parallelStream().anyMatch(group -> group.hasBlock(user)))
						|| (parties != null && parties.parallelStream().anyMatch(group -> group.hasBlock(user))));
	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = super.validate(remoteVotingPapers, user);
		if (result && (color == null || color.isEmpty() || color.length() != 6 || color.startsWith("#")))
			result = false;
		if (result && (type == null || type.isEmpty() || !hasType()))
			result = false;
		if (result && zone == -1 && (type.equals(BIGGER.asString()) || type.equals(BIGGER_PARTYGROUP.asString())))
			result = false;
		if (result && groups != null)
			result = groups.parallelStream().allMatch(group -> group.validate(remoteVotingPapers, user));
		if (result && parties != null)
			if (groups != null)
				result = false;
			else
				result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers, user));
		if (result && parties != null)
			result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers, user));
		if (result && groups != null && type != null && type.equals(LITTLE_NOGROUP.asString()))
			result = false;
		return result;
	}

	private boolean hasType() {
		return type.equals(BIGGER.asString()) || type.equals(BIGGER_PARTYGROUP.asString())
				|| type.equals(LITTLE.asString()) || type.equals(LITTLE_NOGROUP.asString());
	}
}
