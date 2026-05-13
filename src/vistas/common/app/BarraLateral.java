package vistas.common.app;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Esta clase representa la barra lateral en los menús de usuario
 */
public abstract class BarraLateral extends JPanel {
	private static final long serialVersionUID = 1L;
	/** Procentaje de indentación */
	public static final double PERC_INDENTED=0.9;
	
	/**
	 * Constructor de la barra lateral
	 */
	public BarraLateral() {}

	/**
	 * Asigna un controlador a los componentes
	 * @param c Controlador que se asigna
	 */
	public abstract void setControlador(ActionListener c);
}
