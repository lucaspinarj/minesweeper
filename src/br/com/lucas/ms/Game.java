package br.com.lucas.ms;

import java.awt.Dimension;

import javax.swing.JFrame;

import br.com.lucas.ms.model.Board;
import br.com.lucas.ms.model.Field;

@SuppressWarnings("serial")
public class Game extends JFrame {

	Game() {

		Dimension medium = new Dimension(648, 400);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setMaximumSize(medium);
		setMinimumSize(medium);
		
		Board board = new Board();
		Field field = new Field();
		
		add(board);
		add(field);

	}

	public static void main(String[] args) {
		new Game();
	}

}
