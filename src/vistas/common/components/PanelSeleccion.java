package vistas.common.components;

/**
 * Interfaz que nos sirve para poder utilizar múltiples paneles de selección distintos en una misma ventana usando el método compartido de añadir panel
 */
public interface PanelSeleccion {
	/**
	 * Comprueba si el panel se encuentra en estado de selección o no
	 * 
	 * @return true si está seleccionado, false en caso contrario
	 */
	public boolean isSeleccionado();
	
	/**
	 * Cambia el estado del panel
	 */
	public void toggleCheckBox();
}
