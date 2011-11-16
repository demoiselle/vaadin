package ${package}.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import ${package}.domain.Bookmark;
import ${package}.persistence.BookmarkDAO;

@BusinessController
public class BookmarkBC extends DelegateCrud<Bookmark, Long, BookmarkDAO> {

	private static final long serialVersionUID = 1L;

	@Override
	public void insert(Bookmark bean) {
		
		// Dummy business logic.
		if (bean.getDescription() != null && bean.getDescription().indexOf("Vaadin") > -1) {
			throw new RuntimeException("'Vaadin' word is not allowed as description.");
		}
		
		super.insert(bean);
	}

}
