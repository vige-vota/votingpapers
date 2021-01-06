package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.rest.Type.BIGGER;
import static it.vige.labs.gc.rest.Type.BIGGER_PARTYGROUP;
import static it.vige.labs.gc.rest.Type.LITTLE;
import static it.vige.labs.gc.rest.Type.LITTLE_NOGROUP;

import java.util.List;

public class VotingPaper extends Validation {

	private int maxCandidates;

	private String color;

	private String type;

	private boolean disjointed;

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
	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = super.validate(remoteVotingPapers);
		if (result && (color == null || color.isEmpty() || color.length() != 6 || color.startsWith("#")))
			result = false;
		if (result && (type == null || type.isEmpty() || !hasType()))
			result = false;
		if (result && groups != null)
			result = groups.parallelStream().allMatch(group -> group.validate(remoteVotingPapers));
		if (result && parties != null)
			if (groups != null)
				result = false;
			else
				result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers));
		if (result && parties != null)
			result = parties.parallelStream().allMatch(party -> party.validate(remoteVotingPapers));
		if (result && groups != null && type != null && type.equals(LITTLE_NOGROUP.asString()))
			result = false;
		return result;
	}

	private boolean hasType() {
		return type.equals(BIGGER.asString()) || type.equals(BIGGER_PARTYGROUP.asString())
				|| type.equals(LITTLE.asString()) || type.equals(LITTLE_NOGROUP.asString());
	}
}
