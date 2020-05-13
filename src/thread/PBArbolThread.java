package thread;

import application.CarrerasGUI;
import javafx.application.Platform;

public class PBArbolThread extends Thread {

	private CarrerasGUI gui;
	private long cantidadA;

	private boolean active;

	public PBArbolThread(CarrerasGUI gui, long cantidadA) {
		this.gui = gui;
		this.cantidadA = cantidadA;
		active = true;

	}

	@Override
	public void run() {
		while (active) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					gui.progressBarArbol(cantidadA);
				}
			});
			try {
				sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void desactivate() {
		active = false;
	}
}
