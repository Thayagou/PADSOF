package vistas.common;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import vistas.herramientas.*;

/* Panel que muestra una lista de checkboxes con las categorías disponibles.
   Se puede obtener las categorías seleccionadas como un array de String. */
public class PanelSelectorCajas extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final double SCROLL_HEIGHT = 0.4; /* Altura del scroll (40% de la pantalla) */
	private static final double V_PADDING = 0.01; /* Padding vertical entre checkboxes (1% alto) */
	private static final double H_PADDING = 0.02; /* Padding horizontal (2% ancho) */

	private java.util.List<JCheckBox> checkboxes = new java.util.ArrayList<>();

	public PanelSelectorCajas(String[] nombresCategorias) {
		setLayout(new BorderLayout());
		setOpaque(true);
		setBackground(ColorPalette.WHITE.getColor());

		JPanel panelCheckboxes = new JPanel();
		panelCheckboxes.setLayout(new BoxLayout(panelCheckboxes, BoxLayout.Y_AXIS));
		panelCheckboxes.setOpaque(false);

		TiendaFrame t = TiendaFrame.getInstance();
		int vPad = t.getPixelsHeight(V_PADDING);
		int hPad = t.getPixelsWidth(H_PADDING);

		for (String cat : nombresCategorias) {
			JCheckBox cb = new JCheckBox(cat);
			cb.setFont(Fonts.TEXT.getFont());
			cb.setOpaque(false);
			cb.setBorder(BorderFactory.createEmptyBorder(vPad, hPad, vPad, hPad));
			checkboxes.add(cb);
			panelCheckboxes.add(cb);
		}

		int scrollHeight = t.getPixelsHeight(SCROLL_HEIGHT);
		JScrollPane scroll = new JScrollPane(panelCheckboxes);
		scroll.setPreferredSize(new Dimension(0, scrollHeight));
		scroll.setBorder(null);
		add(scroll, BorderLayout.CENTER);
	}

	/* Devuelve un array con los nombres de las categorías seleccionadas */
	public String[] getCategoriasSeleccionadas() {
		return checkboxes.stream().filter(JCheckBox::isSelected).map(JCheckBox::getText).toArray(String[]::new);
	}

	/* (Opcional) Limpia todas las selecciones */
	public void limpiarSeleccion() {
		for (JCheckBox cb : checkboxes) {
			cb.setSelected(false);
		}
	}
}