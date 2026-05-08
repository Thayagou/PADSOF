package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import vistas.common.assets.PanelMultiopcion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.*;

// TODO: Auto-generated Javadoc
/**
 * Muestra los resultados de una búsqueda en forma de lista scrolleable. Incluye
 * combo de ordenación (igual que maqueta 4).
 */
public class VentanaResultadosNoRegistrado extends JPanel implements VentanaConDisplay<PanelProducto>{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo productos. */
	protected ArrayList<? super PanelProducto> productos = new ArrayList<>();
	
	/** Campo listaPanel. */
	protected JPanel listaPanel;
	
	/** Campo ordenCombo. */
	protected PanelMultiopcion panelOpciones;

	/** Campo clickListener. */
	protected ActionListener clickListener; // para navegar al detalle

	/** Constante ORDENES. */
	protected static final String[] ORDENES = { "Mejor valorados", "Peor valorados", "Precio: menor a mayor",
			"Precio: mayor a menor", "Nombre A-Z", "Nombre Z-A" };

	/**
	 * Instancia un nuevo Objeto VentanaResultadosNoRegistrado.
	 *
	 * @param productos parámetro productos
	 */
	public VentanaResultadosNoRegistrado() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// Lista 
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		
		JScrollPane scroll = PanelFactory.getScroll(listaPanel);
		JPanel contenido = new JPanel(new BorderLayout());
		contenido.add(scroll);

		panelOpciones = new PanelMultiopcion("Resultados de búsqueda", contenido, ORDENES);
		panelOpciones.setControlador(e -> refrescarLista());
		
		add(panelOpciones);

		refrescarLista();
	}
	
	public void anadirProducto(String nombre, String descripcion, String image, double puntuacionMedia, double precio, String...categorias) {
		productos.add(new PanelProducto(nombre, descripcion, image, puntuacionMedia, precio, "Ver producto",categorias));
		refrescarLista();
	}

	/**
	 * refrescarLista.
	 */
	protected void refrescarLista() {
		PanelProducto[] ordenados = Arrays.copyOf(productos.toArray(new PanelProducto[0]), productos.size());
		switch (panelOpciones.getOpcionSeleccionada()) {
		case 0 -> Arrays.sort(ordenados, Comparator.comparingDouble(PanelProducto::getPuntuacionMedia).reversed());
		case 1 -> Arrays.sort(ordenados, Comparator.comparingDouble(PanelProducto::getPuntuacionMedia));
		case 2 -> Arrays.sort(ordenados, Comparator.comparingDouble(PanelProducto::getPrecio));
		case 3 -> Arrays.sort(ordenados, Comparator.comparingDouble(PanelProducto::getPrecio).reversed());
		case 4 -> Arrays.sort(ordenados, Comparator.comparing(PanelProducto::getNombre));
		case 5 -> Arrays.sort(ordenados, Comparator.comparing(PanelProducto::getNombre).reversed());
		}

		listaPanel.removeAll();
		for (PanelProducto p : ordenados) {
			if (clickListener != null) p.setControlador(clickListener);
			listaPanel.add(p);
		}
		listaPanel.revalidate();
		listaPanel.repaint();
	}

	/**
	 * Listener que se llama al hacer clic en una fila de producto. El ActionCommand
	 * tiene la forma "Ver producto:<nombreProducto>".
	 *
	 * @param l nuevo valor
	 */
	public void setClickListener(ActionListener l) {
		this.clickListener = l;
		// Reasignar a los paneles ya creados
		for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.setControlador(l);
		}
	}

	@Override
	public <K extends PanelProducto> PanelProducto anadirDisplay(K panelDisplay) {
		productos.add(panelDisplay);
		refrescarLista();
		return panelDisplay;
	}
}
