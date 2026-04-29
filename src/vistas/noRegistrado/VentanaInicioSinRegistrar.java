package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ColorPalette;

/**
 * Pantalla de inicio para usuarios no registrados. Muestra una lista de
 * "Productos populares" (los mejor valorados).
 */
public class VentanaInicioSinRegistrar extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel listaPanel;

	public VentanaInicioSinRegistrar(Producto[] populares) {
		TiendaFrame t = TiendaFrame.getInstance();

		setOpaque(false);
		setLayout(new BorderLayout());

		// ── Cabecera ──────────────────────────────────────────────
		JLabel cabecera = new JLabel("  Productos populares");
		cabecera.setFont(t.getTitle3Font());
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		cabecera.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		// ── Lista ─────────────────────────────────────────────────
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		for (Producto p : populares) {
			listaPanel.add(new PanelProducto(p));
		}

		JScrollPane scroll = new JScrollPane(listaPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getViewport().setBackground(ColorPalette.CARD_LIGHT.getColor());

		add(cabecera, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}

	/**
	 * Listener disparado al hacer clic en un producto de la lista. ActionCommand:
	 * "Ver producto:<nombreProducto>"
	 */
	public void setClickListener(ActionListener l) {
		for (Component c : listaPanel.getComponents()) {
			if (c instanceof PanelProducto pp)
				pp.addClickListener(l);
		}
	}
}