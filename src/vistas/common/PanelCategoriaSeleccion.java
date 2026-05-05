package vistas.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoriaSeleccion extends PanelCategoria implements PanelSeleccion{

	private static final long serialVersionUID = 1L;
	public static final String INCLUIR_ACTION= "Incluir";
	private InvisibleCheckBox checkBox;

	public PanelCategoriaSeleccion(String nombreCategoria) {
		super(nombreCategoria, INCLUIR_ACTION); // ajusta porcentajes a tu diseño

		// CheckBox como display (no interactivo por sí solo)
		checkBox = ButtonFactory.newInvisibleCheckBox("Incluído", "Incluir", ColorPalette.BLACK, ColorPalette.GREY);

		this.add(checkBox, BorderLayout.EAST);

		// Cuando se hace click en la fila, alternar estado
	}

	@Override
	public boolean isSeleccionado() {
		return checkBox.isSelected();
	}
	
	@Override
	public void toggleCheckBox() {
		checkBox.toggleSelection();
	}
}
