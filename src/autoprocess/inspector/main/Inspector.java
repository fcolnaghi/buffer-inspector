/**
 * DIREITOS AUTORAIS. AUTOPROCESS AUTOMA��O DE PROCESSOS LTDA.
 * 
 * Este software � destinado apenas aos fins para os quais foi fornecido.
 * Nenhuma parte do mesmo pode ser reproduzida, desmembrada, transmitida, armazenada em sistemas recuper�vel,
 * nem traduzida em qualquer linguagem humana ou de inform�tica, de nenhuma forma ou para qualquer outra finalidade
 * al�m daquele para o qual foi dada autoriza��o pr�via por AUTOPROCESS AUTOMA��O DE PROCESSOSLTDA
 * 
 * TODOS OS DIREITOS RESERVADOS.
 */
package autoprocess.inspector.main;

import java.awt.EventQueue;
import java.util.Locale;

import autoprocess.inspector.view.forms.FormMain;

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
