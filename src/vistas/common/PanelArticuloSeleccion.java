package vistas.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelArticuloSeleccion extends PanelArticulo {
	private static final long serialVersionUID = 1L;
	private InvisibleCheckBox checkBox;

	public PanelArticuloSeleccion (String nombre, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombre, descripcion, interesadoEn, estimacion, estado, actionName, categorias);

		checkBox = ButtonFactory.newInvisibleCheckBox("Incluído", "Incluir", ColorPalette.BLACK, ColorPalette.GREY);
		
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		centerPanel.setOpaque(false);
		int gap = TiendaFrame.getInstance().getPixelsWidth(HOR_GAP);
		centerPanel.add(Box.createHorizontalStrut(gap));
		centerPanel.add(checkBox);
		

		this.add(checkBox, BorderLayout.EAST);

		// Cuando se hace click en la fila, alternar estado
		setControlador(p->checkBox.toggleSelection());
	}
}
