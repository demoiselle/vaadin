package ${package}.ui.view.form;

import ${package}.domain.Category;
import br.gov.frameworkdemoiselle.vaadin.ui.CrudForm;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;

public class CategoryForm extends CrudForm<Category> implements FormFieldFactory {

	private static final long serialVersionUID = 1L;

	public static final String F_DESCRIPTION = "description";

	public CategoryForm() {
		super(Category.class);
		setFormFieldFactory(this);
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		TextField field = null;
		if (F_DESCRIPTION.equals(propertyId)) {
			field = new TextField();
			field.setInputPrompt(bundle.getString("category.prompt.description"));
			field.setCaption(bundle.getString("category.label.description"));
			field.setNullRepresentation("");
		}
		return field;
	}

}
