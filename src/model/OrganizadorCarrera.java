package model;

import java.util.ArrayList;

public class OrganizadorCarrera {

	// RELACIONES

	private AlgoritmoArbol raiz;
	private AlgoritmoLista primero;
	private ArrayList<AlgoritmoArrayList> algoritmos;
	private int contador;

	// CONSTRUCTOR

	public OrganizadorCarrera() {
		algoritmos = new ArrayList<AlgoritmoArrayList>();
	}

	// METODOS

	public long generarAleatoriamenteNombreAlgoritmo(long cantidadA) {

		long numeroId = (int) (Math.random() * cantidadA + 1);
		return numeroId;
	}

	// Métodos ArrayList

	public ArrayList<AlgoritmoArrayList> generarAleatoriamenteAlgoritmosArray(long cantidadAlgoritmos) {
		int contador = 0;
		ArrayList algoritmos = new ArrayList<AlgoritmoArrayList>();
		String id = "";

		while (contador < cantidadAlgoritmos) {
			id = "Algoritmo" + contador;
			AlgoritmoArrayList algoritmoArray = new AlgoritmoArrayList(
					generarAleatoriamenteNombreAlgoritmo(cantidadAlgoritmos));
			algoritmos.add(algoritmoArray);
			contador++;
		}
		return algoritmos;
	}

	public void agregarArrayList(AlgoritmoArrayList algoritmo) {

		algoritmos.add(algoritmo);
	}

	// En el elemento buscado, cuando llamemos el metodo ponemos ahí un elemento al
	// azar
	public boolean buscarArrayListR(int i, long cantidadAlgoritmos, long buscado) throws StackOverflowError {
		boolean encontrado = false;
		if (i > 0 && i < cantidadAlgoritmos) {
			if (algoritmos.get(i).getId() == buscado) {
				encontrado = true;
				return encontrado;
			} else {
				return buscarArrayListR(i + 1, cantidadAlgoritmos, buscado);
			}
		} else {
			return encontrado;
		}
	}

	public boolean eliminarArrayListR(int i, long cantidadAlgoritmos, long buscado) throws StackOverflowError {
		boolean encontrado = false;
		if (i > 0 && i < cantidadAlgoritmos) {
			if (algoritmos.get(i).getId() == buscado) {
				algoritmos.remove(i);
				encontrado = true;
				return encontrado;
			} else {
				return eliminarArrayListR(i + 1, cantidadAlgoritmos, buscado);
			}
		} else {
			return encontrado;
		}
	}

