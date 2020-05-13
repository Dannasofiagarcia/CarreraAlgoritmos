package thread;

import application.CarrerasGUI;
import javafx.application.Platform;

public class TiempoThreadCronometro extends Thread {

	private CarrerasGUI gui;

	private boolean active;

	public TiempoThreadCronometro(CarrerasGUI gui) {
		this.gui = gui;

		active = true;

	}

	@Override
	public void run() {
		while (active) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					gui.cronometro();
				}
			});
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void desactivate() {
		active = false;
	}

	public Boolean getActive() {
		return active;
	}
}
