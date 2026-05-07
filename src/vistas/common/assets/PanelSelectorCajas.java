package vistas.common.assets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.*;

/* Panel que muestra una lista de checkboxes con las categorías disponibles.
   Se puede obtener las categorías seleccionadas como un array de String. */
public class PanelSelectorCajas extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final double SCROLL_HEIGHT = 0.4; /* Altura del scroll (40% de la pantalla) */
	private static final double V_PADDING = 0.01; /* Padding vertical entre checkboxes (1% alto) */
	private static final double H_PADDING = 0.02; /* Padding horizontal (2% ancho) */

	private java.util.List<JCheckBox> checkboxes = new java.util.ArrayList<>();

	/* Constructor sin selección inicial */
	public PanelSelectorCajas(String[] nombresCategorias) {
		this(nombresCategorias, null);
	}

	/* Constructor con selección inicial por índices */
	public PanelSelectorCajas(String[] nombresCategorias, int[] indicesSeleccionados) {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorPalette.WHITE.getColor());

		JPanel panelCheckboxes = new JPanel();
		panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
		panelCheckboxes.setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();
		int vPad = t.getPixelsHeight(V_PADDING);
		int hPad = t.getPixelsWidth(H_PADDING);

		for (int i = 0; i < nombresCategorias.length; i++) {
			String cat = nombresCategorias[i];
			JCheckBox cb = new JCheckBox(cat);
			cb.setFont(Fonts.TEXT.getFont());
			cb.setOpaque(false);
			cb.setBorder(BorderFactory.createEmptyBorder(vPad, hPad, vPad, hPad));
			if (indicesSeleccionados != null && estaSeleccionado(i, indicesSeleccionados)) {
				cb.setSelected(true);
			}
			checkboxes.add(cb);
			panelCheckboxes.add(cb);
		}

		int scrollHeight = t.getPixelsHeight(SCROLL_HEIGHT);
		JScrollPane scroll = new JScrollPane(panelCheckboxes);
		scroll.setPreferredSize(new Dimension(0, scrollHeight));
		scroll.setBorder(null);
		add(scroll, BorderLayout.CENTER);
	}

	/* Comprueba si un índice está en el array de seleccionados */
	private boolean estaSeleccionado(int indice, int[] indices) {
		for (int i : indices) {
			if (i == indice)
				return true;
		}
		return false;
	}

	/* Devuelve un array con los nombres de las categorías seleccionadas */
	public String[] getCategoriasSeleccionadas() {
		return checkboxes.stream().filter(JCheckBox::isSelected).map(JCheckBox::getText).toArray(String[]::new);
	}

	/* Limpia todas las selecciones */
	public void limpiarSeleccion() {
		for (JCheckBox cb : checkboxes) {
			cb.setSelected(false);
		}
	}
}