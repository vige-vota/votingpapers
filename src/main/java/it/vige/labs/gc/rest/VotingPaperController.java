package it.vige.labs.gc.rest;

import static it.vige.labs.gc.Authorities.ADMIN_ROLE;
import static it.vige.labs.gc.Authorities.hasRole;
import static it.vige.labs.gc.JavaAppApplication.TOPIC_NAME;
import static it.vige.labs.gc.bean.votingpapers.State.PREPARE;
import static it.vige.labs.gc.rest.Type.BIGGER;
import static it.vige.labs.gc.rest.Validator.defaultMessage;
import static it.vige.labs.gc.rest.Validator.errorMessage;
import static java.awt.Color.PINK;
import static java.lang.String.format;

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
import it.vige.labs.gc.websocket.WebSocketClient;

@RestController
@CrossOrigin(origins = "*")
public class VotingPaperController {

	public final static VotingPapers votingPapers = new VotingPapers();

	@Autowired
	private WebSocketClient webSocketClient;

	@Autowired
	private Validator validator;

	@Autowired
	private Environment environment;

	@GetMapping(value = "/votingPapers")
	public VotingPapers getVotingPapers() {
		String[] profiles = environment.getActiveProfiles();
		return generateVotingPapers(profiles);
	}

	@GetMapping(value = "/state")
	public Messages setState(@RequestParam("state") State state) throws Exception {
		if (hasRole(ADMIN_ROLE)) {
			votingPapers.setState(state);
			webSocketClient.getStompSession().send(TOPIC_NAME, votingPapers);
			return defaultMessage;
		} else
			return errorMessage;
	}

	@PostMapping(value = "/votingPapers")
	public Messages setVotingPapers(@RequestBody VotingPapers postVotingPapers) throws Exception {
		if (votingPapers.getState() == PREPARE && hasRole(ADMIN_ROLE))
			return addVotingPapers(postVotingPapers);
		else
			return errorMessage;
	}

	@PostMapping(value = "/import")
	public Messages setVotingPapers(@RequestParam("file") @RequestPart("file") MultipartFile file) throws Exception {
		if (votingPapers.getState() == PREPARE && hasRole(ADMIN_ROLE)) {
			ObjectMapper objectMapper = new ObjectMapper();
			VotingPapers postVotingPapers = objectMapper.readValue(file.getInputStream(), VotingPapers.class);
			return addVotingPapers(postVotingPapers);
		} else
			return errorMessage;
	}

	private Messages addVotingPapers(VotingPapers postVotingPapers) throws Exception {
		Messages messages = validator.validate(postVotingPapers);
		if (messages.isOk()) {
			votingPapers.setVotingPapers(postVotingPapers.getVotingPapers());
			webSocketClient.getStompSession().send(TOPIC_NAME, votingPapers);
		}
		return messages;
	}

	public static VotingPapers generateVotingPapers(String[] profiles) {
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
				page.setType(BIGGER.name());
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
}
