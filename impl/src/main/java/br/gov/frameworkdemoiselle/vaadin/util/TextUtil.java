package br.gov.frameworkdemoiselle.vaadin.util;

public class TextUtil {

	public static String format(String texto, String formato, boolean direcao) {
		if (texto.length() > formato.length()) {
			throw new RuntimeException("Texto maior que o formato!");
		}
		String textoFormatado = "";
		int indiceFormato;
		int tamanho = texto.length() < formato.length() ? texto.length() : formato.length();
		if (direcao) {
			indiceFormato = 0;
			for (int indice = 0; indice < tamanho; indice++) {
				if (texto.charAt(indice) == formato.charAt(indiceFormato)) {
					textoFormatado = textoFormatado + texto.charAt(indice);
					indiceFormato++;
				} else if (formato.charAt(indiceFormato) == '9') {
					if ((texto.charAt(indice) >= '0' && texto.charAt(indice) <= '9')) {
						textoFormatado = textoFormatado + texto.charAt(indice);
						indiceFormato++;
					} else {
						throw new RuntimeException(
								"Texto não atende ao formato informado! Caracter literal quando deveria haver um numérico!");
					}
				} else if (formato.charAt(indiceFormato) == 'A') {
					if ((texto.charAt(indice) >= 'a' && texto.charAt(indice) <= 'z')
							|| (texto.charAt(indice) >= 'A' && texto.charAt(indice) <= 'Z')
							|| (texto.charAt(indice) >= '0' && texto.charAt(indice) <= '9')) {
						textoFormatado = textoFormatado + texto.charAt(indice);
						indiceFormato++;
					} else {
						throw new RuntimeException("Texto não atende ao formato informado! Caracter não alfanumérico!");
					}
				} else if (formato.charAt(indiceFormato) == 'D') {
					if ((texto.charAt(indice) == 'X') || (texto.charAt(indice) >= '0' && texto.charAt(indice) <= '9')) {
						textoFormatado = textoFormatado + texto.charAt(indice);
						indiceFormato++;
					} else {
						throw new RuntimeException(
								"Texto não atende ao formato informado! Caracter não atende à especificação de dígito verificador!");
					}
				} else if (formato.charAt(indiceFormato) == 'X') {
					textoFormatado = textoFormatado + texto.charAt(indice);
					indiceFormato++;
				} else {
					textoFormatado = textoFormatado + formato.charAt(indiceFormato);
					indiceFormato++;
					indice--;
				}
			}
		} else {
			indiceFormato = formato.length() - 1;
			for (int indice = texto.length() - 1; indice >= 0; indice--) {
				if (texto.charAt(indice) == formato.charAt(indiceFormato)) {
					textoFormatado = texto.charAt(indice) + textoFormatado;
					indiceFormato--;
				} else if (formato.charAt(indiceFormato) == '9') {
					if ((texto.charAt(indice) >= '0' && texto.charAt(indice) <= '9')) {
						textoFormatado = texto.charAt(indice) + textoFormatado;
						indiceFormato--;
					} else {
						throw new RuntimeException(
								"Texto não atende ao formato informado! Caracter literal quando deveria haver um numérico!");
					}
				} else if (formato.charAt(indiceFormato) == 'A') {
					if ((texto.charAt(indice) >= 'a' && texto.charAt(indice) <= 'z')
							|| (texto.charAt(indice) >= 'A' && texto.charAt(indice) <= 'Z')
							|| (texto.charAt(indice) >= '0' && texto.charAt(indice) <= '9')) {
						textoFormatado = texto.charAt(indice) + textoFormatado;
						indiceFormato--;
					} else {
						throw new RuntimeException("Texto não atende ao formato informado! Caracter não alfanumérico!");
					}
				} else if (formato.charAt(indiceFormato) == 'D') {
					if ((texto.charAt(indice) == 'X') || (texto.charAt(indice) >= '0' && texto.charAt(indice) <= '9')) {
						textoFormatado = texto.charAt(indice) + textoFormatado;
						indiceFormato--;
					} else {
						throw new RuntimeException(
								"Texto não atende ao formato informado! Caracter não atende à especificação de dígito verificador!");
					}
				} else if (formato.charAt(indiceFormato) == 'X') {
					textoFormatado = texto.charAt(indice) + textoFormatado;
					indiceFormato--;
				} else {
					textoFormatado = formato.charAt(indiceFormato) + textoFormatado;
					indiceFormato--;
					indice++;
				}
			}
		}
		return textoFormatado;
	}
}
