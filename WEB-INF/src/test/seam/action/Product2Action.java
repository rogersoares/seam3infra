package test.seam.action;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.logging.Logger;

import test.biz.Product;
import test.biz.ProductType;

@Named
@RequestScoped
public class Product2Action implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	EntityManager em;

//	@Inject
//	FacesMessages facesMessages;

	private Integer id;

	private Product product = new Product();

	private Integer productType;

	public String save() {
		try {
			boolean isValidationOk = true;
			if ("belonga".equals(product.getName())) {
//				facesMessages.addFromResourceBundle("global.error", "You can't belonga me.");
				isValidationOk = false;
			}

			if (isValidationOk) {
				if (productType == null) {
					product.setType(null);
				} else {
					ProductType type = new ProductType();
					type.setId(productType);
					product.setType(type);
				}
				em.clear();
				em.merge(product);
			//	em.flush();

				return "/private/productList.xhtml";
			}
		} catch (Exception e) {
			log.error(e, e);
//			facesMessages.addFromResourceBundle(Severity.ERROR, "global.error", e);
		}
		return null;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		if (id != null) {
			product = em.find(Product.class, id);
			productType = product.getType().getId();
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

}
