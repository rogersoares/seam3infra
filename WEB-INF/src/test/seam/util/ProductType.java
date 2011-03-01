package test.seam.util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

@Named
public class ProductType {

	@Inject
	EntityManager em;

	public List<SelectItem> getTypes() {
		List<test.biz.ProductType> l = em.createQuery("from ProductType").getResultList();

		List<SelectItem> retList = new ArrayList<SelectItem>();
		SelectItem si = new SelectItem();
		si.setLabel("--");
		retList.add(si);
		for (test.biz.ProductType p : l) {
			si = new SelectItem();
			si.setLabel(p.getDescription());
			si.setValue(p.getId());
			retList.add(si);
		}

		return retList;
	}

}
