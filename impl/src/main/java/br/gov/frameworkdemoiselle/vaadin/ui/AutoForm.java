/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 *
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 *
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 *
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 *
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.vaadin.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.gov.frameworkdemoiselle.vaadin.annotation.CheckBox;
import br.gov.frameworkdemoiselle.vaadin.annotation.ComboBox;
import br.gov.frameworkdemoiselle.vaadin.annotation.CpfField;
import br.gov.frameworkdemoiselle.vaadin.annotation.DateField;
import br.gov.frameworkdemoiselle.vaadin.annotation.FormattedField;
import br.gov.frameworkdemoiselle.vaadin.annotation.OptionGroup;
import br.gov.frameworkdemoiselle.vaadin.annotation.PasswordField;
import br.gov.frameworkdemoiselle.vaadin.annotation.PhoneField;
import br.gov.frameworkdemoiselle.vaadin.annotation.RichText;
import br.gov.frameworkdemoiselle.vaadin.annotation.TextArea;
import br.gov.frameworkdemoiselle.vaadin.annotation.TextField;
import br.gov.frameworkdemoiselle.vaadin.util.FieldFactory;

import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;

/**
 * Form's of this type are capable to produce fields using specific annotations.
 *
 * @author Marlon
 * @param <E>
 *            Bean's type to which this form belongs to.
 */
public class AutoForm<E> extends BeanValidationForm<E> implements FormFieldFactory {

	private static final long serialVersionUID = 1L;

	private Map<String, Field> fields = new HashMap<String, Field>();

	/**
	 * Default constructor.
	 *
	 * @param beanClass
	 *            The bean's class.
	 */
	public AutoForm(Class<E> beanClass) {
		super(beanClass);
		setFormFieldFactory(this);
	}

	/*
	 * (non-Javadoc)
	 * @see com.vaadin.ui.FormFieldFactory#createField(com.vaadin.data.Item, java.lang.Object, com.vaadin.ui.Component)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = null;

		if (fields.containsKey(propertyId)) {
			return fields.get(propertyId);
		}

		String caption = "";
		String prompt = "";

		if (item instanceof BeanItem<?>) {
			Class<E> clazz = (Class<E>) ((BeanItem<E>) item).getBean().getClass();
			java.lang.reflect.Field objectField = getField((String) propertyId, clazz);

			if (objectField.isAnnotationPresent(br.gov.frameworkdemoiselle.vaadin.annotation.Field.class)) {
				caption = objectField.getAnnotation(br.gov.frameworkdemoiselle.vaadin.annotation.Field.class).label();
				prompt = objectField.getAnnotation(br.gov.frameworkdemoiselle.vaadin.annotation.Field.class).prompt();

				field = findFieldByAnnotation(objectField, prompt, caption);
				field = field == null ? findFieldByType(objectField, prompt, caption) : field;

				if (field == null) {
					// TODO Lançar um warning indicando o uso do campo de texto, pq não foi encontrado uma
					// anotação, é não é um tipo primitivo tratado.
					field = FieldFactory.createTextField(prompt, caption);

					// throw new RuntimeException("Can't get the field type.");
				}

				fields.put((String) propertyId, field);
			} else {
				fields.put((String) propertyId, null);
			}

			return field;
		} else {
			throw new RuntimeException("AutomaticForm can only be used with BeanItem.");
		}
	}

	/**
	 * Verifies if the field exists in the object. If exists, return it. If not, throws an exception.
	 *
	 * @param propertyId
	 *            Field to be found.
	 * @param clazz
	 *            Class
	 * @return Found field.
	 */
	private java.lang.reflect.Field getField(String propertyId, Class<?> clazz) {
		try {
			return clazz.getDeclaredField(propertyId);
		} catch (SecurityException e) {
			throw new RuntimeException("AutomaticForm error: Can't access field " + propertyId + " in class "
					+ clazz.getName(), e);
		} catch (NoSuchFieldException e) {
			if (clazz.getSuperclass() != null) {
				return getField(propertyId, clazz.getSuperclass());
			}
			throw new RuntimeException("AutomaticForm error: Can't find field " + propertyId + " in class "
					+ clazz.getName(), e);
		}
	}

