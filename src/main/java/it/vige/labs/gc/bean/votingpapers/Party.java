package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;

import java.util.List;
import java.util.Map;

import it.vige.labs.gc.users.User;

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
	public boolean hasBlock(User user) {
		int block = user.getBlock();
		return block != -1 && (id == block
				|| (candidates != null && candidates.parallelStream().anyMatch(group -> group.hasBlock(user))));
	}

	@Override
	public void update(Identifier identifier, User user) {
		Party party = (Party) identifier;
		if (user.getBlock() == id) {
			setCandidates(party.getCandidates());
			setImage(party.getImage());
			setName(party.getName());
		} else {
			List<Candidate> pCandidates = party.getCandidates();
			if (candidates != null && pCandidates != null)
				candidates.forEach(candidate -> {
					pCandidates.forEach(postCandidate -> {
						if (candidate.getId() == postCandidate.getId())
							candidate.update(postCandidate, user);
					});
				});
		}
	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = super.validate(remoteVotingPapers, user);
		if (result && candidates != null) {
			result = candidates.parallelStream().allMatch(candidate -> candidate.validate(remoteVotingPapers, user));
			VotingPaper remoteVotingPaper = findById(remoteVotingPapers);
			if (remoteVotingPaper.getMaxCandidates() > candidates.size())
				result = false;
		}
		if (result && image != null)
			result = image.length() <= IMAGE_SIZE;
		return result;
	}

	@Override
	protected int duplicate(int result, int id) {
		if (getId() == id)
			result++;
		List<Candidate> candidates = getCandidates();
		if (candidates != null)
			for (Candidate candidate : candidates)
				result = candidate.duplicate(result, id);
		return result;
	}

	@Override
	protected boolean hasId(int block, int id, Map<Integer, Validation> validations) {
		VotingPaper matchedVotingPaper = (VotingPaper) validations.get(0);
		Party matchedParty = (Party) validations.get(2);
		VotingPaper votingPaper = (VotingPaper) validations.get(3);
		validations.put(4, this);
		if (getId() == block) {
			matchedParty.setId(getId());
			if (block == id)
				return true;
		}
		if (getId() == id && match(votingPaper, matchedVotingPaper))
			return true;
		List<Candidate> candidates = getCandidates();
		if (candidates != null)
			for (Candidate candidate : candidates)
				if (candidate.hasId(block, id, validations))
					return true;
		return false;
	}

	@Override
	protected void addNewIds(VotingPapers allVotingPapers, User user) {
		if (getId() < 0 && (user.hasRole(ADMIN_ROLE) || isInBlock(allVotingPapers, user)))
			setId(generateId(allVotingPapers));
		List<Candidate> candidates = getCandidates();
		if (candidates != null)
			for (Candidate candidate : candidates)
				candidate.addNewIds(allVotingPapers, user);
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
