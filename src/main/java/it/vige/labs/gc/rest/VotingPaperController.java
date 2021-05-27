package it.vige.labs.gc.rest;

import static it.vige.labs.gc.JavaAppApplication.TOPIC_NAME;
import static it.vige.labs.gc.bean.votingpapers.State.PREPARE;
import static it.vige.labs.gc.rest.Type.LITTLE;
import static it.vige.labs.gc.rest.Validator.defaultMessage;
import static it.vige.labs.gc.rest.Validator.errorMessage;
import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;
import static java.awt.Color.PINK;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.vige.labs.gc.bean.votingpapers.Group;
import it.vige.labs.gc.bean.votingpapers.State;
import it.vige.labs.gc.bean.votingpapers.VotingPaper;
import it.vige.labs.gc.bean.votingpapers.VotingPapers;
import it.vige.labs.gc.messages.Messages;
import it.vige.labs.gc.users.Authorities;
import it.vige.labs.gc.users.User;
import it.vige.labs.gc.websocket.WebSocketClient;

@RestController
@CrossOrigin(origins = "*")
public class VotingPaperController {

	private VotingPapers votingPapers = new VotingPapers();

	@Autowired
	private WebSocketClient webSocketClient;

	@Autowired
	private Authorities authorities;

	@Autowired
	private Validator validator;

	@Autowired
	private Environment environment;

	@GetMapping(value = "/votingPapers")
	public VotingPapers getVotingPapers() {
		String[] profiles = environment.getActiveProfiles();
		return generateVotingPapers(profiles);
	}

	@GetMapping(value = "/votingPapersByUser")
	public VotingPapers getVotingPapersByUser() throws Exception {
		VotingPapers votingPapers = getVotingPapers();
		VotingPapers localVotingPapers = new VotingPapers();
		localVotingPapers.setVotingPapers(votingPapers.getVotingPapers());
		localVotingPapers.setState(votingPapers.getState());
		if (authorities.hasRole(ADMIN_ROLE))
			return localVotingPapers;
		else {
			User user = authorities.getUser();
			localVotingPapers
					.setVotingPapers(localVotingPapers.getVotingPapers().parallelStream().filter(votingPaper -> {
						return isInZone(votingPaper.getZone(), user);
					}).collect(toList()));
			return localVotingPapers;
		}
	}

	@GetMapping(value = "/state")
	public Messages setState(@RequestParam("state") State state) throws Exception {
		if (authorities.hasRole(ADMIN_ROLE)) {
			votingPapers.setState(state);
			webSocketClient.getStompSession().send(TOPIC_NAME, votingPapers);
			return defaultMessage;
		} else
			return errorMessage;
	}

	@PostMapping(value = "/votingPapers")
	public Messages setVotingPapers(@RequestBody VotingPapers postVotingPapers) throws Exception {
		User user = authorities.getUser();
		if (getVotingPapers().getState() == PREPARE && (user.hasRole(ADMIN_ROLE) || user.hasBlock()))
			return addVotingPapers(postVotingPapers, user);
		else
			return errorMessage;
	}

	@PostMapping(value = "/import")
	public Messages setVotingPapers(@RequestParam("file") @RequestPart("file") MultipartFile file) throws Exception {
		User user = authorities.getUser();
		if (getVotingPapers().getState() == PREPARE && (user.hasRole(ADMIN_ROLE) || user.hasBlock())) {
			ObjectMapper objectMapper = new ObjectMapper();
			VotingPapers postVotingPapers = objectMapper.readValue(file.getInputStream(), VotingPapers.class);
			return addVotingPapers(postVotingPapers, user);
		} else
			return errorMessage;
	}

	private boolean isInZone(int zone, User user) {
		return zone == -1 || user.getZones().contains(zone);
	}

	private Messages addVotingPapers(VotingPapers postVotingPapers, User user) throws Exception {
		Messages messages = validator.validate(postVotingPapers, user);
		if (messages.isOk()) {
			if (!user.hasRole(ADMIN_ROLE))
				votingPapers.setVotingPapers(postVotingPapers.getVotingPapers(), user);
			else
				votingPapers.setVotingPapers(postVotingPapers.getVotingPapers());
			webSocketClient.getStompSession().send(TOPIC_NAME, votingPapers);
		}
		return messages;
	}

	private VotingPapers generateVotingPapers(String[] profiles) {
		if (votingPapers.getVotingPapers().size() == 0) {
			if (profiles.length == 0 || profiles[0].equals("dev")) {
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					InputStream jsonStream = new FileInputStream("src/test/resources/mock/config-app.json");
					VotingPapers votingPapersFromJson = objectMapper.readValue(jsonStream, VotingPapers.class);
					votingPapers.setVotingPapers(votingPapersFromJson.getVotingPapers());
					votingPapers.setState(votingPapersFromJson.getState());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				List<VotingPaper> pages = new ArrayList<VotingPaper>();
				VotingPaper page = new VotingPaper();
				page.setName("VOTA");
				page.setType(LITTLE.asString());
				String color = format("%02x%02x%02x", PINK.getRed(), PINK.getGreen(), PINK.getBlue());
				page.setColor(color);
				page.setGroups(new ArrayList<Group>());
				pages.add(page);
				votingPapers.setVotingPapers(pages);
				votingPapers.setState(PREPARE);
			}
		}
		return votingPapers;
	}

	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}
}