	public boolean buscarArrayListI(long buscado) {
		boolean encontrado = false;
		for (int i = 0; i < algoritmos.size() && !encontrado; i++) {
			if (algoritmos.get(i).getId() == buscado) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	public boolean eliminarArrayListI(long buscado) {
		boolean encontrado = false;
		for (int i = 0; i < algoritmos.size() && !encontrado; i++) {
			if (algoritmos.get(i).getId() == buscado) {
				encontrado = true;
			}
		}
		return encontrado;
	}

	// Métodos árbol

	public ArrayList<AlgoritmoArbol> generarAleatoriamenteAlgoritmosArbol(long cantidadAlgoritmos) {
		int contador = 0;
		String id = "";
		ArrayList algoritmos = new ArrayList<AlgoritmoArbol>();
		while (contador < cantidadAlgoritmos) {
			id = "Algoritmo" + contador;
			AlgoritmoArbol algoritmoArbol = new AlgoritmoArbol(
					generarAleatoriamenteNombreAlgoritmo(cantidadAlgoritmos));
			algoritmos.add(algoritmoArbol);
			contador++;
		}
		return algoritmos;
	}

	public void agregarArbolI(AlgoritmoArbol nuevo) {
		if (raiz == null) {
			raiz = nuevo;
		} else {
			AlgoritmoArbol aux = raiz;
			boolean insertado = false;
			while (insertado != true) {
				if (aux.compareTo(nuevo) > 0) {
					if (aux.getIzquierda() == null) {
						aux.setIzquierda(nuevo);
						insertado = true;
					} else if (aux.getIzquierda() != null) {
						aux = aux.getIzquierda();
					}
				} else if (aux.compareTo(nuevo) < 0) {
					if (aux.getDerecha() == null) {
						aux.setDerecha(nuevo);
						insertado = true;
					} else if (aux.getDerecha() != null) {
						aux = aux.getDerecha();
					}
				} else if (aux.compareTo(nuevo) == 0) {
					if (aux.getIzquierda() == null) {
						aux.setIzquierda(nuevo);
						insertado = true;
					} else if (aux.getDerecha() == null) {
						aux.setDerecha(nuevo);
						insertado = true;
					} else {
						aux = aux.getDerecha();
					}
				}
			}
		}
	}

	public void agregarArbolR(AlgoritmoArbol nuevo) throws StackOverflowError {
		if (raiz == null)
			raiz = nuevo;
		else
			raiz.agregarR(nuevo);
	}

	public void eliminarArbolR(long eliminar) throws StackOverflowError {
		if (raiz != null) {
			raiz = raiz.eliminarR(eliminar);
		}
	}

	public boolean buscarArbolR(long buscado) throws StackOverflowError {
		if (raiz.buscarI(buscado) != null) {
			return true;
		} else {
			return false;
		}
	}

	public AlgoritmoArbol minSubArbol(AlgoritmoArbol algoritmo) {

		if (algoritmo != null && algoritmo.getIzquierda() != null) {
			while (esHoja(algoritmo)) {
				algoritmo = algoritmo.getIzquierda();
			}
		}

		return algoritmo;
	}

	public boolean esHoja(AlgoritmoArbol algoritmo) {
		return algoritmo.getIzquierda() == null && algoritmo.getDerecha() == null;
	}

	private final int ONE_NODE_LEFT = 1;
	private final int ONE_NODE_RIGHT = 2;
	private final int TWO_NODES = 3;

	public boolean eliminarArbolI(AlgoritmoArbol eliminar) {
		boolean eliminado = false;
		if (raiz != null) {

			if (esHoja(eliminar)) {
				eliminado = eliminarHoja(eliminar);
			} else if (eliminar.getDerecha() != null && eliminar.getIzquierda() == null) {
				eliminado = eliminarConHijo(eliminar, ONE_NODE_RIGHT);
			} else if (eliminar.getDerecha() == null && eliminar.getIzquierda() != null) {
				eliminado = eliminarConHijo(eliminar, ONE_NODE_LEFT);
			} else {
				eliminado = eliminarConHijo(eliminar, TWO_NODES);
			}
		}
		return eliminado;
	}

	private boolean eliminarHoja(AlgoritmoArbol eliminar) {
		boolean eliminado = false;
		if (eliminar == raiz) {
			raiz = null;
			eliminado = true;
		} else {

			AlgoritmoArbol padre = getPadre(eliminar);
			if (padre != null) {
				if (padre.getIzquierda() != null && padre.getIzquierda() == eliminar) {
					padre.setIzquierda(null);
					eliminado = true;
				}

				if (padre.getDerecha() != null && padre.getDerecha() == eliminar) {
					padre.setDerecha(null);
					eliminado = true;
				}

				eliminar = null;
			}
		}
		return eliminado;
	}

	private boolean eliminarConHijo(AlgoritmoArbol eliminar, int tipoAlgoritmoArbol) {
		boolean eliminado = false;
		AlgoritmoArbol siguiente = null;

		switch (tipoAlgoritmoArbol) {
		case ONE_NODE_LEFT:
			siguiente = eliminar.getIzquierda();
			break;
		case ONE_NODE_RIGHT:
			siguiente = minSubArbol(eliminar.getDerecha());
			break;
		case TWO_NODES:

			siguiente = minSubArbol(eliminar.getDerecha());

			if (!esRaiz(getPadre(siguiente))) {
				if (getPadre(siguiente).getIzquierda() == siguiente) {
					getPadre(siguiente).setIzquierda(null);
				} else if (getPadre(siguiente).getDerecha() == siguiente) {
					getPadre(siguiente).setDerecha(null);
				}
				eliminado = true;
			}

			break;
		}

		if (!esRaiz(eliminar)) {
			if (getPadre(eliminar).getIzquierda() == eliminar) {
				getPadre(eliminar).setIzquierda(siguiente);
			} else if (getPadre(eliminar).getDerecha() == eliminar) {
				getPadre(eliminar).setDerecha(siguiente);
			}
			eliminado = true;

		} else {
			raiz = siguiente;
			eliminado = true;
		}

		if (eliminar.getDerecha() != null && eliminar.getDerecha() != siguiente) {
			siguiente.setDerecha(eliminar.getDerecha());
		}
		if (eliminar.getIzquierda() != null && eliminar.getIzquierda() != siguiente) {
			siguiente.setIzquierda(eliminar.getIzquierda());
		}

		eliminar = null;
		return eliminado;
	}

	public AlgoritmoArbol getPadre(AlgoritmoArbol algoritmo) {
		AlgoritmoArbol aux = raiz;
		AlgoritmoArbol padre = null;
		boolean encontrado = false;
		while (encontrado == true) {
			if (aux.getDerecha() == algoritmo || aux.getIzquierda() == algoritmo) {
				padre = aux;
				encontrado = true;
			} else {
				if (algoritmo.compareTo(aux) > 0) {
					aux = aux.getDerecha();
				} else if (algoritmo.compareTo(aux) < 0) {
					aux = aux.getIzquierda();
				}
			}
		}
		return padre;
	}

	public boolean esRaiz(AlgoritmoArbol algoritmo) {
		return algoritmo == raiz;
	}

	public boolean buscarArbolI(long buscado) {
		if (raiz.buscarI(buscado) != null) {
			return true;
		} else {
			return false;
		}
	}

	// Método listas

	public ArrayList<AlgoritmoLista> generarAleatoriamenteAlgoritmosLista(long cantidadAlgoritmos) {
		int contador = 0;
		String id = "";
		ArrayList algoritmos = new ArrayList<AlgoritmoLista>();
		while (contador < cantidadAlgoritmos) {
			id = "Algoritmo" + contador;
			AlgoritmoLista algoritmoLista = new AlgoritmoLista(
					generarAleatoriamenteNombreAlgoritmo(cantidadAlgoritmos));
			algoritmos.add(algoritmoLista);
			contador++;
		}
		return algoritmos;
	}

	public void agregarListaR(AlgoritmoLista aux, AlgoritmoLista nuevo) throws StackOverflowError {
		if (primero == null) {
			primero = nuevo;
		} else {
			if (aux.getSiguiente() == null) {
				aux.setSiguiente(nuevo);
				nuevo.setAnterior(aux);
			} else {
				agregarListaR(aux.getSiguiente(), nuevo);
			}
		}
	}

	public boolean eliminarListaR(AlgoritmoLista actual, long eliminar) throws StackOverflowError {
		boolean deleted = false;
		if (primero != null) {
			if (primero.getId() == eliminar) {
				primero = primero.getSiguiente();
				deleted = true;
			} else {

				if (actual.getId() == eliminar) {
					AlgoritmoLista anterior = actual.getAnterior();
					anterior.eliminarSiguiente();
					deleted = true;
				} else {
					eliminarListaR(actual.getSiguiente(), eliminar);
				}

			}
		}
		return deleted;
	}

	public boolean buscarListaR(AlgoritmoLista aux, long buscado) throws StackOverflowError {
		if (primero == null) {
			return false;
		} else if (aux.getId() == buscado) {
			return true;
		} else {
			return buscarListaR(aux.getSiguiente(), buscado);
		}
	}

	public void agregarListaI(AlgoritmoLista nuevo) {
		if (primero == null) {
			primero = nuevo;
		} else {
			AlgoritmoLista aux = primero;
			while (aux.getSiguiente() != null) {
				aux = aux.getSiguiente();

			}
			aux.setSiguiente(nuevo);
			nuevo.setAnterior(aux);
		}
	}

	public boolean eliminarListaI(long eliminar) {
		boolean deleted = false;
		if (primero != null) {
			if (primero.getId() == eliminar) {
				primero = primero.getSiguiente();
				deleted = true;
			}
		} else {
			for (AlgoritmoLista i = primero.getSiguiente(); i != null; i = i.getSiguiente()) {
				if (Long.compare(i.getId(), eliminar) == 0) {
					AlgoritmoLista anterior = i.getAnterior();
					anterior.eliminarSiguiente();
					deleted = true;
				}
			}
		}

		return deleted;
	}

	public boolean buscarListaI(long buscado) {
		AlgoritmoLista aux = primero;
		while (aux != null) {
			if (aux.getId() == buscado) {
				return true;
			} else {
				aux = aux.getSiguiente();
			}
		}
		return false;
	}

	// METODOS GETTER Y SETTER

	public AlgoritmoArbol getRaiz() {
		return raiz;
	}

	public void setRaiz(AlgoritmoArbol raiz) {
		this.raiz = raiz;
	}

	public AlgoritmoLista getPrimero() {
		return primero;
	}

	public void setPrimero(AlgoritmoLista primero) {
		this.primero = primero;
	}

	public ArrayList<AlgoritmoArrayList> getAlgoritmos() {
		return algoritmos;
	}

	public void setAlgoritmos(ArrayList<AlgoritmoArrayList> algoritmos) {
		this.algoritmos = algoritmos;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

}
