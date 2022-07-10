package br.com.lucas.ms.model;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Board extends JPanel {

	
	
	public Board () {
	
		Color c1 = new Color(47, 47, 47);
		setBackground(c1);
		
		setLayout(new GridLayout(10, 10));
		
	}
}
