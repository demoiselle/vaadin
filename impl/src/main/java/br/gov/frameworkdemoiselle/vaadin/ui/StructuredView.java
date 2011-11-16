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

import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.vaadin.template.BaseVaadinView;
import br.gov.frameworkdemoiselle.vaadin.util.ViewNavigator;

import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

/**
 * Provides a simple view strucuture based on a "header", "menu", "content" and "footer".
 * 
 * @author CETEC
 */
public abstract class StructuredView extends BaseVaadinView {

	private static final long serialVersionUID = 1L;

	private VerticalLayout footer;

	private VerticalLayout header;

	private MenuBar menuBar;

	private VerticalLayout content;
 
	@Override
	public void init() {
		footer = new VerticalLayout();
		header = new VerticalLayout();
		content = new VerticalLayout();
		menuBar = new MenuBar();

		content.setHeight("600px");
		content.setWidth("100%");

		header.setHeight("80px");
		header.setWidth("100%");

		footer.setHeight("100px");
		footer.setWidth("100%");

		addComponent(header);
		addComponent(menuBar);
		addComponent(content);
		addComponent(footer);
		
		super.init();
	}

	public void setContentAsNavigable() {
		ViewNavigator navigator = Beans.getReference(ViewNavigator.class);
		navigator.setNavigable(content);
	}

	public VerticalLayout getFooter() {
		return footer;
	}

	public VerticalLayout getHeader() {
		return header;
	}

	public VerticalLayout getContent() {
		return content;
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

}
