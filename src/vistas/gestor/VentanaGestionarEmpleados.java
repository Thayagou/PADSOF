package vistas.gestor;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import modelo.sistema.Tienda;
import vistas.common.VentanaConDisplay;

public class VentanaGestionarEmpleados extends JPanel implements VentanaConDisplay<PanelEmpleado>{
	private static final long serialVersionUID = 1L;
	
	private JPanel listaEmpleados = new JPanel();

	public VentanaGestionarEmpleados(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		
	}

	@Override
	public <K extends PanelEmpleado> PanelEmpleado anadirDisplay(K panelDisplay) {
		// TODO Auto-generated method stub
		return null;
	}

}
