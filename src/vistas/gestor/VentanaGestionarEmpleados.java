package vistas.gestor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import modelo.sistema.Tienda;
import vistas.common.VentanaConDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

public class VentanaGestionarEmpleados extends JPanel implements VentanaConDisplay<PanelEmpleado>{
	private static final long serialVersionUID = 1L;
	public static final String NUEVO_EMPLEADO_ACTION = "Dar de alta nuevo empleado";
	
	private JPanel listaEmpleados = new JPanel();

	public VentanaGestionarEmpleados(Tienda tienda) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		ButtonFactory f = new ButtonFactory();
		JPanel panelDarDeAlta = new JPanel();
		
		
		JButton nuevoEmpleadoButton = f.newRoundedButton(NUEVO_EMPLEADO_ACTION, 300, 300, 0.5f);
		f.paintButton(nuevoEmpleadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		f.addMouseMecanics(nuevoEmpleadoButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		panelDarDeAlta.add(nuevoEmpleadoButton);
		listaEmpleados.setLayout(new BoxLayout(listaEmpleados, BoxLayout.Y_AXIS));
		listaEmpleados.setBackground(ColorPalette.CARD_LIGHT.getColor());
		//listaEmpleados.setOpaque(false);

		JScrollPane scroll = PanelFactory.getScroll(listaEmpleados);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		
		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);
		JPanel cabeceraDeAlta = PanelFactory.getVentanaConCabecera("  Nuevo empleado", panelDarDeAlta);
		cabeceraDeAlta.setOpaque(false);
		this.add(cabeceraDeAlta, BorderLayout.WEST);
		
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
	public <K extends PanelEmpleado> PanelEmpleado anadirDisplay(K panelDisplay) {
		listaEmpleados.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
