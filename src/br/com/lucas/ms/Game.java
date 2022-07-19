package br.com.lucas.ms;

import java.awt.Dimension;

import javax.swing.JFrame;

import br.com.lucas.ms.interactions.Board;
import br.com.lucas.ms.model.PanelBoard;

@SuppressWarnings("serial")
public class Game extends JFrame {
	
	private final Dimension medium = new Dimension(690, 438);

	Game() {
		
		Board board = new Board(16, 30, 50);
		add(new PanelBoard(board));

		setVisible(true);
		setTitle("MineSweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		setMaximumSize(medium);
		setMinimumSize(medium);

	}

	public static void main(String[] args) {
		new Game();
	}

}
