package it.vige.labs.gc.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.vige.labs.gc.JavaAppApplication;
import it.vige.labs.gc.messages.Messages;
import it.vige.labs.gc.votingpapers.State;
import it.vige.labs.gc.votingpapers.VotingPapers;
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
		votingPapers.setState(state);
		webSocketClient.getStompSession().send(JavaAppApplication.TOPIC_NAME, votingPapers);
		return Validator.defaultMessage;
	}

	@PostMapping(value = "/votingPapers")
	public Messages setVotingPapers(@RequestBody VotingPapers postVotingPapers) throws Exception {
		if (votingPapers.getState() == State.PREPARE) {
			Messages messages = validator.validate(postVotingPapers);
			if (messages.isOk()) {
				votingPapers.setVotingPapers(postVotingPapers.getVotingPapers());
				webSocketClient.getStompSession().send(JavaAppApplication.TOPIC_NAME, votingPapers);
			}
			return messages;
		} else
			return Validator.errorMessage;
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
			} else
				votingPapers.setState(State.PREPARE);
		}
		return votingPapers;
	}
}
