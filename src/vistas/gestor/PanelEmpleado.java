package vistas.gestor;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.venta.productos.Producto;
import vistas.common.PanelProducto;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelEmpleado extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel listaPanel;
	private static double GAP_CABECERA;

	public PanelEmpleado(Producto[] populares) {
		TiendaFrame t = TiendaFrame.getInstance();

		setOpaque(false);
		setLayout(new BorderLayout());

		ButtonFactory factory = new ButtonFactory();
		// ── Cabecera ──────────────────────────────────────────────
		JLabel cabecera = factory.newLabel("  Productos populares", Fonts.TITLE3);
		cabecera.setForeground(ColorPalette.WHITE.getColor());
		cabecera.setOpaque(true);
		cabecera.setBackground(ColorPalette.DARK_BLUE.getColor());
		int hGap = t.getPixelsHeight(GAP_CABECERA);
		int wGap = t.getPixelsWidth(GAP_CABECERA);
		
		cabecera.setBorder(BorderFactory.createEmptyBorder(hGap, wGap, hGap, wGap));

		// ── Lista ─────────────────────────────────────────────────
		listaPanel = new JPanel();
		listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
		listaPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());

		/*for (Producto p : populares) {
			listaPanel.add(new PanelProducto(p));
		}*/

		JScrollPane scroll = new JScrollPane(listaPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		scroll.getVerticalScrollBar().setUnitIncrement(16);
		scroll.getViewport().setBackground(ColorPalette.CARD_LIGHT.getColor());

		add(cabecera, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
	}
	
	public void addEmpleado(String name) {
		
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