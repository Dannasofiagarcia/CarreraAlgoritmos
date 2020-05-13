package model;

public class AlgoritmoLista {

	// ATRIBUTOS

	private long id;

	// RELACIONES

	private AlgoritmoLista siguiente;
	private AlgoritmoLista anterior;

	// CONSTRUCTOR

	public AlgoritmoLista(long id) {
		this.id = id;
	}

	// METODOS

	public void eliminarSiguiente() {
		siguiente.siguiente.setSiguiente(this);
		siguiente = siguiente.siguiente;
	}

	// METODOS GETTER Y SETTER

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AlgoritmoLista getSiguiente() {
		return siguiente;
	}

	public void setSiguiente(AlgoritmoLista siguiente) {
		this.siguiente = siguiente;
	}

	public AlgoritmoLista getAnterior() {
		return anterior;
	}

	public void setAnterior(AlgoritmoLista anterior) {
		this.anterior = anterior;
	}

}
