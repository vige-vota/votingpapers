package it.vige.labs.gc.rest;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.vige.labs.gc.JavaAppApplication;
import it.vige.labs.gc.messages.Messages;
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

	@GetMapping(value = "/votingPapers")
	public VotingPapers getVotingPapers() {
		return generateVotingPapers();
	}

	@PostMapping(value = "/votingPapers")
	public Messages setVotingPapers(@RequestBody VotingPapers postVotingPapers) throws Exception {
		Messages messages = validator.validate(postVotingPapers);
		if (messages.isOk()) {
			votingPapers.setVotingPapers(postVotingPapers.getVotingPapers());
			webSocketClient.getStompSession().send(JavaAppApplication.TOPIC_NAME, votingPapers);
		}
		return messages;
	}

	public static VotingPapers generateVotingPapers() {
		if (votingPapers.getVotingPapers().size() == 0) {
			InputStream jsonStream = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("mock/config-app.json");
			ObjectMapper objectMapper = new ObjectMapper();
			try {
				VotingPapers votingPapersFromJson = objectMapper.readValue(jsonStream, VotingPapers.class);
				votingPapers.setVotingPapers(votingPapersFromJson.getVotingPapers());
				votingPapers.setAdmin(votingPapersFromJson.isAdmin());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return votingPapers;
	}
}
