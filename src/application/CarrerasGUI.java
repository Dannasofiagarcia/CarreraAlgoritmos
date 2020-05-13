package application;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.AlgoritmoArbol;
import model.AlgoritmoArrayList;
import model.AlgoritmoLista;
import model.OrganizadorCarrera;
import thread.CirculoThread;
import thread.PBArbolThread;
import thread.PBArrayThread;
import thread.PBListaThread;
import thread.TiempoThreadArbol;
import thread.TiempoThreadArrayList;
import thread.TiempoThreadCronometro;
import thread.TiempoThreadLista;

public class CarrerasGUI {

	private OrganizadorCarrera organizador;

	// *****************************************************
	// ELEMENTOS INTERFAZ
	// *****************************************************

	@FXML
	private Pane pane;

	@FXML
	private Circle circuloPequeno;

	@FXML
	private Circle circuloGrande;

	@FXML
	private Label clockArrayList;

	@FXML
	private Label clockArbol;

	@FXML
	private Label clockLista;

	@FXML
	private Label clockCronometro;

	@FXML
	private TextField cantidadAlgoritmos;

	@FXML
	private RadioButton add;

	@FXML
	private RadioButton delete;

	@FXML
	private RadioButton search;

	@FXML
	private RadioButton iterative;

	@FXML
	private RadioButton recursive;

	@FXML
	private ProgressBar pbListas;

	@FXML
	private ProgressBar pbArbol;

	@FXML
	private ProgressBar pbArray;

	// *****************************************************
	// ATRIBUTOS
	// *****************************************************

	private int horaCronometro = 0;
	private int minutoCronometro = 0;
	private int segundoCronometro = 0;
	private int horaArrayList = 0;
	private int minutoArrayList = 0;
	private int segundoArrayList = 0;
	private int horaArbol = 0;
	private int minutoArbol = 0;
	private int segundoArbol = 0;
	private int horaLista = 0;
	private int minutoLista = 0;
	private int segundoLista = 0;
	private double min = 14;
	private double max = 36;
	private int contadorArbol = 0;
	private int contadorLista = 0;
	private int contadorArray = 0;

	// *****************************************************
	// RELACIONES
	// *****************************************************

	private TiempoThreadArbol hiloTiempoArbol = new TiempoThreadArbol(this);
	private TiempoThreadArrayList hiloTiempoArrayList = new TiempoThreadArrayList(this);
	private TiempoThreadLista hiloTiempoLista = new TiempoThreadLista(this);
	private TiempoThreadCronometro hiloCronometro = new TiempoThreadCronometro(this);
	private CirculoThread hiloCirculo;

	@FXML
	void initialize() {
		organizador = new OrganizadorCarrera();

	}

	@FXML
	public void run(ActionEvent event) {
		hiloCirculo = new CirculoThread(this);
		hiloCronometro.start();
		hiloCirculo.start();
		if (exceptions() == false) {
			listasMetodos();
			arbolMetodos();
			arrayListMetodos();
		}

	}

