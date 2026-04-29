package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.*;
import modelo.venta.productos.Producto;
import vistas.*;
import vistas.herramientas.ColorPalette;

/**
 * Muestra los resultados de una búsqueda en forma de lista scrolleable. Incluye
 * combo de ordenación (igual que maqueta 4).
 */
public class VentanaResultadosNoRegistrado extends JPanel {
	private static final long serialVersionUID = 1L;

	private Producto[] productos;
	private JPanel listaPanel;
	private JComboBox<String> ordenCombo;

	private ActionListener clickListener; // para navegar al detalle

	private static final String[] ORDENES = { "Mejor valorados", "Peor valorados", "Precio: menor a mayor",
			"Precio: mayor a menor", "Nombre A-Z", "Nombre Z-A" };

	public VentanaResultadosNoRegistrado(Producto[] productos) {
		this.productos = productos;

		TiendaFrame t = TiendaFrame.getInstance();

		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// ── Cabecera ──────────────────────────────────────────────
		JPanel cabecera = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 6));
		cabecera.setBackground(ColorPalette.BG_BLUE.getColor());

		JLabel lblResultados = new JLabel("Resultados de búsqueda");
		lblResultados.setFont(t.getTitle3Font());
		lblResultados.setForeground(ColorPalette.WHITE.getColor());

		ordenCombo = new JComboBox<>(ORDENES);
		ordenCombo.setFont(t.getTextFont());
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

	private void refrescarLista() {
		Producto[] ordenados = Arrays.copyOf(productos, productos.length);
		switch (ordenCombo.getSelectedIndex()) {
		case 0 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPuntuacionMedia).reversed());
		case 1 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPuntuacionMedia));
		case 2 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPrecio));
		case 3 -> Arrays.sort(ordenados, Comparator.comparingDouble(Producto::getPrecio).reversed());
		case 4 -> Arrays.sort(ordenados, Comparator.comparing(Producto::getNombre));
		case 5 -> Arrays.sort(ordenados, Comparator.comparing(Producto::getNombre).reversed());
		}

		listaPanel.removeAll();
		for (Producto p : ordenados) {
			PanelProducto fila = new PanelProducto(p);
			if (clickListener != null)
				fila.addClickListener(clickListener);
			listaPanel.add(fila);
		}
		listaPanel.revalidate();
		listaPanel.repaint();
	}

	/**
	 * Listener que se llama al hacer clic en una fila de producto. El ActionCommand
	 * tiene la forma "Ver producto:<nombreProducto>".
	 */
	public void setClickListener(ActionListener l) {
		this.clickListener = l;
		// Reasignar a los paneles ya creados
		for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.addClickListener(l);
		}
	}
}
