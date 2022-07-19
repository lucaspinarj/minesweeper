package br.com.lucas.ms.interactions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Board implements FieldObserver {

	private final int rows, cols, mines;

	private final List<Field> fields = new ArrayList<>();
	private final List<Consumer<ResultEvent>> observers = new ArrayList<>();

	public Board(int rows, int cols, int mines) {
		this.rows = rows;
		this.cols = cols;
		this.mines = mines;
		
		generateFields();
		generateClosers();
		randomMines();
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public void forEach(Consumer<Field> function) {
		fields.forEach(function);
	}

	public void registerOBS(Consumer<ResultEvent> observer) {
		observers.add(observer);
	}

	public void notifyOBS(boolean result) {
		observers.stream().forEach(o -> o.accept(new ResultEvent(result)));
	}

	public void open(int row, int col) {
		fields.parallelStream().filter(f -> f.getRow() == row && f.getCol() == col).findFirst()
				.ifPresent(f -> f.open());
	}

	public void tick(int row, int col) {
		fields.parallelStream().filter(f -> f.getRow() == row && f.getCol() == col).findFirst()
				.ifPresent(f -> f.setTicked());
	}

	private void generateFields() {
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				Field field = new Field(r, c);
				field.registerOBS(this);
				fields.add(field);
			}
		}
	}

	private void generateClosers() {
		for (Field f1 : fields) {
			for (Field f2 : fields) {
				f1.addCloser(f2);
			}
		}
	}

	private void randomMines() {
		long placedMines = 0;

		Predicate<Field> mined = f -> f.getMined();

		do {
			int random = (int) (Math.random() * fields.size());
			fields.get(random).setMined();
			placedMines = fields.stream().filter(mined).count();
		} while (placedMines < mines);
	}

	private void showMines() {
		fields.stream().filter(f -> f.getMined()).filter(f -> !f.getTicked()).forEach(f -> f.setOpened(true));
	}

	public boolean endGame() {
		return fields.stream().allMatch(f -> f.endGame());
	}

	public void reset() {
		fields.stream().forEach(f -> f.reset());
		randomMines();
	}

	public void event(Field field, FieldEvent event) {
		if (event == FieldEvent.EXPLODE) {
			showMines();
			notifyOBS(false);
		} else if (endGame()) {
			notifyOBS(true);
		}
	}
}
