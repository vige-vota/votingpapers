package it.vige.labs.gc.rest;

import static it.vige.labs.gc.JavaAppApplication.TOPIC_NAME;
import static it.vige.labs.gc.bean.votingpapers.State.PREPARE;
import static it.vige.labs.gc.rest.Type.LITTLE;
import static it.vige.labs.gc.rest.Validator.defaultMessage;
import static it.vige.labs.gc.rest.Validator.errorMessage;
import static it.vige.labs.gc.users.Authorities.ADMIN_ROLE;
import static java.awt.Color.PINK;
import static java.lang.String.format;
import static java.util.Calendar.DATE;
import static java.util.Calendar.getInstance;
import static java.util.stream.Collectors.toList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import it.vige.labs.gc.bean.votingpapers.Candidate;
import it.vige.labs.gc.bean.votingpapers.Group;
import it.vige.labs.gc.bean.votingpapers.Party;
import it.vige.labs.gc.bean.votingpapers.State;
import it.vige.labs.gc.bean.votingpapers.VotingDate;
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

	public VotingPapers getAllVotingPapers() {
		String[] profiles = environment.getActiveProfiles();
		return generateVotingPapers(profiles);
	}

	@GetMapping(value = "/votingPapers")
	public VotingPapers getVotingPapers(@RequestParam(required = false) String all,
			@RequestParam(required = false) String info) throws Exception {
		VotingPapers votingPapers = getAllVotingPapers();
		VotingPapers localVotingPapers = (VotingPapers) votingPapers.clone();
		filterByInfo(localVotingPapers.getVotingPapers(), info);
		if (all != null)
			return localVotingPapers;
		if (votingPapers.getState().equals(PREPARE) && authorities.hasRole(ADMIN_ROLE)
				|| !votingPapers.getState().equals(PREPARE) && authorities.isAnonymous())
			return localVotingPapers;
		else {
			User user = authorities.getUser();
			localVotingPapers
					.setVotingPapers(localVotingPapers.getVotingPapers().parallelStream().filter(votingPaper -> {
						return isValid(votingPaper, user);
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
		if (getVotingPapers(null, null).getState() == PREPARE && (user.hasRole(ADMIN_ROLE) || user.hasBlock()))
			return addVotingPapers(postVotingPapers, user);
		else
			return errorMessage;
	}

	@PostMapping(value = "/import")
	public Messages setVotingPapers(@RequestParam("file") @RequestPart("file") MultipartFile file) throws Exception {
		User user = authorities.getUser();
		if (getVotingPapers(null, null).getState() == PREPARE && (user.hasRole(ADMIN_ROLE) || user.hasBlock())) {
			ObjectMapper objectMapper = new ObjectMapper();
			VotingPapers postVotingPapers = objectMapper.readValue(file.getInputStream(), VotingPapers.class);
			return addVotingPapers(postVotingPapers, user);
		} else
			return errorMessage;
	}

	private boolean isValid(VotingPaper votingPaper, User user) {
		State state = votingPapers.getState();
		if (state == PREPARE) {
			return votingPaper.hasBlock(user);
		} else {
			String zone = votingPaper.getZone();
			return (zone == null || user.getZones().contains(zone)) && votingPaper.getDates() != null
					&& votingPaper.getDates().parallelStream().anyMatch(votingDate -> votingDate.dateMatchTime());
		}
	}

	private Messages addVotingPapers(VotingPapers postVotingPapers, User user) throws Exception {
		Messages messages = validator.validate(postVotingPapers, user);
		if (messages.isOk()) {
			postVotingPapers.addNewIds(votingPapers, user);
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
					votingPapers.setNextId(votingPapersFromJson.getNextId());
					votingPapersFromJson.getVotingPapers().forEach(e -> {
						updateDates(e, -1, 3);
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				List<VotingPaper> pages = new ArrayList<VotingPaper>();
				VotingPaper page = new VotingPaper();
				page.setName("VOTA");
				addDates(page);
				page.setType(LITTLE.asString());
				String color = format("%02x%02x%02x", PINK.getRed(), PINK.getGreen(), PINK.getBlue());
				page.setColor(color);
				page.setGroups(new ArrayList<Group>());
				pages.add(page);
				votingPapers.setVotingPapers(pages);
				votingPapers.setState(PREPARE);
				votingPapers.setNextId(1);
			}
		}
		return votingPapers;
	}

	public void setAuthorities(Authorities authorities) {
		this.authorities = authorities;
	}

	private void filterByInfo(List<VotingPaper> votingPapers, String info) {
		if (info != null && votingPapers != null) {
			for (VotingPaper votingPaper : votingPapers) {
				List<Group> groups = votingPaper.getGroups();
				if (groups != null)
					for (Group group : groups) {
						group.setImage(null);
						List<Party> parties = group.getParties();
						if (parties != null)
							for (Party party : parties) {
								party.setImage(null);
								List<Candidate> candidates = party.getCandidates();
								if (candidates != null)
									for (Candidate candidate : candidates) {
										candidate.setImage(null);
									}
							}
					}
				List<Party> parties = votingPaper.getParties();
				if (parties != null)
					for (Party party : parties) {
						party.setImage(null);
						List<Candidate> candidates = party.getCandidates();
						if (candidates != null)
							for (Candidate candidate : candidates) {
								candidate.setImage(null);
							}
					}
			}
		}
	}

	public void addDates(VotingPaper votingPaper) {
		addDates(votingPaper, 3, 3);
	}

	public void updateDates(VotingPaper votingPaper) {
		updateDates(votingPaper, 3, 3);
	}

	public void updateDates(VotingPaper votingPaper, int startingDays, int endingDays) {
		Date startingDate = addDays(new Date(), startingDays);
		if (votingPaper.getDates() != null)
			for (VotingDate votingDate : votingPaper.getDates()) {
				votingDate.setStartingDate(startingDate);
				votingDate.setEndingDate(addDays(startingDate, endingDays));
				startingDate = addDays(startingDate, endingDays + 1);
			}
	}

	public void addDates(VotingPaper votingPaper, int startingDays, int endingDays) {
		Date startingDate = addDays(new Date(), startingDays);
		Date endingDate = addDays(startingDate, endingDays);
		if (votingPaper.getDates() == null)
			votingPaper.setDates(new ArrayList<VotingDate>());
		VotingDate votingDate = new VotingDate();
		votingDate.setStartingDate(startingDate);
		votingDate.setEndingDate(endingDate);
		votingPaper.getDates().add(votingDate);
	}

	public Date addDays(Date date, int days) {
		Calendar cal = getInstance();
		cal.setTime(date);
		cal.add(DATE, days); // minus number would decrement the days
		return cal.getTime();
	}
}
