package br.com.autoprocess.inspector.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import br.com.autoprocess.inspector.main.Inspector;

public class FormAbout extends JDialog {

	private static final long serialVersionUID = -5170522121679086680L;
	private JLabel labelName;
	private JLabel labelVersion;
	private JLabel labelUpdate;
	private JLabel labelFreeText;
	private JLabel labelInviteLink;
	private JLabel labelInvite;
	private JLabel labelCopy;
	private JLabel labelVisitUs;
	private JLabel labelLicensing;
	private JPanel bottomPanel;
	private JLabel labelLogo;

	public FormAbout() {

		// ---------------------------
		// Propriedades
		// ---------------------------
		this.setSize(700, 300); // tamanho da tela
		setLocationRelativeTo(null); // centralizado na tela
		setModal(true);
		setResizable(false);
		setBackground(new Color(250, 250, 250));

		getContentPane().setLayout(null); // design livre
		setTitle("About " + Inspector.APPLICATION_NAME);

		// ---------------------------
		// Logo
		// ---------------------------
		labelLogo = new JLabel();
		getContentPane().add(labelLogo);
		labelLogo.setBounds(32, 74, 177, 51);

		labelLogo.setIcon(new ImageIcon(getClass().getResource("/logo-lateral-175x50.png")));
		
		// ---------------------------
		// Info
		// ---------------------------
		
		// name
		labelName = new JLabel();
		labelName.setText(Inspector.APPLICATION_NAME);
		labelName.setBounds(255, 20, 425, 26);
		labelName.setFont(new java.awt.Font("Segoe UI", 1, 24));
		labelName.setForeground(new Color(71, 71, 71));
		getContentPane().add(labelName);
		
		// version
		labelVersion = new JLabel();
		labelVersion.setText(Inspector.VERSAO);
		labelVersion.setBounds(255, 52, 425, 16);
		getContentPane().add(labelVersion);
		
		// update
		labelUpdate = new JLabel();
		labelUpdate.setText("Check for updates");
		labelUpdate.setBounds(255, 83, 100, 16);
		labelUpdate.setForeground(new Color(0, 128, 224));
		labelUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelUpdate.addMouseListener(new MouseAdapter() {
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
		getContentPane().add(labelUpdate);
		
		// free text
		labelFreeText = new JLabel();
		labelFreeText.setText("<html>"+ Inspector.APPLICATION_NAME + " is designed by Autoprocess, working together with the community. This software is free, open and distributed under Apache 2.0 license. Enjoy!");
		labelFreeText.setBounds(255, 114, 425, 52);
		labelFreeText.setVerticalAlignment(SwingConstants.TOP);
		getContentPane().add(labelFreeText);
		
		// invite
		labelInvite = new JLabel();
		labelInvite.setText("Sound interesting?");
		labelInvite.setBounds(255, 172, 100, 16);
		getContentPane().add(labelInvite);
		
		labelInviteLink = new JLabel();
		labelInviteLink.setText("Get involved!");
		labelInviteLink.setBounds(348, 172, 74, 16);
		labelInviteLink.setForeground(new Color(0, 128, 224));
		labelInviteLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelInviteLink.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					try {
						Desktop.getDesktop().browse(new URI("https://github.com/autoprocess/buffer-inspector"));
					} catch (Exception ex) {
						// nothing to do
					}
				}
			}
		});
		getContentPane().add(labelInviteLink);
		
		// ---------------------------
		// Footer
		// ---------------------------
		bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 215, 694, 57);
		bottomPanel.setBackground(new java.awt.Color(227, 227, 227));
		bottomPanel.setLayout(null);
		getContentPane().add(bottomPanel);

		// licensing
		labelLicensing = new JLabel();
		labelLicensing.setText("Licensing information");
		labelLicensing.setBounds(184, 11, 121, 16);
		labelLicensing.setForeground(new Color(0, 128, 224));
		labelLicensing.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelLicensing.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					try {
						Desktop.getDesktop().browse(new URI("http://www.apache.org/licenses/LICENSE-2.0.html"));
					} catch (Exception ex) {
						// nothing to do
					}
				}
			}
		});
		bottomPanel.add(labelLicensing);

		// visit us
		labelVisitUs = new JLabel();
		labelVisitUs.setText("Visit our website");
		labelVisitUs.setBounds(395, 11, 89, 16);

		labelVisitUs.setForeground(new Color(0, 128, 224));
		labelVisitUs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		labelVisitUs.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() > 0) {
					try {
						Desktop.getDesktop().browse(new URI("http://www.autoprocess.com.br"));
					} catch (Exception ex) {
						// nothing to do
					}
				}
			}
		});
		bottomPanel.add(labelVisitUs);

		// Copy
		labelCopy = new JLabel();
		labelCopy.setText("Autoprocess e logotipo Autoprocess são marcas comerciais da Autoprocess Automação de Processos.");
		labelCopy.setBounds(0, 39, 694, 16);
		labelCopy.setHorizontalAlignment(SwingConstants.CENTER);
		labelCopy.setFont(new java.awt.Font("Segoe UI", 0, 10));
		labelCopy.setForeground(new Color(153, 153, 153));
		bottomPanel.add(labelCopy);

	}

}
