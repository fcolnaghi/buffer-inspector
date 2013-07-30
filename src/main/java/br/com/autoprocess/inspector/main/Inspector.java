package br.com.autoprocess.inspector.main;

import java.awt.EventQueue;
import java.util.Locale;

import javax.swing.UIManager;

import br.com.autoprocess.inspector.view.FormMain;

public class Inspector {

	public static final String VERSAO = "13.6.0";
	public static final String APPLICATION_NAME = "Buffer Inspector";
	public static final String INTERNAL_NAME = "buffer-inspector-"+ VERSAO;
	public static final int QTD_IDENTACOES = 3;
	
	/**
	 * Cria uma instancia da janela principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// ---------------------------
		// Ao inicializar apresenta a versao
		// ---------------------------
		System.out.println(Inspector.INTERNAL_NAME + " has started...");
		
		// ---------------------------
		// Seta a aplicacao para o idioma ingles
		// ---------------------------
		Locale.setDefault(new Locale("en", "US"));
		
		
		// ---------------------------
		// Look and Feel
		// ---------------------------
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// ---------------------------
		// Cria uma nova instancia do form main
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
