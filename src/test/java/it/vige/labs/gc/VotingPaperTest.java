package it.vige.labs.gc;

import static it.vige.labs.gc.bean.votingpapers.Validation.IMAGE_SIZE;
import static java.util.Base64.getEncoder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import it.vige.labs.gc.bean.votingpapers.Candidate;
import it.vige.labs.gc.bean.votingpapers.Group;
import it.vige.labs.gc.bean.votingpapers.Party;
import it.vige.labs.gc.bean.votingpapers.State;
import it.vige.labs.gc.bean.votingpapers.VotingPaper;
import it.vige.labs.gc.bean.votingpapers.VotingPapers;
import it.vige.labs.gc.messages.Messages;
import it.vige.labs.gc.rest.Sex;
import it.vige.labs.gc.rest.Type;
import it.vige.labs.gc.rest.VotingPaperController;

@RunWith(SpringRunner.class)
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
		assertEquals("is prepare", State.PREPARE, votingPapers.getState());
		assertEquals("size ok", 4, list.size());
		logger.info(list + "");

		votingPapers = new VotingPapers();
		Messages messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("you must be admin", messages.isOk());

		votingPapers.setState(State.PREPARE);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("you must be admin", messages.isOk());

		VotingPaper votingPaper = new VotingPaper();
		votingPapers.setVotingPapers(new ArrayList<VotingPaper>(Arrays.asList(new VotingPaper[] { votingPaper })));
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("votingpaper without name, color and type", messages.isOk());

		votingPaper.setName("My first voting paper");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("votingpaper without color and type", messages.isOk());

		votingPaper.setColor("#344433");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("color must be 6 size and no # at the start", messages.isOk());

		votingPaper.setColor("ff0055");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("votingpaper without type", messages.isOk());

		votingPaper.setType("wrong_type");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the type must be one of the types described in the Type enum", messages.isOk());

		votingPaper.setType(Type.BIGGER.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("mandatory fields are ok", messages.isOk());

		List<Group> groups = new ArrayList<Group>();
		Group group = new Group();
		groups.add(group);
		votingPaper.setGroups(groups);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("group has no name", messages.isOk());

		group.setName("my new group");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the id is duplicate", messages.isOk());

		group.setId(1);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("the id is ok", messages.isOk());

		BufferedInputStream image = (BufferedInputStream) this.getClass().getResourceAsStream(BIG_IMAGE);
		String bigImage = new String(getEncoder().encodeToString(image.readAllBytes()));
		group.setImage(bigImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("image is over the " + IMAGE_SIZE + " bytes", messages.isOk());

		image = (BufferedInputStream) this.getClass().getResourceAsStream(RIGHT_IMAGE);
		String rightImage = new String(getEncoder().encodeToString(image.readAllBytes()));
		group.setImage(rightImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("image is under the " + IMAGE_SIZE + " bytes", messages.isOk());

		votingPaper.setType(Type.LITTLE_NOGROUP.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the little-nogroup type cannot have groups", messages.isOk());

		Party party = new Party();
		group.setParties(new ArrayList<Party>());
		group.getParties().add(party);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("group has no name", messages.isOk());

		party.setName("my new party");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the id is duplicate", messages.isOk());

		party.setId(2);
		votingPaper.setType(Type.BIGGER.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("the id is ok", messages.isOk());

		party.setImage(bigImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("image is over the " + IMAGE_SIZE + " bytes", messages.isOk());

		party.setImage(rightImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("image is under the " + IMAGE_SIZE + " bytes", messages.isOk());

		Candidate candidate = new Candidate();
		party.setCandidates(new ArrayList<Candidate>());
		party.getCandidates().add(candidate);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("group has no name", messages.isOk());

		candidate.setName("mynewcandidate");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the name need a space because it represents a name and a surname", messages.isOk());

		candidate.setName("my new candidate");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the id is duplicate", messages.isOk());

		candidate.setId(3);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("the sex is mandatory and it must be represented by a M or a F", messages.isOk());

		candidate.setSex(Sex.F.asChar());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("the id is ok", messages.isOk());

		candidate.setImage(bigImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("image is over the " + IMAGE_SIZE + " bytes", messages.isOk());

		candidate.setImage(rightImage);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue("image is under the " + IMAGE_SIZE + " bytes", messages.isOk());

		List<Party> parties = new ArrayList<Party>();
		votingPaper.setParties(parties);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse("no groups and parties in the same voting paper", messages.isOk());
	}

}
