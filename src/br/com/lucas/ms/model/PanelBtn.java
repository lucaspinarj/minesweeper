package br.com.lucas.ms.model;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import br.com.lucas.ms.interactions.Field;
import br.com.lucas.ms.interactions.FieldEvent;
import br.com.lucas.ms.interactions.FieldObserver;

@SuppressWarnings("serial")
public class PanelBtn extends JButton implements FieldObserver, MouseListener {

	private final Color BG_DEF = new Color(184, 184, 184);
	private final Color BG_TICKED = new Color(8, 179, 247);
	private final Color BG_EXPLODED = new Color(189, 6, 68);
	private final Color TXT = new Color(0, 100, 0);

	private Field field;

	public PanelBtn(Field field) {
		this.field = field;

		setBackground(BG_DEF);
		setOpaque(true);
		setBorder(BorderFactory.createBevelBorder(0));

		addMouseListener(this);
		field.registerOBS(this);
	}

	public void event(Field field, FieldEvent event) {
		switch (event) {
		case OPEN:
			open();
			break;
		case TICK:
			tick();
			break;
		case EXPLODE:
			explode();
			break;
		default:
			def();
		}
		
		SwingUtilities.invokeLater(() -> {
			repaint();
			validate();
		});
	}

	private void def() {
		setBackground(BG_DEF);
		setBorder(BorderFactory.createBevelBorder(0));
		setText("");
	}

	private void explode() {
		setBackground(BG_EXPLODED);
		setForeground(Color.WHITE);
		setText("X");
	}

	private void tick() {
		setBackground(BG_TICKED);
		setForeground(Color.BLACK);
		setText("M");
	}

	private void open() {

		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		if (field.getMined()) {
			setBackground(BG_EXPLODED);
			return;
		}

		setBackground(BG_DEF);

		switch (field.closeMines()) {
		case 1:
			setForeground(TXT);
			break;
		case 2:
			setForeground(Color.BLUE);
			break;
		case 3:
			setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			setForeground(Color.RED);
			break;
		default:
			setForeground(Color.PINK);
		}
		String value = !field.safeClosers() ? field.closeMines() + "" : "";
		setText(value);
	}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			field.open();
		} else {
			if (!field.getOpened()) {
				field.setTicked();
			}
		}
	}

	// unused events
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
