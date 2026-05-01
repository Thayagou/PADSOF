package vistas.common;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoriaSeleccion extends PanelCategoria {

	private static final long serialVersionUID = 1L;
	private boolean seleccionada;
	private JCheckBox checkBox;

	public PanelCategoriaSeleccion(String nombreCategoria) {
		super(nombreCategoria, "Incluir"); // ajusta porcentajes a tu diseño

		seleccionada = false;
		// CheckBox como display (no interactivo por sí solo)
		checkBox = new JCheckBox("Incluida") {
		    @Override
		    public boolean contains(int x, int y) {
		        return false; // nunca captura eventos de ratón
		    }
		};
		checkBox.setOpaque(false);
		checkBox.setFocusPainted(false);
		checkBox.setSelected(false);
		checkBox.setFocusable(false);
		checkBox.setFont(Fonts.BOLD.getFont());
		checkBox.setForeground(ColorPalette.GREY.getColor());

		checkBox.setEnabled(true);
		

		this.add(checkBox, BorderLayout.CENTER);

		// Cuando se hace click en la fila, alternar estado
		setControlador(p->toggleSeleccion());
	}

	/*private void toggleSeleccion() {
		seleccionada = !seleccionada;
		checkBox.setSelected(seleccionada);
	}*/
	
	private void toggleSeleccion() {
	    seleccionada = !seleccionada;
	    checkBox.setSelected(seleccionada);
	    checkBox.setForeground(seleccionada 
	        ? ColorPalette.BLACK.getColor() 
	        : ColorPalette.GREY.getColor());
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public String getCategoria() {
		return clickArea.getActionCommand();
	}
}
