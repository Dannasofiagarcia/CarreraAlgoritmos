package thread;

import application.CarrerasGUI;
import javafx.application.Platform;

public class CirculoThread extends Thread {

	private CarrerasGUI gui;

	private boolean active;

	public CirculoThread(CarrerasGUI gui) {
		this.gui = gui;
		active = true;

	}

	@Override
	public void run() {
		while (active) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					gui.circle();
				}
			});
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void desactivate() {
		active = false;
	}
}
