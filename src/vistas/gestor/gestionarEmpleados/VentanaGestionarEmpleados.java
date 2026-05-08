package vistas.gestor.gestionarEmpleados;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelDisplay;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarEmpleados extends JPanel implements VentanaConDisplay<PanelDisplay>{
	private static final long serialVersionUID = 1L;
	public static final String NUEVO_EMPLEADO_ACTION = "Dar de alta nuevo empleado";
	private JPanel listaEmpleados = new JPanel();

	public VentanaGestionarEmpleados() {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		listaEmpleados.setLayout(new BoxLayout(listaEmpleados, BoxLayout.Y_AXIS));
		listaEmpleados.setBackground(ColorPalette.CARD_LIGHT.getColor());
		//listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaEmpleados);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		
		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);
		
		JPanel cabeceraEmpleados = PanelFactory.getVentanaConCabecera("      Empleados de la tienda", contenido);
		//cabeceraEmpleados.setOpaque(false);
		this.add(cabeceraEmpleados , BorderLayout.CENTER);
		
		refreshList();
	}
	
	public void refreshList() {
		listaEmpleados.revalidate();
		listaEmpleados.repaint();
	}

	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaEmpleados.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
