package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.components.InvisibleCheckBox;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

public class PanelArticuloSeleccion extends PanelArticulo {
	private static final long serialVersionUID = 1L;
	private InvisibleCheckBox checkBox;

	public PanelArticuloSeleccion (String nombre, String foto, String descripcion, String interesadoEn, double estimacion, String estado, String actionName, String...categorias) {
		super(nombre, foto, descripcion, interesadoEn, estimacion, estado, actionName, categorias);

		checkBox = ButtonFactory.newInvisibleCheckBox("Incluído", "Incluir", ColorPalette.BLACK, ColorPalette.GREY);
		
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		centerPanel.setOpaque(false);
		int gap = TiendaFrame.getInstance().getPixelsWidth(HOR_GAP);
		centerPanel.add(Box.createHorizontalStrut(gap));
		centerPanel.add(checkBox);

		this.add(checkBox, BorderLayout.EAST);
	}

	public void toggleSelection() {
		checkBox.toggleSelection();
	}
	
	public boolean isSelected() {
		return checkBox.isSelected();
	}
}
