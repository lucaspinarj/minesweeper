package br.com.lucas.ms.interactions;

public class ResultEvent {

	private final boolean win;
	
	public ResultEvent(boolean win) {
		this.win = win;
	}
	
	public boolean getWin() {
		return win;
	}
}
