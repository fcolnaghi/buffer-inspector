package autoprocess.inspector.view.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;
import javax.swing.plaf.TextUI;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * Sobreescreve o componente JTextArea, incrementando com a funcionalidade de
 * alterar os modos de inserção ou alteração (tecla insert)
 */
public class InspectorTextArea extends JTextArea {

	private static final long serialVersionUID = -3034518557599314758L;
	
	private boolean isOvertypeMode;

	private Caret defaultCaret;
	private Caret overtypeCaret;

	public InspectorTextArea() {
		super();
		initialize();
	}

	public InspectorTextArea(Document doc, String text, int rows, int columns) {
		super(doc, text, rows, columns);
		initialize();
	}

	public InspectorTextArea(Document doc) {
		super(doc);
		initialize();
	}

	public InspectorTextArea(int rows, int columns) {
		super(rows, columns);
		initialize();
	}

	public InspectorTextArea(String text, int rows, int columns) {
		super(text, rows, columns);
		initialize();
	}

	public InspectorTextArea(String text) {
		super(text);
		initialize();
	}
	
	/**
	 * Inicializa o componente com as opções default
	 */
	private void initialize() {
		
		// ---------------------------
		// Seta a cor do cursor
		// ---------------------------
		setCaretColor(Color.BLACK);
		
		// ---------------------------
		// Recupera o cursor padrão (linha vertical antes do caractere)
		// ---------------------------
		defaultCaret = getCaret();
		
		// ---------------------------
		// Cria um cursor customizado (linha horizontal abaixo do caractere)
		// ---------------------------
		overtypeCaret = new OvertypeCaret();
		overtypeCaret.setBlinkRate(defaultCaret.getBlinkRate());
		
		// ---------------------------
		// Seta o modo customizado como default
		// ---------------------------
		setOvertypeMode(false);
		
	}
	
	/**
	 * Alterna o tipo de cursor
	 */
	public void setOvertypeMode(boolean mode) {
		
		isOvertypeMode = mode;
		
		int pos = getCaretPosition();

		if (isOvertypeMode) {
			setCaret(overtypeCaret);
		} else {
			setCaret(defaultCaret);
		}

		setCaretPosition(pos);
	}

	/**
	 * Sobreescreve o método replaceSelection
	 */
	public void replaceSelection(String text) {

		// ---------------------------
		// Implementa o modo sobrescrever, selecionando o caractere na posição atual do cursor
		// ---------------------------
		if (isOvertypeMode) {
			int pos = getCaretPosition();

			if (getSelectedText() == null && pos < getDocument().getLength()) {
				moveCaretPosition(pos + 1);
			}
		}

		super.replaceSelection(text);
	}

	/**
	 * Sobreescreve o método processKeyEvent
	 */
	protected void processKeyEvent(KeyEvent e) {
		
		super.processKeyEvent(e);

		// ---------------------------
		// Captura o evento da tecla insert
		// ---------------------------
		if (e.getID() == KeyEvent.KEY_RELEASED && e.getKeyCode() == KeyEvent.VK_INSERT) {
			setOvertypeMode(!isOvertypeMode);
		}
	}

	/**
	 * Desenha uma linha horizontal logo abaixo do caractere atual, com a
	 * largura default da fonte e 1 pixel de altura
	 */
	class OvertypeCaret extends DefaultCaret {

		private static final long serialVersionUID = -5361810404680369411L;

		public void paint(Graphics g) {
			if (isVisible()) {
				try {
					JTextComponent component = getComponent();
					
					TextUI mapper = component.getUI();
					Rectangle r = mapper.modelToView(component, getDot());
					g.setColor(component.getCaretColor());
					int width = g.getFontMetrics().charWidth('w');
					int y = r.y + r.height - 2;
					g.drawLine(r.x, y, r.x + width - 2, y);
					
				} catch (BadLocationException e) {
				}
			}
		}

		/*
		 * Damage must be overridden whenever the paint method is overridden
		 * (The damaged area is the area the caret is painted in. We must
		 * consider the area for the default caret and this caret)
		 */
		protected synchronized void damage(Rectangle r) {
			
			if (r != null) {
				JTextComponent component = getComponent();
				x = r.x;
				y = r.y;
				width = component.getFontMetrics(component.getFont()).charWidth('w');
				height = r.height;
				repaint();
			}
			
		}
	}

}
