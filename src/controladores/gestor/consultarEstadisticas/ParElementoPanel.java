package controladores.gestor.consultarEstadisticas;

public class ParElementoPanel<E,P> {
	private E elem;
	private P panel;
	
	public ParElementoPanel(E elem, P panel) {
		this.elem = elem;
		this.panel = panel;
	}
	
	public E getElem() { return elem; }
	
	public P getPanel() { return panel; }
}
