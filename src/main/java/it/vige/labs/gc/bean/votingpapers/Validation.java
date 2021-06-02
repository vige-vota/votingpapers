package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;

import java.util.HashMap;
import java.util.Map;

import it.vige.labs.gc.users.User;

public abstract class Validation extends Identifier {

	public static final int IMAGE_SIZE = 60000;

	protected boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = false;
		if (user.hasRole(ADMIN_ROLE) || isInBlock(remoteVotingPapers, user)) {
			if (name != null && !name.isEmpty() && !duplicate(remoteVotingPapers))
				result = true;
		} else
			result = true;
		return result;
	};

	protected abstract boolean hasBlock(User user);

	protected boolean match(Validation... validation) {
		boolean result = false;
		for (int i = 0; i < validation.length; i = i + 2)
			result = result || validation[i] == validation[i + 1];
		return result;
	}

	protected boolean isInBlock(VotingPapers remoteVotingPapers, User user) {
		int block = user.getBlock();
		return block != -1 && hasId(block, remoteVotingPapers);
	}

	protected abstract boolean hasId(int block, int id, Map<Integer, Validation> validations);

	private boolean hasId(int block, VotingPapers remoteVotingPapers) {
		VotingPaper matchedVotingPaper = new VotingPaper();
		Group matchedGroup = new Group();
		Party matchedParty = new Party();
		matchedVotingPaper.setId(-1);
		matchedGroup.setId(-1);
		matchedParty.setId(-1);
		Map<Integer, Validation> validations = new HashMap<Integer, Validation>();
		validations.put(0, matchedVotingPaper);
		validations.put(1, matchedGroup);
		validations.put(2, matchedParty);
		for (VotingPaper votingPaper : remoteVotingPapers.getVotingPapers())
			if (votingPaper.hasId(block, id, validations))
				return true;
		return false;
	}

	protected abstract int duplicate(int result, int id);

	private boolean duplicate(VotingPapers remoteVotingPapers) {
		int result = 0;
		if (id < 0)
			return false;
		for (VotingPaper votingPaper : remoteVotingPapers.getVotingPapers())
			result = votingPaper.duplicate(result, id);
		return result > 1;
	}
}
