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
package br.gov.frameworkdemoiselle.vaadin.template;

import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.vaadin.event.AfterViewInitialization;
import br.gov.frameworkdemoiselle.vaadin.event.AfterViewRepainted;

import com.vaadin.ui.VerticalLayout;

/**
 * Vaadin ${link View} implementation.
 * 
 * @author CETEC
 */
public abstract class BaseVaadinView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanManager beanManager;

	public BaseVaadinView() {
		setWidth("100%");
		addListener(new RepaintRequestListener() {

			private static final long serialVersionUID = 1L;

			@SuppressWarnings("serial")
			@Override
			public void repaintRequested(RepaintRequestEvent event) {
				beanManager.fireEvent(BaseVaadinView.this, new AnnotationLiteral<AfterViewRepainted>() {});
			}

		});

	}

	@SuppressWarnings("serial")
	public void init() {
		this.initializeComponents();
		beanManager.fireEvent(this, new AnnotationLiteral<AfterViewInitialization>() {
		});
	}

	/**
	 * Message that appears when the user switches between views.
	 * 
	 * @return Message.
	 */
	public String getWarningMessage() {
		return null;
	}

}
