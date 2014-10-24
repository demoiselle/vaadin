/*
 * Demoiselle Framework Copyright (C) 2010 SERPRO
 * ---------------------------------------------------------------------------- This file is part of Demoiselle
 * Framework. Demoiselle Framework is free software; you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License version 3 as published by the Free Software Foundation. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU Lesser General Public License version 3 along with this program; if not, see
 * <http://www.gnu.org/licenses/> or write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA 02110-1301, USA. ---------------------------------------------------------------------------- Este arquivo
 * é parte do Framework Demoiselle. O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação do Software Livre (FSF). Este
 * programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA GARANTIA; sem uma garantia implícita de
 * ADEQUAÇÃO a qualquer MERCADO ou APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português para
 * maiores detalhes. Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título "LICENCA.txt", junto com esse
 * programa. Se não, acesse <http://www.gnu.org/licenses/> ou escreva para a Fundação do Software Livre (FSF) Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package br.gov.frameworkdemoiselle.vaadin.util;

import java.io.File;
import java.util.ResourceBundle;

import org.vaadin.easyuploads.FileFactory;
import org.vaadin.easyuploads.UploadField;
import org.vaadin.easyuploads.UploadField.FieldType;
import org.vaadin.easyuploads.UploadField.StorageMode;

import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.vaadin.configuration.VaadinConfig;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.RichTextArea;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

/**
 * Helper class that provides basic Vaadin field creation.
 *
 * @author Marlon
 */
public abstract class FieldFactory {

