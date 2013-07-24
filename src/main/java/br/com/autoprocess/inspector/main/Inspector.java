package br.com.autoprocess.inspector.main;

import java.awt.EventQueue;
import java.util.Locale;

import br.com.autoprocess.inspector.view.forms.FormMain;

public class Inspector {

	public static final String VERSAO = "13.6.0";
	public static final String APPLICATION_NAME = "Buffer Inspector - v" + VERSAO;
	public static final String INTERNAL_NAME = "buffer-inspector-"+ VERSAO;
	public static final int QTD_IDENTACOES = 3;
	
	/**
	 * Cria uma inst�ncia da janela principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// ---------------------------
		// Seta a aplica��o para o idioma ingl�s
		// ---------------------------
		Locale.setDefault(new Locale("en", "US"));
		
		// ---------------------------
		// Cria uma nova inst�ncia do form main
		// ---------------------------
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormMain frame = new FormMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

}
