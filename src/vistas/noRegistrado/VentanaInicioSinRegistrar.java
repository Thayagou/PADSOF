package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

// TODO: Auto-generated Javadoc
/**
 * Pantalla de inicio para usuarios no registrados. Muestra una lista de
 * "Productos populares" (los mejor valorados).
 */
public class VentanaInicioSinRegistrar extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	private ActionListener clickListener;

	/** Campo listaPanel. */
	private JPanel listaPanel = new JPanel();

	/**
	 * Instancia un nuevo Objeto VentanaInicioSinRegistrar.
	 *
	 * @param populares parámetro populares
	 */
	public VentanaInicioSinRegistrar(Producto[] populares) {
		setOpaque(false);
		setLayout(new BorderLayout());

		// ── Cabecera ──────────────────────────────────────────────
		JLabel cabecera = new JLabel("  Productos populares");
		cabecera.setFont(Fonts.TITLE3.getFont());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// ── Lista ─────────────────────────────────────────────────
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		JScrollPane scroll = new JScrollPane(listaPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getViewport().setBackground(ColorPalette.CARD_LIGHT.getColor());

		add(cabecera, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		
		refreshList();
	}
	
	public void anadirProductoRecomendado(String nombre, String descripcion, double puntuacionMedia, double precio, String...categorias) {
		PanelProducto p = new PanelProducto(nombre, descripcion, puntuacionMedia, precio, categorias);
		p.addClickListener(clickListener);
		listaPanel.add(p);
		refreshList();
	}
	
	private void refreshList() {
		listaPanel.revalidate();
		listaPanel.repaint();
	}

	/**
	 * Listener disparado al hacer clic en un producto de la lista. ActionCommand:
	 * "Ver producto:<nombreProducto>"
	 *
	 * @param l nuevo valor
	 */
	public void setClickListener(ActionListener l) {
		clickListener = l;
		for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.addClickListener(l);
		}
	}
}