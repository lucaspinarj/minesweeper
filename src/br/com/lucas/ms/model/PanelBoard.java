package br.com.lucas.ms.model;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.lucas.ms.interactions.Board;

@SuppressWarnings("serial")
public class PanelBoard extends JPanel {

	public PanelBoard(Board board) {
		setLayout(new GridLayout(board.getRows(), board.getCols()));

		board.forEach(f -> add(new PanelBtn(f)));
		board.registerOBS(e -> {

			SwingUtilities.invokeLater(() -> {
				if (e.getWin()) {
					JOptionPane.showMessageDialog(this, "You Win!!");
				} else {
					JOptionPane.showMessageDialog(this, "You Lose!!");
				}
				board.reset();
			});
		});
	}

}
