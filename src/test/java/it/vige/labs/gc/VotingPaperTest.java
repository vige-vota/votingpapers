package it.vige.labs.gc;

import static it.vige.labs.gc.bean.votingpapers.State.PREPARE;
import static it.vige.labs.gc.bean.votingpapers.Validation.IMAGE_SIZE;
import static it.vige.labs.gc.rest.Sex.F;
import static it.vige.labs.gc.rest.Sex.M;
import static it.vige.labs.gc.rest.Type.BIGGER;
import static it.vige.labs.gc.rest.Type.LITTLE_NOGROUP;
import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;
import static it.vige.labs.gc.users.Authorities.CITIZEN_ROLE;
import static java.util.Arrays.asList;
import static java.util.Base64.getEncoder;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;

import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import com.c4_soft.springaddons.security.oauth2.test.annotations.OidcStandardClaims;
import com.c4_soft.springaddons.security.oauth2.test.annotations.keycloak.WithMockKeycloakAuth;

import it.vige.labs.gc.bean.votingpapers.Candidate;
import it.vige.labs.gc.bean.votingpapers.Group;
import it.vige.labs.gc.bean.votingpapers.Party;
import it.vige.labs.gc.bean.votingpapers.VotingPaper;
import it.vige.labs.gc.bean.votingpapers.VotingPapers;
import it.vige.labs.gc.messages.Messages;
import it.vige.labs.gc.rest.Sex;
import it.vige.labs.gc.rest.VotingPaperController;
import it.vige.labs.gc.users.Authorities;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles("dev")
public class VotingPaperTest {

	private Logger logger = getLogger(VotingPaperTest.class);

	private String BIG_IMAGE = "/parties/pinetina.jpg";
	private String RIGHT_IMAGE = "/parties/movimento5stelle.jpg";
	private final static String DEFAULT_USER = "669d3be4-4a67-41f5-a49d-5fe5157b6dd5";

	@Autowired
	private VotingPaperController votingPaperController;

	@Mock
	private RestTemplate restTemplate;

	@Autowired
	private Authorities authorities;

	@Test
	@WithMockKeycloakAuth(authorities = { ADMIN_ROLE }, oidc = @OidcStandardClaims(preferredUsername = DEFAULT_USER))
	public void votingPaperAdmin() throws Exception {
		VotingPapers votingPapers = votingPaperController.getVotingPapers();
		List<VotingPaper> list = votingPapers.getVotingPapers();
		assertEquals(PREPARE, votingPapers.getState(), "is prepare");
		assertEquals(4, list.size(), "size ok");
		logger.info(list + "");

		mockUsers(100, 6542276, 2523962, 2523228, 4);
		votingPapers = new VotingPapers();
		Messages messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "you must be admin or to have attributes");