	/**
	 * Produces basic textfields.
	 *
	 * @param inputPrompt
	 *            Prompt message.
	 * @param caption
	 *            Label.
	 * @return Produced field.
	 */
	public static TextField createTextField(String inputPrompt, String caption) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);

		TextField field = new TextField();
		field.setNullRepresentation("");
		setBasicProperties(field, caption);

		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
		} else {
			field.setInputPrompt(inputPrompt);
		}

		return field;
	}

	/**
	 * Produces a RichText field.
	 *
	 * @param caption
	 *            Field's caption.
	 * @return Produced field.
	 */
	public static RichTextArea createRichText(String caption) {
		RichTextArea field = new RichTextArea();
		field.setNullRepresentation("");
		setBasicProperties(field, caption);
		return field;
	}

	/**
	 * Produces a TextArea field.
	 *
	 * @param inputPrompt
	 *            Field's prompt message.
	 * @param caption
	 *            Field's caption.
	 * @param rows
	 *            How much rows.
	 * @param columns
	 *            How much columns.
	 * @return Produced field.
	 */
	public static TextArea createTextArea(String inputPrompt, String caption, int rows, int columns) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);

		TextArea field = new TextArea();
		field.setNullRepresentation("");
		field.setRows(rows);
		field.setColumns(columns);
		setBasicProperties(field, caption);

		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
		} else {
			field.setInputPrompt(inputPrompt);
		}

		return field;
	}

	/**
	 * Produces an OptionGroup.
	 *
	 * @param caption
	 *            Field's caption.
	 * @param itemCaptionPropertyId
	 *            Object's property used as label.
	 * @return Produced field.
	 */
	public static OptionGroup createOptionGroup(String caption, String itemCaptionPropertyId) {
		OptionGroup field = new OptionGroup();
		field.setItemCaptionPropertyId(itemCaptionPropertyId);
		setBasicProperties(field, caption);
		return field;
	}

	/**
	 * Produces a ComboBox.
	 *
	 * @param inputPrompt
	 *            Field's prompt message.
	 * @param caption
	 *            Field's caption.
	 * @param itemCaptionPropertyId
	 *            Object's property used as label.
	 * @return Produced field.
	 */
	public static ComboBox createComboBox(String inputPrompt, String caption, String itemCaptionPropertyId) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);

		ComboBox field = new ComboBox();
		field.setItemCaptionPropertyId(itemCaptionPropertyId);
		setBasicProperties(field, caption);

		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
		} else {
			field.setInputPrompt(inputPrompt);
		}

		return field;
	}

	/**
	 * Produces a PopupDateField.
	 *
	 * @param inputPrompt
	 *            Field's prompt message.
	 * @param caption
	 *            Field's caption.
	 * @param format
	 *            Date format.
	 * @return Produced field.
	 */
	public static PopupDateField createDateField(String inputPrompt, String caption, String format) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);

		PopupDateField field = new PopupDateField();
		setBasicProperties(field, caption);
		field.setDateFormat(format);

		if (Strings.isResourceBundleKeyFormat(inputPrompt)) {
			field.setInputPrompt(bundle.getString(Strings.removeBraces(inputPrompt)));
		} else {
			field.setInputPrompt(inputPrompt);
		}

		return field;
	}

	/**
	 * Produces a CheckBox.
	 *
	 * @param inputPrompt
	 *            Field's prompt.
	 * @param caption
	 *            Field's caption.
	 * @return Produced field.
	 */
	public static CheckBox createCheckBox(String inputPrompt, String caption) {
		CheckBox field = new CheckBox();
		setBasicProperties(field, caption);
		return field;
	}

	private static void setBasicProperties(Field field, String caption) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		if (Strings.isResourceBundleKeyFormat(caption)) {
			field.setCaption(bundle.getString(Strings.removeBraces(caption)));
		} else {
			field.setCaption(caption);
		}
	}

	private static String resolveProperties(final String message) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		String result = message;
		if (Strings.isResourceBundleKeyFormat(message)) {
			result = bundle.getString(Strings.removeBraces(message));
		}
		return result;
	}

	public static UploadField createFileField(String caption, String buttonCaption) {
		UploadField field = new UploadField();
		field.setFieldType(FieldType.BYTE_ARRAY);
		field.setCaption(resolveProperties(caption));
		field.setButtonCaption(resolveProperties(buttonCaption));
		field.setStorageMode(StorageMode.MEMORY);
		field.setFileFactory(new FileFactory() {

			VaadinConfig config = Beans.getReference(VaadinConfig.class);

			@Override
			public File createFile(String fileName, String mimeType) {
				File f = new File(config.getUploadPath() + fileName);
				return f;
			}
		});

		return field;
	}

	public static Field createPasswordField(String prompt, String caption) {
		PasswordField field = new PasswordField();
		field.setCaption(resolveProperties(caption));
		field.setInputPrompt(resolveProperties(prompt));
		return field;
	}

	@SuppressWarnings("serial")
	public static Field createCPFField(String prompt, String caption) {
		final TextField listenedField = createTextField(prompt, caption);
		listenedField.setImmediate(true);
		listenedField.addListener(new ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if (listenedField.getValue() != null) {
					try {
						listenedField.setValue(TextUtil.format(listenedField.getValue().toString(), "999.999.999-99",
								true));
					} catch (RuntimeException re) {
						// Notthing to be done!
					}
				}
			}
		});
		return listenedField;
	}

	public static Field createPhoneField(String prompt, String caption) {
		final TextField listenedField = createTextField(prompt, caption);
		listenedField.setImmediate(true);
		listenedField.addListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if (listenedField.getValue() != null) {
					try {
						listenedField.setValue(TextUtil.format(listenedField.getValue().toString(), "9999-9999", true));
					} catch (RuntimeException re) {
						// Notthing to be done!
					}
				}
			}
		});
		return listenedField;
	}

	@SuppressWarnings("serial")
	public static Field createFormattedField(String prompt, String caption, final String formato, final boolean direcao) {
		final TextField listenedField = createTextField(prompt, caption);
		listenedField.setImmediate(true);
		listenedField.addListener(new ValueChangeListener() {

			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				if (listenedField.getValue() != null) {
					try {
						listenedField.setValue(TextUtil.format(listenedField.getValue().toString(), formato, direcao));
					} catch (RuntimeException re) {
						// Notthing to be done!
					}
				}
			}

		});
		return listenedField;
	}

}
