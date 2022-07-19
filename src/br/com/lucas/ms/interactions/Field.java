package br.com.lucas.ms.interactions;

import java.util.ArrayList;
import java.util.List;

public class Field {

	private final int row, col;

	private boolean opened, mined, ticked;

	private List<FieldObserver> observers = new ArrayList<>();
	private List<Field> closers = new ArrayList<>(); // Close fields

	public Field(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public void registerOBS(FieldObserver observer) {
		observers.add(observer);
	}

	public void notifyOBS(FieldEvent event) {
		observers.stream().forEach(o -> o.event(this, event));
	}

	public boolean addCloser(Field closer) {
		boolean rowD = row != closer.row;
		boolean colD = col != closer.col;
		boolean diagonal = rowD && colD;

		int deltaR = Math.abs(row - closer.row);
		int deltaC = Math.abs(col - closer.col);
		int delta = deltaR + deltaC;

		if (delta == 1 && !diagonal) {
			closers.add(closer);
			return true;
		} else if (delta == 2 && diagonal) {
			closers.add(closer);
			return true;
		} else {
			return false;
		}
	}

	public boolean safeClosers() {
		return closers.stream().noneMatch(c -> c.mined);
	}

	public void setTicked() {
		if (!opened) {
			ticked = !ticked;
		}
		if (ticked) {
			notifyOBS(FieldEvent.TICK);
		} else {
			notifyOBS(FieldEvent.UNTICK);
		}
	}

	public boolean getTicked() {
		return ticked;
	}

	public boolean open() {
		if (!opened && !ticked) {
			opened = true;

			if (mined) {
				notifyOBS(FieldEvent.EXPLODE);
			}

			setOpened(true);

			if (safeClosers()) {
				closers.forEach(c -> c.open());
			}
			return true;
		} else {
			return false;
		}
	}

	public void setOpened(boolean opened) {
		this.opened = opened;

		if (opened) {
			notifyOBS(FieldEvent.OPEN);
		}
	}

	public boolean getOpened() {
		return opened;
	}

	public void setMined() {
		mined = true;
	}

	public boolean getMined() {
		return mined;
	}

	public int closeMines() {
		return (int) closers.stream().filter(c -> c.mined).count();
	}
	
	public boolean endGame() {
		boolean shown = !ticked && opened;
		boolean protectd = mined && ticked;
		return shown || protectd;
	}

	public void reset() {
		opened = false;
		ticked = false;
		mined = false;
		notifyOBS(FieldEvent.RESET);
	}

}
