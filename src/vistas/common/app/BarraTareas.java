package vistas.common.app;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Esta clase representa la barra superior en los menús de usuario
 */
public abstract class BarraTareas extends JPanel{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor de una barra de tareas superior
	 */
	public BarraTareas() {}

	/**
	 * Asigna un controlador a los componentes
	 * @param c Controlador que se asigna
	 */
	public abstract void setControlador(ActionListener c);
}