	/**
	 * Attempts to deduce the field's type (Vaadin field) via the class of the attribute.
	 *
	 * @param objectField
	 *            Object which have the attribute
	 * @param prompt
	 *            Prompt
	 * @param caption
	 *            Caption
	 * @return Deduced field or Null if not deduced.
	 */
	private Field findFieldByType(java.lang.reflect.Field objectField, String prompt, String caption) {
		Field field = null;

		// Check primitive wrapper classes.
		if (String.class.equals(objectField.getType()) || Long.class.equals(objectField.getType())
				|| Integer.class.equals(objectField.getType()) || Double.class.equals(objectField.getType())
				|| Short.class.equals(objectField.getType()) || Float.class.equals(objectField.getType())) {
			field = FieldFactory.createTextField(prompt, caption);
		}

		// Check primitive classes.
		if (long.class.equals(objectField.getType()) || int.class.equals(objectField.getType())
				|| double.class.equals(objectField.getType()) || short.class.equals(objectField.getType())
				|| float.class.equals(objectField.getType())) {
			field = FieldFactory.createTextField(prompt, caption);
		}
		if (Date.class.isInstance(objectField.getType())) {
			field = FieldFactory.createDateField(prompt, caption, "MM/dd/yyyy");
		}
		if (Boolean.class.isInstance(objectField.getType())) {
			field = FieldFactory.createCheckBox(prompt, caption);
		}
		return field;
	}

	/**
	 * Attempts to deduce the field's type (Vaadin field) via annotations.
	 *
	 * @param objectField
	 *            Object which have the field.
	 * @param prompt
	 *            Prompt.
	 * @param caption
	 *            Caption.
	 * @return Deduced Field.
	 */
	private Field findFieldByAnnotation(java.lang.reflect.Field objectField, String prompt, String caption) {
		Field field = null;

		if (objectField.isAnnotationPresent(TextArea.class)) {
			final int rows = objectField.getAnnotation(TextArea.class).rows();
			final int columns = objectField.getAnnotation(TextArea.class).columns();
			field = FieldFactory.createTextArea(prompt, caption, rows, columns);
		} else if (objectField.isAnnotationPresent(RichText.class)) {
			field = FieldFactory.createRichText(caption);
		} else if (objectField.isAnnotationPresent(TextField.class)) {
			field = FieldFactory.createTextField(prompt, caption);
		} else if (objectField.isAnnotationPresent(PasswordField.class)) {
			field = FieldFactory.createPasswordField(prompt, caption);
		} else if (objectField.isAnnotationPresent(OptionGroup.class)) {
			String fieldLabel = objectField.getAnnotation(OptionGroup.class).fieldLabel();
			field = FieldFactory.createOptionGroup(caption, fieldLabel);
		} else if (objectField.isAnnotationPresent(ComboBox.class)) {
			String fieldLabel = objectField.getAnnotation(ComboBox.class).fieldLabel();
			field = FieldFactory.createComboBox(prompt, caption, fieldLabel);
		} else if (objectField.isAnnotationPresent(DateField.class)) {
			String format = objectField.getAnnotation(DateField.class).format();
			field = FieldFactory.createDateField(prompt, caption, format);
		} else if (objectField.isAnnotationPresent(CheckBox.class)) {
			field = FieldFactory.createCheckBox(prompt, caption);
		} else if (objectField.isAnnotationPresent(CpfField.class)) {
			field = FieldFactory.createCPFField(prompt, caption);
		} else if (objectField.isAnnotationPresent(PhoneField.class)) {
			field = FieldFactory.createPhoneField(prompt, caption);
		} else if (objectField.isAnnotationPresent(FormattedField.class)) {
			final String formato = objectField.getAnnotation(FormattedField.class).format();
			final boolean direcao = objectField.getAnnotation(FormattedField.class).straight();
			field = FieldFactory.createFormattedField(prompt, caption, formato, direcao);
		}
		return field;
	}

	@Override
	public void addField(Object propertyId, Field field) {
		super.addField(propertyId, field);
		if (propertyId instanceof String) {
			fields.put((String) propertyId, field);
		}
	}

}
