package ${package}.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.Startup;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import ${package}.domain.Bookmark;
import ${package}.domain.Category;
import ${package}.business.CategoryBC;
import ${package}.persistence.BookmarkDAO;

@BusinessController
public class BookmarkBC extends DelegateCrud<Bookmark, Long, BookmarkDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoryBC categoryBC;
	
	@Inject
	private MessageContext messageContext;
	
	@Startup
	@Transactional
	public void load() {
		Category categoryDemoiselle = new Category("Demoiselle"); 
		categoryBC.insert(categoryDemoiselle);
		if (findAll().isEmpty()) {
			insert(new Bookmark("Demoiselle Portal", "http://www.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Demoiselle SourceForge", "http://sf.net/projects/demoiselle", categoryDemoiselle));
			insert(new Bookmark("Twitter", "http://twitter.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Blog", "http://blog.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Wiki", "http://wiki.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Bug Tracking", "http://tracker.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Forum", "http://forum.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("SVN", "http://svn.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Maven", "http://repository.frameworkdemoiselle.gov.br", categoryDemoiselle));
			insert(new Bookmark("Downloads", "http://download.frameworkdemoiselle.gov.br", categoryDemoiselle));
		}
	}
	
	@Override
	public void insert(Bookmark bean) {
		
		// Dummy business logic.
		if (bean.getDescription() != null && bean.getDescription().indexOf("Vaadin") > -1) {
			throw new RuntimeException("'Vaadin' word is not allowed as description.");
		}
		
		super.insert(bean);
	}

}
