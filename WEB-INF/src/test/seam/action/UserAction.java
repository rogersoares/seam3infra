package test.seam.action;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import test.biz.User;

@Named
@RequestScoped
public class UserAction {

	@Inject
	EntityManager em;

	private int id;
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		user = em.find(User.class, id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
