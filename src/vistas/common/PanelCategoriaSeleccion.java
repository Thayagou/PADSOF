package vistas.common;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoriaSeleccion extends PanelCategoria {

	private static final long serialVersionUID = 1L;
	public static final String INCLUIR_ACTION= "Incluir";
	private InvisibleCheckBox checkBox;

	public PanelCategoriaSeleccion(String nombreCategoria) {
		super(nombreCategoria, INCLUIR_ACTION); // ajusta porcentajes a tu diseño

		// CheckBox como display (no interactivo por sí solo)
		checkBox = ButtonFactory.newInvisibleCheckBox("Incluído", "Incluir", ColorPalette.BLACK, ColorPalette.GREY);
		
		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		centerPanel.setOpaque(false);
		int gap = TiendaFrame.getInstance().getPixelsWidth(HOR_GAP);
		centerPanel.add(Box.createHorizontalStrut(gap));
		centerPanel.add(checkBox);
		

		this.add(centerPanel, BorderLayout.CENTER);

		// Cuando se hace click en la fila, alternar estado
	}

	public boolean isSeleccionada() {
		return checkBox.isSelected();
	}
	
	public void toggleCheckBox() {
		checkBox.toggleSelection();
	}

	public String getCategoria() {
		return clickArea.getActionCommand();
	}
}
