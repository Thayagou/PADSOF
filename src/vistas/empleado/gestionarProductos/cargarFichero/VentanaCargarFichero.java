package vistas.empleado.gestionarProductos.cargarFichero;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import vistas.common.PanelFormulario;

public class VentanaCargarFichero extends JPanel{
	private static final long serialVersionUID = 1L;
	public final static String CONFIRMAR_ACTION_NAME = "Confirmar";
	private PanelFormulario panel;

	public VentanaCargarFichero() {
		setOpaque(false);
		String[] labels = {"Nombre del fichero"};
		Integer[] indx = {};
		panel = new PanelFormulario("Cargar fichero de productos", CONFIRMAR_ACTION_NAME, indx, labels);
		add(panel, BorderLayout.CENTER);
	}
	
	public void setControlador(ActionListener c) {
		panel.setControlador(c);
	}
	
	public String getNombreFichero() {
		return panel.getCampo(0);
	}
}
