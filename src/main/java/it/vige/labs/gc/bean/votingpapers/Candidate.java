package it.vige.labs.gc.bean.votingpapers;

import it.vige.labs.gc.rest.Sex;

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
	public boolean validate(VotingPapers remoteVotingPapers) {
		boolean result = super.validate(remoteVotingPapers);
		if (result && !name.trim().contains(" "))
			result = false;
		if (result && sex != Sex.M.asChar() && sex != Sex.F.asChar())
			result = false;
		if (result && image != null)
			result = image.length() <= Validation.IMAGE_SIZE;
		return result;
	}

}
