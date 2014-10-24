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
package br.gov.frameworkdemoiselle.vaadin.util;

import br.gov.frameworkdemoiselle.util.Beans;

import com.github.wolfie.refresher.Refresher;

/**
 * AsyncTask enables proper and easy use of the UI thread. This class allows to perform background operations and
 * publish results on the UI thread without having to manipulate threads and/or handlers.
 *
 * @author CETEC
 */
public abstract class AsyncTask<Params, Result> extends Refresher {

	private static final long serialVersionUID = 1L;

	private Result result = null;

	private boolean executed = false;

	private boolean isExecuting = false;

	private Params[] params;

	public abstract Result doInBackground(Params... params);

	public void onPreExecute() {
	}

	public void onPostExecute(Result result) {
	}

	public void execute(Params... params) {
		if (!isExecuting) {
			this.params = params;

			onPreExecute();

			OwnRefreshListener listener = new OwnRefreshListener();
			addListener(listener);

			ViewNavigator navigator = Beans.getReference(ViewNavigator.class);
			navigator.getNavigable().addComponent(this);

			new Thread(new OwnRunnable()).start();
			isExecuting = true;

		} else {
			throw new RuntimeException("Task is already executing.");
		}
	}

	private class OwnRunnable implements Runnable {

		@Override
		public void run() {
			result = doInBackground(params);
			executed = true;
			isExecuting = false;
		}

	}

	private class OwnRefreshListener implements RefreshListener {

		private static final long serialVersionUID = 1L;

		@Override
		public void refresh(Refresher source) {
			if (executed) {
				onPostExecute(result);
				source.setEnabled(false);
			}
		}
	}
}
