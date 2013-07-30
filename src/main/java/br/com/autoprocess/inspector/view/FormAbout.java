package br.com.autoprocess.inspector.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import br.com.autoprocess.inspector.main.Inspector;

public class FormAbout extends JDialog {

	private static final long serialVersionUID = -5170522121679086680L;
	
	public FormAbout() {

		// ---------------------------
		// Propriedades
		// ---------------------------
		setSize(500, 300); // tamanho da tela
		setLocationRelativeTo(null); // centralizado na tela
		setModal(true);
		setResizable(false);
		setTitle("About...");
		
		// ---------------------------
		// Logo
		// ---------------------------
		JLabel logo = new JLabel();
		logo.setToolTipText("Visit our website");
		logo.setIcon(new ImageIcon(getClass().getResource("/logo-lateral-175x50.png")));
		
		logo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					try {
						Desktop.getDesktop().browse(new URI("http://www.autoprocess.com.br/?f="+ Inspector.INTERNAL_NAME));
					} catch (Exception ex) {
						// nothing to do
					}
				}
			}
		});
		
		add(logo, BorderLayout.CENTER);
		
		
	}
	
}
