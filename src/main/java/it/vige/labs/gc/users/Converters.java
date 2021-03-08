package it.vige.labs.gc.users;

import static java.lang.Integer.parseInt;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.keycloak.representations.idm.UserRepresentation;

public interface Converters {

	Function<UserRepresentation, User> UserRepresentationToUser = new Function<UserRepresentation, User>() {

		public User apply(UserRepresentation t) {
			User user = new User();
			user.setId(t.getUsername().toUpperCase());
			user.setName(t.getFirstName());
			user.setSurname(t.getLastName());
			Map<String, List<String>> attributes = t.getAttributes();
			if (attributes != null) {
				List<String> incomes = attributes.get("income");
				String incomeStr = "";
				if (incomes != null) {
					incomeStr = incomes.get(0);
					if (incomeStr != null && !incomeStr.isEmpty()) {
						int income = parseInt(incomeStr);
						user.setIncome(income);
					}
				}
			}

			return user;
		}
	};
}