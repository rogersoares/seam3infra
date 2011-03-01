package test.seam.util;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.remoting.annotations.WebRemote;

import test.biz.Product;

@Named("ajaxtest")
//@Restrict("#{s:hasRole('admin')}")
public class AjaxTest {

	@Inject
	EntityManager em;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String checkProductName() {
		if(name != null && name.length() > 0) {
			List<Product> l = em.createQuery("select p.name from Product p where p.name=:name")
			.setParameter("name", name)
			.setMaxResults(1)
			.getResultList();

			if (l.size() == 0) {
				return "Name " + name + " doesn't exist.";
			} else {
				return "Name " + name + " <b>already</b> exists.";
			}
		}

		return "";
	}

	@WebRemote
	public boolean checkDesc(String desc) {
		if(desc != null && desc.length() > 0) {
			List<Product> l = em.createQuery("select p.description from Product p where p.description=:desc")
			.setParameter("desc", desc)
			.setMaxResults(1)
			.getResultList();

			if (l.size() > 0) {
				return false;
			}
		}

		return true;
	}

}
