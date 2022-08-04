package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.rest.Type.BIGGER;
import static it.vige.labs.gc.rest.Type.BIGGER_PARTYGROUP;
import static it.vige.labs.gc.rest.Type.LITTLE;
import static it.vige.labs.gc.rest.Type.LITTLE_NOGROUP;
import static it.vige.labs.gc.rest.Type.REFERENDUM;
import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;

import it.vige.labs.gc.users.User;

public class VotingPaper extends Validation {

	private int maxCandidates;

	private String color;

	private String type;

	private boolean disjointed;

	private String zone;

	private List<Group> groups;

	private List<Party> parties;

	private List<VotingDate> dates;

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

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
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

	public List<VotingDate> getDates() {
		return dates;
	}

	public void setDates(List<VotingDate> dates) {
		this.dates = dates;
	}

	@Override
	public void update(Identifier identifier, User user) {
		VotingPaper votingPaper = (VotingPaper) identifier;
		if (user.getBlock() == id) {
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
							group.update(postGroup, user);
							found.append(true);
						}
					});
				});
			if (found.length() == 0 && parties != null && votingPaper.getParties() != null)
				parties.forEach(party -> {
					votingPaper.getParties().forEach(postParty -> {
						if (party.getId() == postParty.getId())
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
		if (result && zone == null && (type.equals(BIGGER.asString()) || type.equals(BIGGER_PARTYGROUP.asString())))
			result = false;
		if (result && zone != null && (type.equals(LITTLE_NOGROUP.asString()) || type.equals(LITTLE.asString())))
			result = false;
		if (result && (dates == null || !dates.parallelStream().anyMatch(votingDate -> votingDate.dateMatchTime())
				|| !dates.parallelStream().allMatch(votingDate -> votingDate.startingMinor())))
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

	@Override
	protected int duplicate(int result, int id) {
		if (getId() == id)
			result++;
		List<Party> parties = getParties();
		if (parties != null)
			for (Party party : parties)
				result = party.duplicate(result, id);
		List<Group> groups = getGroups();
		if (groups != null)
			for (Group group : groups)
				result = group.duplicate(result, id);
		return result;
	}

	@Override
	protected boolean hasId(int block, int id, Map<Integer, Validation> validations) {
		VotingPaper matchedVotingPaper = (VotingPaper) validations.get(0);
		validations.put(3, this);
		if (getId() == block) {
			matchedVotingPaper.setId(getId());
			if (block == id)
				return true;
		}
		if (parties != null)
			for (Party party : parties)
				if (party.hasId(block, id, validations))
					return true;
		if (groups != null)
			for (Group group : groups)
				if (group.hasId(block, id, validations))
					return true;
		return false;
	}

	@Override
	protected void addNewIds(VotingPapers allVotingPapers, VotingPapers remoteVotingPapers, User user) {
		if (getId() < 0 && (user.hasRole(ADMIN_ROLE) || isInBlock(allVotingPapers, user)))
			setId(generateId(remoteVotingPapers));
		List<Party> parties = getParties();
		if (parties != null)
			for (Party party : parties)
				party.addNewIds(allVotingPapers, remoteVotingPapers, user);
		List<Group> groups = getGroups();
		if (groups != null)
			for (Group group : groups)
				group.addNewIds(allVotingPapers, remoteVotingPapers, user);
	}

	private boolean hasType() {
		return type.equals(BIGGER.asString()) || type.equals(BIGGER_PARTYGROUP.asString())
				|| type.equals(LITTLE.asString()) || type.equals(LITTLE_NOGROUP.asString())
				|| type.equals(REFERENDUM.asString());
	}

	@Override
	public Object clone() {
		try {
			VotingPaper votingPaper = (VotingPaper) super.clone();
			List<Group> groups = votingPaper.getGroups();
			List<Party> parties = votingPaper.getParties();
			if (groups != null)
				votingPaper.setGroups(groups.parallelStream().map(g -> (Group) g.clone()).collect(toList()));
			if (parties != null)
				votingPaper.setParties(parties.parallelStream().map(p -> (Party) p.clone()).collect(toList()));
			return votingPaper;
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
