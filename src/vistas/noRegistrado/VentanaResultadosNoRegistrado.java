package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import vistas.common.*;
import vistas.herramientas.*;

// TODO: Auto-generated Javadoc
/**
 * Muestra los resultados de una búsqueda en forma de lista scrolleable. Incluye
 * combo de ordenación (igual que maqueta 4).
 */
public class VentanaResultadosNoRegistrado extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo productos. */
	private ArrayList<PanelProducto> productos = new ArrayList<>();
	
	/** Campo listaPanel. */
	private JPanel listaPanel;
	
	/** Campo ordenCombo. */
	private JComboBox<String> ordenCombo;

	/** Campo clickListener. */
	private ActionListener clickListener; // para navegar al detalle

	/** Constante ORDENES. */
	private static final String[] ORDENES = { "Mejor valorados", "Peor valorados", "Precio: menor a mayor",
			"Precio: mayor a menor", "Nombre A-Z", "Nombre Z-A" };

	/**
	 * Instancia un nuevo Objeto VentanaResultadosNoRegistrado.
	 *
	 * @param productos parámetro productos
	 */
	public VentanaResultadosNoRegistrado() {
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// ── Cabecera ──────────────────────────────────────────────
		JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
		cabecera.setBackground(ColorPalette.BG_BLUE.getColor());

		JLabel lblResultados = new JLabel("Resultados de búsqueda");
		lblResultados.setFont(Fonts.TITLE3.getFont());
		lblResultados.setForeground(ColorPalette.WHITE.getColor());

		ordenCombo = new JComboBox<>(ORDENES);
		ordenCombo.setFont(Fonts.TEXT.getFont());
		ordenCombo.addActionListener(e -> refrescarLista());

		cabecera.add(lblResultados);
		cabecera.add(ordenCombo);

		// ── Lista ─────────────────────────────────────────────────
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = new JScrollPane(listaPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.setBackground(ColorPalette.CARD_LIGHT.getColor());
		scroll.getViewport().setBackground(ColorPalette.CARD_LIGHT.getColor());

		add(cabecera, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);

		refrescarLista();
	}
	
	public void anadirProducto(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		productos.add(new PanelProducto(nombre, descripcion, puntuacionMedia, precio, categorias));
		refrescarLista();
	}

	/**
	 * refrescarLista.
	 */
	private void refrescarLista() {
		PanelProducto[] ordenados = Arrays.copyOf(productos.toArray(new PanelProducto[0]), productos.size());
		switch (ordenCombo.getSelectedIndex()) {
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
}
