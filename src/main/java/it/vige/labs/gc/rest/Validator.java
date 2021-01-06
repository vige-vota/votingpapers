package it.vige.labs.gc.rest;

import static it.vige.labs.gc.messages.Severity.error;
import static it.vige.labs.gc.messages.Severity.message;
import static java.util.Arrays.asList;

import org.springframework.stereotype.Component;

import it.vige.labs.gc.bean.votingpapers.VotingPapers;
import it.vige.labs.gc.messages.Message;
import it.vige.labs.gc.messages.Messages;

@Component
public class Validator {

	public final static String ok = "ok";

	public final static Messages defaultMessage = new Messages(true,
			asList(new Message[] { new Message(message, ok, "all is ok") }));

	public final static Messages errorMessage = new Messages(false,
			asList(new Message[] { new Message(error, "Generic error", "Validation not ok") }));

	public Messages validate(VotingPapers votingPapers) {
		boolean results = votingPapers.validate(votingPapers);
		return results ? defaultMessage : errorMessage;
	}
}
