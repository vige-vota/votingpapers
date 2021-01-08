package it.vige.labs.gc;

import static it.vige.labs.gc.bean.votingpapers.State.PREPARE;
import static it.vige.labs.gc.bean.votingpapers.Validation.IMAGE_SIZE;
import static java.util.Arrays.asList;
import static java.util.Base64.getEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import it.vige.labs.gc.bean.votingpapers.Candidate;
import it.vige.labs.gc.bean.votingpapers.Group;
import it.vige.labs.gc.bean.votingpapers.Party;
import it.vige.labs.gc.bean.votingpapers.VotingPaper;
import it.vige.labs.gc.bean.votingpapers.VotingPapers;
import it.vige.labs.gc.messages.Messages;
import it.vige.labs.gc.rest.Sex;
import it.vige.labs.gc.rest.Type;
import it.vige.labs.gc.rest.VotingPaperController;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles("dev")
public class VotingPaperTest {

	private Logger logger = getLogger(VotingPaperTest.class);

	private String BIG_IMAGE = "/parties/pinetina.jpg";
	private String RIGHT_IMAGE = "/parties/movimento5stelle.jpg";

	@Autowired
	private VotingPaperController votingPaperController;

	@Test
	public void votingPaper() throws Exception {
		VotingPapers votingPapers = votingPaperController.getVotingPapers();
		List<VotingPaper> list = votingPapers.getVotingPapers();
		assertEquals(PREPARE, votingPapers.getState(), "is prepare");
		assertEquals(4, list.size(), "size ok");
		logger.info(list + "");

		votingPapers = new VotingPapers();
		Messages messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "you must be admin");

		votingPapers.setState(PREPARE);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "you must be admin");

		VotingPaper votingPaper = new VotingPaper();
		votingPapers.setVotingPapers(new ArrayList<VotingPaper>(asList(new VotingPaper[] { votingPaper })));
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "votingpaper without name, color and type");

		votingPaper.setName("My first voting paper");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "votingpaper without color and type");

		votingPaper.setColor("#344433");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "color must be 6 size and no # at the start");

		votingPaper.setColor("ff0055");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "votingpaper without type");

		votingPaper.setType("wrong_type");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the type must be one of the types described in the Type enum");

		votingPaper.setType(Type.BIGGER.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "mandatory fields are ok");

		List<Group> groups = new ArrayList<Group>();
		Group group = new Group();
		groups.add(group);
		votingPaper.setGroups(groups);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "group has no name");

		group.setName("my new group");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the id is duplicate");

		group.setId(1);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "the id is ok");

		BufferedInputStream image = (BufferedInputStream) this.getClass().getResourceAsStream(BIG_IMAGE);
		String bigImage = new String(getEncoder().encodeToString(image.readAllBytes()));
		group.setImage(bigImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "image is over the " + IMAGE_SIZE + " bytes");

		image = (BufferedInputStream) this.getClass().getResourceAsStream(RIGHT_IMAGE);
		String rightImage = new String(getEncoder().encodeToString(image.readAllBytes()));
		group.setImage(rightImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "image is under the " + IMAGE_SIZE + " bytes");

		votingPaper.setType(Type.LITTLE_NOGROUP.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the little-nogroup type cannot have groups");

		Party party = new Party();
		group.setParties(new ArrayList<Party>());
		group.getParties().add(party);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "group has no name");

		party.setName("my new party");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the id is duplicate");

		party.setId(2);
		votingPaper.setType(Type.BIGGER.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "the id is ok");

		party.setImage(bigImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "image is over the " + IMAGE_SIZE + " bytes");

		party.setImage(rightImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "image is under the " + IMAGE_SIZE + " bytes");

		Candidate candidate = new Candidate();
		party.setCandidates(new ArrayList<Candidate>());
		party.getCandidates().add(candidate);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "group has no name");

		candidate.setName("mynewcandidate");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the name need a space because it represents a name and a surname");

		candidate.setName("my new candidate");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the id is duplicate");

		candidate.setId(3);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the sex is mandatory and it must be represented by a M or a F");

		candidate.setSex(Sex.F.asChar());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "the id is ok");

		candidate.setImage(bigImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "image is over the " + IMAGE_SIZE + " bytes");

		candidate.setImage(rightImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "image is under the " + IMAGE_SIZE + " bytes");

		List<Party> parties = new ArrayList<Party>();
		votingPaper.setParties(parties);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "no groups and parties in the same voting paper");
	}

}
