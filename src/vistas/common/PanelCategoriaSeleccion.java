package vistas.common;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCategoriaSeleccion extends PanelDisplay {

	private static final long serialVersionUID = 1L;
	private boolean seleccionada;
	private JCheckBox checkBox;

	public PanelCategoriaSeleccion(String nombreCategoria) {
		super(0.08, 0.06, "Incluir"); // ajusta porcentajes a tu diseño

		seleccionada = false;
		
		JLabel nombreLabel = new JLabel(nombreCategoria);
		nombreLabel.setFont(Fonts.BOLD.getFont());
		nombreLabel.setForeground(ColorPalette.DARK_GREY.getColor().darker());
		add(nombreLabel, BorderLayout.WEST);
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
		checkBox.setForeground(ColorPalette.BLACK.getColor());

		// Que el checkbox no capture el click (lo gestiona el mouseListener del padre)
		checkBox.setEnabled(false);
		

		this.add(checkBox, BorderLayout.CENTER);

		// Cuando se hace click en la fila, alternar estado
		setControlador(p->toggleSeleccion());
	}

	private void toggleSeleccion() {
		seleccionada = !seleccionada;
		checkBox.setSelected(seleccionada);
	}

	public boolean isSeleccionada() {
		return seleccionada;
	}

	public String getCategoria() {
		return clickArea.getActionCommand();
	}
}
