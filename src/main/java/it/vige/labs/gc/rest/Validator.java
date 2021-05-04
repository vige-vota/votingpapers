package it.vige.labs.gc.rest;

import static it.vige.labs.gc.messages.Severity.error;
import static it.vige.labs.gc.messages.Severity.message;
import static java.util.Arrays.asList;

import org.springframework.stereotype.Component;

import it.vige.labs.gc.bean.votingpapers.VotingPapers;
import it.vige.labs.gc.messages.Message;
import it.vige.labs.gc.messages.Messages;
import it.vige.labs.gc.users.ModuleException;
import it.vige.labs.gc.users.User;

@Component
public class Validator {

	public final static String ok = "ok";

	public final static Messages defaultMessage = new Messages(true,
			asList(new Message[] { new Message(message, ok, "all is ok") }));

	public final static Messages errorMessage = new Messages(false,
			asList(new Message[] { new Message(error, "Generic error", "Validation not ok") }));

	public Messages validate(VotingPapers votingPapers, User user) throws ModuleException {
		boolean results = votingPapers.validate(votingPapers, user);
		return results ? defaultMessage : errorMessage;
	}
}
