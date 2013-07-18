/**
 * DIREITOS AUTORAIS. AUTOPROCESS AUTOMAÇÃO DE PROCESSOS LTDA.
 * 
 * Este software é destinado apenas aos fins para os quais foi fornecido.
 * Nenhuma parte do mesmo pode ser reproduzida, desmembrada, transmitida, armazenada em sistemas recuperável,
 * nem traduzida em qualquer linguagem humana ou de informática, de nenhuma forma ou para qualquer outra finalidade
 * além daquele para o qual foi dada autorização prévia por AUTOPROCESS AUTOMAÇÃO DE PROCESSOSLTDA
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
	 * Cria uma instância da janela principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// ---------------------------
		// Seta a aplicação para o idioma inglês
		// ---------------------------
		Locale.setDefault(new Locale("en", "US"));
		
		// ---------------------------
		// Cria uma nova instância do form main
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
