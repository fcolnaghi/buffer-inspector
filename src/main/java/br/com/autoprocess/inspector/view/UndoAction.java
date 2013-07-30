package br.com.autoprocess.inspector.view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoAction extends AbstractAction {

	private static final long serialVersionUID = 8258988300561395698L;

	public UndoAction(UndoManager manager) {
		this.manager = manager;
	}

	public void actionPerformed(ActionEvent evt) {
		try {
			manager.undo();
		} catch (CannotUndoException e) {
			// do nothing
		}
	}

	private UndoManager manager;
}
