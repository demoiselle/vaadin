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

import javax.enterprise.util.AnnotationLiteral;

import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.ResourceBundle;
import br.gov.frameworkdemoiselle.util.Strings;
import br.gov.frameworkdemoiselle.vaadin.annotation.Field;
import br.gov.frameworkdemoiselle.vaadin.event.ProcessItemSelection;

import com.vaadin.data.Container;
import com.vaadin.ui.Table;

/**
 * Simple table component which extends ${com.vaadin.ui.Table} adding some specific behavior to fire events when the
 * user selects some line in the table.
 * 
 * @author CETEC
 */
public class AutoTable extends Table {

	private static final long serialVersionUID = 1L;

	private Class<?> beanClass;

	private String[] columnOrder = null;

	public AutoTable(Class<?> beanClass) {
		super();

		this.beanClass = beanClass;

		setImmediate(true);
		setSelectable(true);

		addListener(new ValueChangeListener() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("serial")
			@Override
			public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
				Object item = event.getProperty().getValue();
				if (item != null) {
					Beans.getBeanManager().fireEvent(item, new AnnotationLiteral<ProcessItemSelection>() {
					});
				}
			}

		});

	}

	/**
	 * Adding the order behaviour
	 */
	@Override
	public void setContainerDataSource(Container newDataSource) {
		super.setContainerDataSource(newDataSource);
		if (!newDataSource.getItemIds().isEmpty()) {
			if (columnOrder != null && columnOrder.length > 0) {
				if (getVisibleColumns().length > 0)
					setVisibleColumns(columnOrder);
			}
		}
	}

	/**
	 * If this is informed, always an setContainerDataSource is called the setVisibleColumns is called with the
	 * colunOrder as parameter.
	 * 
	 * @param properties
	 */
	public void setColumnOrder(String[] properties) {
		this.columnOrder = properties;
	}

	@Override
	public String getColumnHeader(Object propertyId) {
		try {
			Field field = beanClass.getDeclaredField((String) propertyId).getAnnotation(Field.class);
			if (field != null && !"".equals(field.label())) {
				return resolveProperties(field.label());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return super.getColumnHeader(propertyId);
	}

	private static String resolveProperties(final String message) {
		ResourceBundle bundle = Beans.getReference(ResourceBundle.class);
		String result = message;
		if (Strings.isResourceBundleKeyFormat(message)) {
			result = bundle.getString(Strings.removeBraces(message));
		}
		return result;
	}

}
