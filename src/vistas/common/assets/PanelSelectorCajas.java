package vistas.common.assets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;

import controladores.TiendaFrame;
import vistas.herramientas.*;

/**
 * Panel que muestra una lista de checkboxes con las categorías disponibles.
 * Se puede obtener las categorías seleccionadas como un array de String.
 */
public class PanelSelectorCajas extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Constante SCROLL_HEIGHT. Altura del área de scroll como porcentaje de la pantalla. */
	private static final double SCROLL_HEIGHT = 0.4; /* Altura del scroll (40% de la pantalla) */
	
	/** Constante V_PADDING. Padding vertical entre checkboxes como porcentaje de la altura de la pantalla. */
	private static final double V_PADDING = 0.01; /* Padding vertical entre checkboxes (1% alto) */
	
	/** Constante H_PADDING. Padding horizontal de los checkboxes como porcentaje de la anchura de la pantalla. */
	private static final double H_PADDING = 0.02; /* Padding horizontal (2% ancho) */

	/** Campo checkboxes. Lista de checkboxes con las categorías disponibles. */
	private java.util.List<JCheckBox> checkboxes = new java.util.ArrayList<>();

	/**
	 * Instancia un nuevo Objeto PanelSelectorCajas.
	 *
	 * @param nombresCategorias Array con los nombres de las categorías a mostrar.
	 */
	/* Constructor sin selección inicial */
	public PanelSelectorCajas(String[] nombresCategorias) {
		this(nombresCategorias, null);
	}

	/**
	 * Instancia un nuevo Objeto PanelSelectorCajas.
	 *
	 * @param nombresCategorias Array con los nombres de las categorías a mostrar.
	 * @param indicesSeleccionados Array con los índices de las categorías que deben aparecer seleccionadas.
	 */
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

	/**
	 * Comprueba si un índice está en el array de seleccionados.
	 *
	 * @param indice Índice a comprobar.
	 * @param indices Array de índices seleccionados.
	 * @return true si la operación fue correcta, falso en caso contrario
	 */
	private boolean estaSeleccionado(int indice, int[] indices) {
		for (int i : indices) {
			if (i == indice)
				return true;
		}
		return false;
	}

	/**
	 * Devuelve un array con los nombres de las categorías seleccionadas
	 *
	 * @return valor de CategoriasSeleccionadas, array con los nombres de las categorías marcadas.
	 */
	public String[] getCategoriasSeleccionadas() {
		return checkboxes.stream().filter(JCheckBox::isSelected).map(JCheckBox::getText).toArray(String[]::new);
	}

	/**
	 * Limpia todas las selecciones de los checkboxes.
	 */
	public void limpiarSeleccion() {
		for (JCheckBox cb : checkboxes) {
			cb.setSelected(false);
		}
	}
}