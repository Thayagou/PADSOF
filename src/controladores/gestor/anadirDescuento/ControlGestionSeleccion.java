package controladores.gestor.anadirDescuento;

import vistas.common.components.PanelSeleccion;

/**
 * Interfaz que define el comportamiento de un Controlador que maneja la selección de elementos 
 * @param <T> Tipo de elemento a gestionar
 */
public interface ControlGestionSeleccion<T> {
	
	/**
	 * Marca el elemeto como seleccionado y pasa junto a él su panel asociado
	 *
	 * @param elem Elemento que se desea seleccionar
	 * @param panel Panel correspondiente a dicho elemento
	 * @param seleccionado Determina si está o no seleccionado
	 */
	public void setSeleccionado(T elem, PanelSeleccion panel, boolean seleccionado);
}