		votingPapers.setState(PREPARE);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "you must be admin or to have attributes");

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

		votingPaper.setType(BIGGER.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "The BIGGER template need the zone");

		votingPaper.setZone(6542276);
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

		VotingPapers currentVotingPapers = votingPaperController.getVotingPapers();
		int nextId = currentVotingPapers.getNextId();
		assertEquals(362, nextId, "the next id is autogenerated");

		mockUsers(86, 6542276, 2523962, 2523228, 4);
		group.setId(-nextId);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "the id is ok");
		currentVotingPapers = votingPaperController.getVotingPapers();
		nextId = currentVotingPapers.getNextId();
		assertEquals(363, nextId, "the next id is autogenerated");
		assertEquals(363, currentVotingPapers.getVotingPapers().get(0).getGroups().get(0).getId(),
				"the next id is autogenerated");

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

		votingPaper.setType(LITTLE_NOGROUP.asString());
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

		currentVotingPapers = votingPaperController.getVotingPapers();
		nextId = currentVotingPapers.getNextId();
		assertEquals(363, nextId, "the next id is not autogenerated because a validation error");
		party.setId(-nextId);
		votingPaper.setType(BIGGER.asString());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "the id is ok");
		currentVotingPapers = votingPaperController.getVotingPapers();
		nextId = currentVotingPapers.getNextId();
		assertEquals(364, nextId, "the next id is autogenerated");
		assertEquals(364, currentVotingPapers.getVotingPapers().get(0).getGroups().get(0).getParties().get(0).getId(),
				"the next id is autogenerated");

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

		currentVotingPapers = votingPaperController.getVotingPapers();
		nextId = currentVotingPapers.getNextId();
		assertEquals(364, nextId, "the next id is the same");
		candidate.setId(-nextId);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertFalse(messages.isOk(), "the sex is mandatory and it must be represented by a M or a F");
		currentVotingPapers = votingPaperController.getVotingPapers();
		nextId = currentVotingPapers.getNextId();
		assertEquals(364, nextId, "the next id is the same because the candidate is not updated for an error");

		candidate.setSex(Sex.F.asChar());
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "the id is ok");
		currentVotingPapers = votingPaperController.getVotingPapers();
		nextId = currentVotingPapers.getNextId();
		assertEquals(365, nextId, "the next id is autogenerated");
		assertEquals(365, currentVotingPapers.getVotingPapers().get(0).getGroups().get(0).getParties().get(0)
				.getCandidates().get(0).getId(), "the next id is autogenerated");

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

	@Test
	@WithMockKeycloakAuth(authorities = { CITIZEN_ROLE }, oidc = @OidcStandardClaims(preferredUsername = DEFAULT_USER))
	public void votingPaperCitizen() throws Exception {

		mockUsers(100, 6542276, 2523962, 2523228, 4);
		VotingPapers votingPapers = new VotingPapers();
		votingPapers.setState(PREPARE);
		VotingPaper votingPaper = new VotingPaper();
		votingPapers.setVotingPapers(new ArrayList<VotingPaper>(asList(new VotingPaper[] { votingPaper })));
		votingPaper.setName("My first voting paper");
		votingPaper.setColor("ff0055");
		votingPaper.setType(BIGGER.asString());
		votingPaper.setZone(6542276);

		List<Group> groups = new ArrayList<Group>();
		Group group = new Group();
		groups.add(group);
		votingPaper.setGroups(groups);
		group.setName("my updated group");
		VotingPapers currentVotingPapers = votingPaperController.getVotingPapers();
		int nextId = currentVotingPapers.getNextId();
		assertEquals(362, nextId, "it is the last available id in the config-app.json");
		group.setId(-nextId);
		Party party = new Party();
		party.setId(101);
		party.setName("new party name");
		group.setParties(new ArrayList<Party>());
		group.getParties().add(party);

		Messages messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "schede code not in the user properties, so no group is updated");

		currentVotingPapers = votingPaperController.getVotingPapers();
		currentVotingPapers.getVotingPapers().forEach(vPaper -> {
			if (vPaper.getGroups() != null)
				vPaper.getGroups().forEach(gr -> {
					if (gr.getId() == group.getId())
						assertFalse(group.getName().equals(gr.getName()),
								"Group was not updated because the user is not authorized to update the group number 1");
				});
		});
		nextId = currentVotingPapers.getNextId();
		assertEquals(362, nextId, "we have not updated the group so the next id is the same");

		votingPaper.setId(86);
		group.setId(100);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "update is executed");
		currentVotingPapers = votingPaperController.getVotingPapers();
		currentVotingPapers.getVotingPapers().forEach(vPaper -> {
			if (vPaper.getId() == 86)
				assertFalse(vPaper.getName().equals(votingPaper.getName()),
						"Voting paper was not updated because the user can update only the code where it is authorized, the number 100, a group");
			if (vPaper.getGroups() != null)
				vPaper.getGroups().forEach(gr -> {
					if (gr.getId() == group.getId()) {
						assertTrue(group.getName().equals(gr.getName()),
								"Group was updated because the user is authorized to update the group number 100");
						gr.getParties().forEach(pr -> {
							if (pr.getId() == party.getId()) {
								assertTrue(party.getName().equals(pr.getName()),
										"Party was updated because the user is authorized to update the group number 100 and all its elements");
							}
						});
					}
				});
		});

		mockUsers(37, 6542276, 2523962, 2523228, 4);
		party.setName("new party name 2");
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "update is executed");
		currentVotingPapers = votingPaperController.getVotingPapers();
		currentVotingPapers.getVotingPapers().forEach(vPaper -> {
			if (vPaper.getGroups() != null)
				vPaper.getGroups().forEach(gr -> {
					gr.getParties().forEach(pr -> {
						if (pr.getId() == party.getId()) {
							assertTrue(party.getName().equals(pr.getName()),
									"Party was updated because the user is authorized to update the group number 101");
						}
					});
				});
		});

		mockUsers(132, 6536811, 3174529, 3165361, 2);
		votingPaper.setId(121);
		votingPaper.setGroups(null);
		votingPaper.setParties(new ArrayList<Party>());
		votingPaper.getParties().add(party);
		party.setId(123);
		party.setName("new party name");
		party.setCandidates(new ArrayList<Candidate>());
		Candidate candidate = new Candidate();
		candidate.setId(132);
		candidate.setSex(M.asChar());
		candidate.setName("new candidate name");
		party.getCandidates().add(candidate);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "update is executed");
		currentVotingPapers = votingPaperController.getVotingPapers();
		currentVotingPapers.getVotingPapers().forEach(vPaper -> {
			if (vPaper.getParties() != null)
				vPaper.getParties().forEach(pr -> {
					if (pr.getId() == party.getId()) {
						assertFalse(party.getName().equals(pr.getName()),
								"Party was not updated because the user is not authorized to update the party number 123");
					}
					pr.getCandidates().forEach(ca -> {
						if (ca.getId() == candidate.getId()) {
							assertTrue(candidate.getName().equals(ca.getName()),
									"Candidate was updated because the user is authorized to update the candidate number 132");
						}
					});
				});
		});

		mockUsers(123, 6536811, 3174529, 3165361, 2);
		votingPapers = votingPaperController.getVotingPapers();
		Party party123 = votingPapers.getVotingPapers().get(1).getParties().get(0);
		assertEquals(15, party123.getCandidates().size(), "the party has candidates");
		party123.setCandidates(null);
		messages = votingPaperController.setVotingPapers(votingPapers);
		assertTrue(messages.isOk(), "update is executed");
		votingPapers = votingPaperController.getVotingPapers();
		party123 = votingPapers.getVotingPapers().get(1).getParties().get(0);
		assertNull(party123.getCandidates(), "user can remove the candidates in the party");
		candidate.setId(-2);
		Candidate candidate2 = new Candidate();
		candidate2.setId(-3);
		candidate2.setSex(F.asChar());
		candidate2.setName("new candidate name 2");
		Candidate candidate3 = new Candidate();
		candidate3.setId(-4);
		candidate3.setSex(M.asChar());
		candidate3.setName("new candidate name 3");
		party123.getCandidates().add(candidate);
		party123.getCandidates().add(candidate2);
		party123.getCandidates().add(candidate3);
		messages = votingPaperController.setVotingPapers(votingPapers);
		votingPapers = votingPaperController.getVotingPapers();
		party123 = votingPapers.getVotingPapers().get(1).getParties().get(0);
		List<Candidate> candidates = party123.getCandidates();
		assertEquals(3, candidates.size(), "user is allowed to insert new candidates");
		assertEquals(363, candidates.get(0), "id is autogenerated");
		assertEquals(364, candidates.get(1), "id is autogenerated");
		assertEquals(365, candidates.get(2), "id is autogenerated");
	}

	private void mockUsers(int block, Integer... zones) {
		UserRepresentation user = new UserRepresentation();
		user.setUsername(DEFAULT_USER);
		Map<String, List<String>> attributes = new HashMap<String, List<String>>();
		attributes.put("block", asList(new String[] { block + "" }));
		attributes.put("zones", asList(zones).parallelStream().map(zone -> zone + "").collect(toList()));
		user.setAttributes(attributes);
		when(restTemplate.exchange(authorities.getFindUserByIdURI(DEFAULT_USER).toString(), GET, null,
				UserRepresentation.class)).thenReturn(new ResponseEntity<UserRepresentation>(user, OK));
		authorities.setRestTemplate(restTemplate);
		votingPaperController.setAuthorities(authorities);
	}

}
