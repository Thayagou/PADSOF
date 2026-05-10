package controladores.gestor.consultarEstadisticas;

/**
 * Clase que permite almacenar un par de elemento y panel. Sirve para reordenaciones
 *
 * @param <E> Parámetro genérico del producto asociado al panel
 * @param <P> Parámetro genérico de la clase del panel
 */
public class ParElementoPanel<E,P> {
	
	/** Elemento almacenado */
	private E elem;
	
	/** Panel que se muestra por pantalla asociado al elemento */
	private P panel;
	
	/**
	 * Instancia un nuevo Objeto ParElementoPanel.
	 *
	 * @param elem Elemento 
	 * @param panel Panel asociado al elemento
	 */
	public ParElementoPanel(E elem, P panel) {
		this.elem = elem;
		this.panel = panel;
	}
	
	/**
	 * Getter del elemento
	 *
	 * @return valor de Elem
	 */
	public E getElem() { return elem; }
	
	/**
	 * Getter del panel.
	 *
	 * @return valor de Panel
	 */
	public P getPanel() { return panel; }
}
