package model;

public class AlgoritmoArbol {

	// ATRIBUTOS

	private long id;

	// RELACIONES

	// Arbol

	private AlgoritmoArbol izquierda;
	private AlgoritmoArbol derecha;

	public AlgoritmoArbol(long id) {
		this.id = id;
	}

	// METODOS

	public int compareTo(AlgoritmoArbol o) {
//		return id.compareTo(o.id);
		return Long.compare(id, o.getId());
	}

	public void agregaI(AlgoritmoArbol nuevo) {
		if (compareTo(nuevo) > 0) {
			if (izquierda == null) {
				izquierda = nuevo;
			} else {
				AlgoritmoArbol actual = izquierda;
				while (actual != null) {
					actual = actual.getIzquierda();
				}
				actual.setIzquierda(nuevo);
			}
		}
	}

	public void agregarR(AlgoritmoArbol nuevo) {
		if (compareTo(nuevo) > 0) {
			if (izquierda == null) {
				izquierda = nuevo;
			} else {
				izquierda.agregarR(nuevo);
			}
		} else {
			if (derecha == null) {
				derecha = nuevo;
			} else {
				derecha.agregarR(nuevo);
			}
		}
	}

	public AlgoritmoArbol eliminarR(long eliminar) {
		if (Long.compare(id, eliminar) == 0) {
			if (izquierda == null)
				return derecha;
			if (derecha == null)
				return izquierda;
			AlgoritmoArbol sucesor = derecha.darMenor();
			if (sucesor != null)
				derecha = derecha.eliminarR(sucesor.getId());
			if (sucesor.izquierda != null)
				sucesor.izquierda = izquierda;
			if (sucesor.derecha != null)
				sucesor.derecha = derecha;
			return sucesor;
		} else if (Long.compare(id, eliminar) > 0) {
			if (izquierda != null)
				izquierda = izquierda.eliminarR(eliminar);
		} else {
			if (derecha != null)
				derecha = derecha.eliminarR(eliminar);
		}
		return this;
	}

	public AlgoritmoArbol darMenor() {
		return (izquierda == null) ? this : izquierda.darMenor();
	}

	public AlgoritmoArbol buscarI(long buscado) {
		AlgoritmoArbol aux = this;
		while (aux != null) {
			int comp = Long.compare(aux.getId(), buscado);
			if (comp == 0)
				return aux;
			else if (comp > 0)
				aux = aux.getIzquierda();
			else
				aux = aux.getDerecha();
		}
		return null;
	}

	public AlgoritmoArbol buscarR(long buscado) {
		if (Long.compare(id, buscado) == 0)
			return this;
		else if (Long.compare(id, buscado) > 0)
			return (izquierda == null) ? null : izquierda.buscarR(buscado);
		else
			return (derecha == null) ? null : derecha.buscarR(buscado);
	}

	// METODOS GETTER Y SETTER

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AlgoritmoArbol getIzquierda() {
		return izquierda;
	}

	public void setIzquierda(AlgoritmoArbol izquierda) {
		this.izquierda = izquierda;
	}

	public AlgoritmoArbol getDerecha() {
		return derecha;
	}

	public void setDerecha(AlgoritmoArbol derecha) {
		this.derecha = derecha;
	}

}
