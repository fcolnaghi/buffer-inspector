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

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class FormAbout extends JDialog {

	private static final long serialVersionUID = -5170522121679086680L;
	private JLabel labelName;
	private JLabel labelVersion;
	private JLabel labelUpdate;
	private JLabel labelFreeText;
	private JLabel labelInvite;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JLabel labelVisitUs;
	private JLabel jLabel1;
	private JPanel bottomPanel;
	private JLabel logo;

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
		setTitle("About "+ Inspector.APPLICATION_NAME);
		{
			logo = new JLabel();
			getContentPane().add(logo);
			logo.setBounds(32, 74, 177, 51);
			
			logo.setIcon(new ImageIcon(getClass().getResource("/logo-lateral-175x50.png")));
		}
		{
			labelName = new JLabel();
			getContentPane().add(labelName);
			labelName.setText(Inspector.APPLICATION_NAME);
			labelName.setBounds(255, 20, 425, 26);
			labelName.setFont(new java.awt.Font("Segoe UI",1,24));
			labelName.setForeground(new Color(71, 71, 71));
		}
		{
			labelVersion = new JLabel();
			getContentPane().add(labelVersion);
			labelVersion.setText(Inspector.VERSAO);
			labelVersion.setBounds(255, 52, 425, 16);
		}
		{
			labelUpdate = new JLabel();
			getContentPane().add(labelUpdate);
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
			
		}
		{
			labelFreeText = new JLabel();
			getContentPane().add(labelFreeText);
			
			labelFreeText.setText("<html>"+ Inspector.APPLICATION_NAME +" is designed by Autoprocess, working together with the community. This software is free, open and distributed under Apache 2.0 license. Enjoy!");
			
			labelFreeText.setBounds(255, 114, 425, 52);
			labelFreeText.setVerticalAlignment(SwingConstants.TOP);
		}
		{
			labelInvite = new JLabel();
			getContentPane().add(labelInvite);
			labelInvite.setText("Get involved!");
			labelInvite.setBounds(348, 172, 74, 16);
			
			labelInvite.setForeground(new Color(0, 128, 224));
			
			labelInvite.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			labelInvite.addMouseListener(new MouseAdapter() {
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
			
		}
		{
			bottomPanel = new JPanel();
			getContentPane().add(bottomPanel);
			bottomPanel.setBounds(0, 215, 694, 57);
			bottomPanel.setBackground(new java.awt.Color(227,227,227));
			bottomPanel.setLayout(null);
			{
				jLabel1 = new JLabel();
				bottomPanel.add(jLabel1);
				jLabel1.setText("Licensing information");
				jLabel1.setBounds(184, 11, 121, 16);
				jLabel1.setForeground(new Color(0, 128, 224));
				jLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				jLabel1.addMouseListener(new MouseAdapter() {
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
				
			}
			{
				labelVisitUs = new JLabel();
				bottomPanel.add(labelVisitUs);
				labelVisitUs.setText("Visit out website");
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
				
			}
			{
				jLabel3 = new JLabel();
				bottomPanel.add(jLabel3);
				jLabel3.setText("Autoprocess e logotipo Autoprocess são marcas comerciais da Autoprocess Automação de Processos.");
				jLabel3.setBounds(0, 39, 694, 16);
				jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
				
				jLabel3.setFont(new java.awt.Font("Segoe UI",0,10));
				jLabel3.setForeground(new Color(153, 153, 153));
			}
		}
		{
			jLabel4 = new JLabel();
			getContentPane().add(jLabel4);
			jLabel4.setText("Sound interesting?");

			jLabel4.setBounds(255, 172, 100, 16);
		}

	}
	
}