	private boolean exceptions() {
		boolean problem = false;
		if (cantidadAlgoritmos.getText().equals("")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Empty");
			alert.setHeaderText("You need to enter the number of algorithms");
			alert.showAndWait();
			problem = true;
		}
		if (add.isSelected() == false && search.isSelected() == false && delete.isSelected() == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Not selected");
			alert.setHeaderText("You need to select the algorithm");
			alert.showAndWait();
			problem = true;
		}
		if (iterative.isSelected() == false && recursive.isSelected() == false) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Not selected");
			alert.setHeaderText("You need to select the mode");
			alert.showAndWait();
			problem = true;
		}
		return problem;
	}

	public void arbolMetodos() {
		long cantidadA = Long.parseLong(cantidadAlgoritmos.getText());
		ArrayList<AlgoritmoArbol> elementosArbol = organizador
				.generarAleatoriamenteAlgoritmosArbol(Integer.parseInt(cantidadAlgoritmos.getText()));

		PBArbolThread pbArbolThread = new PBArbolThread(this, cantidadA);
		new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(1000);
					if (add.isSelected() != true) {
						for (int i = 0; i < cantidadA; i++) {
							organizador.agregarArbolR(elementosArbol.get(i));
						}
					}
					hiloTiempoArbol.start();
					pbArbolThread.start();
					while (contadorArbol < cantidadA) {
						if (iterative.isSelected() == true) {
							if (add.isSelected() == true) {
								organizador.agregarArbolI(elementosArbol.get(contadorArbol));

							} else if (search.isSelected() == true) {
								organizador.buscarArbolI(organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							} else if (delete.isSelected() == true) {
								organizador.eliminarArbolI(new AlgoritmoArbol(
										organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA)));
							}

						} else if (recursive.isSelected() == true) {
							if (add.isSelected() == true) {
								organizador.agregarArbolR(elementosArbol.get(contadorArbol));
							} else if (search.isSelected() == true) {
								organizador.buscarArbolR(organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							} else if (delete.isSelected() == true) {
								organizador.eliminarArbolR(organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							}

						}
						System.out.println(contadorArbol);
						contadorArbol++;
					}
					hiloTiempoArbol.desactivate();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	public void listasMetodos() {
		long cantidadA = Long.parseLong(cantidadAlgoritmos.getText());
		ArrayList<AlgoritmoLista> elementosLista = organizador
				.generarAleatoriamenteAlgoritmosLista(Integer.parseInt(cantidadAlgoritmos.getText()));

		PBListaThread pbListaThread = new PBListaThread(this, cantidadA);
		new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(100);
					if (add.isSelected() != true) {
						for (int i = 0; i < cantidadA; i++) {
							organizador.agregarListaI(elementosLista.get(i));
						}
					}
					hiloTiempoLista.start();
					pbListaThread.start();
					while (contadorLista < cantidadA) {

						if (iterative.isSelected() == true) {
							if (add.isSelected() == true) {

								organizador.agregarListaI(elementosLista.get(contadorLista));
							} else if (search.isSelected() == true) {
								organizador.buscarListaI(organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							} else if (delete.isSelected() == true) {
								organizador.eliminarListaI(organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							}

						} else if (recursive.isSelected() == true) {
							if (add.isSelected() == true) {
								organizador.agregarListaR(organizador.getPrimero(), elementosLista.get(contadorLista));
							} else if (search.isSelected() == true) {
								organizador.buscarListaR(organizador.getPrimero(),
										organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							} else if (delete.isSelected() == true) {
								organizador.eliminarListaR(organizador.getPrimero(),
										organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							}

						}
						System.out.println(contadorLista);
						contadorLista++;
					}
					hiloTiempoLista.desactivate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	@FXML
	public void arrayListMetodos() {
		long cantidadA = Long.parseLong(cantidadAlgoritmos.getText());
		ArrayList<AlgoritmoArrayList> elementosArray = organizador
				.generarAleatoriamenteAlgoritmosArray(Long.parseLong(cantidadAlgoritmos.getText()));

		PBArrayThread pbArrayThread = new PBArrayThread(this, cantidadA);
		new Thread() {
			@Override
			public void run() {

				try {
					Thread.sleep(100);
					if (add.isSelected() != true) {
						for (int i = 0; i < cantidadA; i++) {
							organizador.agregarArrayList(elementosArray.get(i));
						}
					}
					hiloTiempoArrayList.start();
					pbArrayThread.start();
					while (contadorArray < cantidadA) {
						if (add.isSelected() == true) {
							organizador.agregarArrayList(elementosArray.get(contadorArray));
						} else if (iterative.isSelected() == true) {
							organizador.agregarArrayList(elementosArray.get(contadorArray));
							if (search.isSelected() == true) {
								organizador
										.buscarArrayListI(organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							} else if (delete.isSelected() == true) {
								organizador.eliminarArrayListI(
										organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							}
						} else if (recursive.isSelected() == true) {

							if (search.isSelected() == true) {
								organizador.buscarArrayListR(0, cantidadA,
										organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							} else if (delete.isSelected() == true) {
								organizador.eliminarArrayListR(0, cantidadA,
										organizador.generarAleatoriamenteNombreAlgoritmo(cantidadA));
							}
						}
						System.out.println(contadorArray);
						contadorArray++;
					}
					hiloTiempoArrayList.desactivate();

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	public void cronometroArrayList() {
		segundoArrayList++;

		String txtSegundoArrayList = "";
		String txtMinutoArrayList = "";
		String txtHoraArrayList = "";

		if (segundoArrayList > 59) {
			segundoArrayList = 0;
			minutoArrayList++;
		}

		if (minutoArrayList > 59) {
			minutoArrayList = 0;
			horaArrayList++;
		}

		if (segundoArrayList < 10) {
			txtSegundoArrayList = "0" + segundoArrayList;
		} else {
			txtSegundoArrayList = segundoArrayList + "";
		}
		if (minutoArrayList < 10) {
			txtMinutoArrayList = "0" + minutoArrayList;
		} else {
			txtMinutoArrayList = minutoArrayList + "";
		}
		if (horaArbol < 10) {
			txtHoraArrayList = "0" + horaArrayList;
		} else {
			txtHoraArrayList = horaArrayList + "";
		}

		clockArrayList.setText(txtHoraArrayList + ":" + txtMinutoArrayList + ":" + txtSegundoArrayList);

	}

	public void cronometroLista() {
		segundoLista++;

		String txtSegundoLista = "";
		String txtMinutoLista = "";
		String txtHoraLista = "";

		if (segundoLista > 59) {
			segundoLista = 0;
			minutoLista++;
		}

		if (minutoLista > 59) {
			minutoLista = 0;
			horaLista++;
		}

		if (segundoLista < 10) {
			txtSegundoLista = "0" + segundoLista;
		} else {
			txtSegundoLista = segundoLista + "";
		}
		if (minutoLista < 10) {
			txtMinutoLista = "0" + minutoLista;
		} else {
			txtMinutoLista = minutoLista + "";
		}
		if (horaLista < 10) {
			txtHoraLista = "0" + horaLista;
		} else {
			txtHoraLista = horaLista + "";
		}

		clockLista.setText(txtHoraLista + ":" + txtMinutoLista + ":" + txtSegundoLista);
	}

	public void cronometroArbol() {
		segundoArbol++;

		String txtSegundoArbol = "";
		String txtMinutoArbol = "";
		String txtHoraArbol = "";

		if (segundoArbol > 59) {
			segundoArbol = 0;
			minutoArbol++;
		}
		if (minutoArbol > 59) {
			minutoArbol = 0;
			horaArbol++;
		}

		if (segundoArbol < 10) {
			txtSegundoArbol = "0" + segundoArbol;
		} else {
			txtSegundoArbol = segundoArbol + "";
		}

		if (minutoArbol < 10) {
			txtMinutoArbol = "0" + minutoLista;
		} else {
			txtMinutoArbol = minutoArbol + "";
		}
		if (horaArbol < 10) {
			txtHoraArbol = "0" + horaArbol;
		} else {
			txtHoraArbol = horaArbol + "";
		}
		clockArbol.setText(txtHoraArbol + ":" + txtMinutoArbol + ":" + txtSegundoArbol);

	}

	public void cronometro() {
		segundoCronometro++;

		String txtSegundoCronometro = "";
		String txtMinutoCronometro = "";
		String txtHoraCronometro = "";

		if (segundoCronometro > 59) {
			segundoCronometro = 0;
			minutoCronometro++;
		}
		if (minutoCronometro > 59) {
			minutoCronometro = 0;
			horaCronometro++;
		}

		if (segundoCronometro < 10) {
			txtSegundoCronometro = "0" + segundoCronometro;
		} else {
			txtSegundoCronometro = segundoCronometro + "";
		}

		if (minutoArbol < 10) {
			txtMinutoCronometro = "0" + minutoCronometro;
		} else {
			txtMinutoCronometro = minutoCronometro + "";
		}
		if (horaArbol < 10) {
			txtHoraCronometro = "0" + horaCronometro;
		} else {
			txtHoraCronometro = horaCronometro + "";
		}
		clockCronometro.setText(txtHoraCronometro + ":" + txtMinutoCronometro + ":" + txtSegundoCronometro);
	}

	public void circle() {
		if (circuloGrande.getRadius() > min) {
			circuloGrande.setRadius(circuloGrande.getRadius() - 1);
		} else if (circuloGrande.getRadius() == min) {
			while (circuloGrande.getRadius() < max) {
				circuloGrande.setRadius(circuloGrande.getRadius() + 1);
			}
		}
		if (circuloPequeno.getRadius() < max) {
			circuloPequeno.setRadius(circuloPequeno.getRadius() + 1);
		} else if (circuloPequeno.getRadius() == max) {
			while (circuloPequeno.getRadius() > min) {
				circuloPequeno.setRadius(circuloPequeno.getRadius() - 1);
			}

		}
	}

	public void progressBarArbol(long cantidadA) {
		if (this.contadorArbol < cantidadA) {
			if (pbArbol.getProgress() != 1) {
				pbArbol.setProgress(pbArbol.getProgress() + 0.01);
			}
		}
	}

	public void progressBarLista(long cantidadA) {

		if (this.contadorLista < cantidadA) {
			if (pbListas.getProgress() != 1) {
				pbListas.setProgress(pbListas.getProgress() + 0.1);
			}
		}
	}

	public void progressBarArray(long cantidadA) {
		if (this.contadorArray < cantidadA) {
			if (pbArray.getProgress() != 1) {
				pbArray.setProgress(pbArray.getProgress() + 0.1);
			}
		}
	}

}
