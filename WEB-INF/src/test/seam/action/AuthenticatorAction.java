package test.seam.action;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.impl.api.PasswordCredential;
import org.picketlink.idm.impl.api.model.SimpleUser;

import test.biz.Profile;
import test.biz.User;

public class AuthenticatorAction implements Authenticator {

	@Inject
	EntityManager em;

	@Inject
	Credentials credentials;

	AuthenticationStatus status;
	org.picketlink.idm.api.User plUser;

	@Override
	public void authenticate() {
		List<?> results = em.createQuery("select u from User u where u.username=:username " +
				"and u.password=:password")
			.setParameter("username", credentials.getUsername())
			.setParameter("password", ((PasswordCredential) credentials.getCredential()).getValue())
			.getResultList();

		if (results.size() != 0) {
			User user = (User) results.get(0);
			for (Profile p : user.getProfileList()) {
			//	identity.addRole(p.getDescription());
			}
			status = AuthenticationStatus.SUCCESS;
			plUser = new SimpleUser(user.getUsername());
		} else {
			status = AuthenticationStatus.FAILURE;
			plUser = null;
		}
	}

	@Override
	public AuthenticationStatus getStatus() {
		return status;
	}

	@Override
	public void postAuthenticate() {
		// Empty
	}

	@Override
	public org.picketlink.idm.api.User getUser() {
		return plUser;
	}

}
