package test.seam.action;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.logging.Logger;

import test.biz.Product;
import test.biz.ProductType;

@Named
@ConversationScoped
public class ProductAction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	EntityManager em;

//	@Inject
//	FacesMessages facesMessages;

	@Inject Conversation conversation;

	private Integer id;
	private Product product;

	@PostConstruct
	public void create() {
		System.out.println("---> created called");
		if (conversation.isTransient()) {
			conversation.begin();
		}

		// No form parameters set here yet
		product = new Product();
		product.setType(new ProductType());
	}

	public void preRenderView() {
		System.out.println("--> preRenderView called - cid:" + conversation.getId());
		if (id != null) {
			product = em.find(Product.class, id);
		}
	}

	public String save() {
		try {
			boolean isValidationOk = true;
			if ("belonga".equals(product.getName())) {
//				facesMessages.addFromResourceBundle("global.error", "You can't belonga me.");
				isValidationOk = false;
			}

			if (isValidationOk) {
				em.clear();
				em.merge(product);
				em.flush();

				return "/private/productList.xhtml";
			}
		} catch (Exception e) {
			log.error(e, e);
//			facesMessages.addFromResourceBundle(Severity.ERROR, "global.error", e);
		}
		return null;
	}

	public String delete() {
		em.remove(product);
		em.flush();

		return "/private/productList.xhtml";
	}

	public List<Product> getProductList() {
		return em.createQuery("from Product order by id").getResultList();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		System.out.println("--> getProduct called - cid:" + conversation.getId());
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
