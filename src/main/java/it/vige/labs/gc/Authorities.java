package it.vige.labs.gc;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authorities {

	public final static String ADMIN_ROLE = "ROLE_ADMIN";

	public final static String CITIZEN_ROLE = "ROLE_CITIZEN";

	public static boolean hasRole(String... role) {
		List<String> roles = asList(role);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getAuthorities().parallelStream().anyMatch(r -> roles.contains(r.getAuthority()));
	}
}
