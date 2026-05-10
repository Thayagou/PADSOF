package vistas.gestor.gestionarEmpleados;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.PanelDisplay;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de gestionar los empleados.
 */
public class VentanaGestionarEmpleados extends JPanel implements VentanaConDisplay<PanelDisplay>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de añadir un nuevo empleado. */
	public static final String NUEVO_EMPLEADO_ACTION = "Dar de alta nuevo empleado";
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaEmpleados = new JPanel();

	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella.
	 */
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
	
	/**
	 * Vacía la lista de empleados almacenada
	 */
	public void vaciarLista() {
		listaEmpleados.removeAll();
	}
	
	/**
	 * Vuelve a pintar el scroll con los últimos datos almacenados
	 */
	public void refreshList() {
		listaEmpleados.revalidate();
		listaEmpleados.repaint();
	}

	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	@Override
	public <K extends PanelDisplay> PanelDisplay anadirDisplay(K panelDisplay) {
		listaEmpleados.add(panelDisplay);
		refreshList();
		
		return panelDisplay;
	}

}
