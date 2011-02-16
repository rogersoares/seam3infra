package test.conf;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.jboss.seam.persistence.SeamManaged;

public class PersistenceContextProvider {

	@SeamManaged
	@Produces
	@PersistenceUnit(unitName="testDatabase")
	@ConversationScoped
	@Default
	EntityManagerFactory producerField;

}