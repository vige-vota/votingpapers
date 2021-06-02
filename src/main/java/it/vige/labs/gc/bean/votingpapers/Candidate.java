package it.vige.labs.gc.bean.votingpapers;

import static it.vige.labs.gc.rest.Sex.F;
import static it.vige.labs.gc.rest.Sex.M;

import it.vige.labs.gc.users.User;

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

	@Override
	public void update(Identifier identifier, User user) {
		if (user.getBlock() == getId()) {
			Candidate candidate = (Candidate) identifier;
			setImage(candidate.getImage());
			setName(candidate.getName());
			setSex(candidate.getSex());
		}
	}

	@Override
	public boolean hasBlock(User user) {
		int block = user.getBlock();
		return block != -1 && id == block;
	}

	@Override
	public boolean validate(VotingPapers remoteVotingPapers, User user) {
		boolean result = super.validate(remoteVotingPapers, user);
		if (result && !name.trim().contains(" "))
			result = false;
		if (result && sex != M.asChar() && sex != F.asChar())
			result = false;
		if (result && image != null)
			result = image.length() <= Validation.IMAGE_SIZE;
		return result;
	}

	@Override
	protected int duplicate(int result, int id) {
		if (this.id == id)
			result++;
		return result;
	}

}
