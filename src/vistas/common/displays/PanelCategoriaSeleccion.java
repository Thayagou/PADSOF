package vistas.common.displays;

import java.awt.BorderLayout;

import vistas.common.components.InvisibleCheckBox;
import vistas.common.components.PanelSeleccion;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelCategoriaSeleccion extends PanelCategoria implements PanelSeleccion{

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** ActionCommand de la acción de incluir la categoría. */
	public static final String INCLUIR_ACTION= "Incluir";
	
	/** CheckBox invisible que indica si está incluído o no la categoría */
	private InvisibleCheckBox checkBox;

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombreCategoria Nombre de la categoría
	 */
	public PanelCategoriaSeleccion(String nombreCategoria) {
		super(nombreCategoria, INCLUIR_ACTION); // ajusta porcentajes a tu diseño

		// CheckBox como display (no interactivo por sí solo)
		checkBox = ButtonFactory.newInvisibleCheckBox("Incluído", "Incluir", ColorPalette.BLACK, ColorPalette.GREY);

		this.add(checkBox, BorderLayout.EAST);

		// Cuando se hace click en la fila, alternar estado
	}

	/**
	 * Comprueba si está seleccionada.
	 *
	 * @return true si está seleccionado, falso en caso contrario
	 */
	@Override
	public boolean isSeleccionado() {
		return checkBox.isSelected();
	}
	
	/**
	 *	Cambia el estado del panel, de seleccionado a no y viceversa
	 */
	@Override
	public void toggleCheckBox() {
		checkBox.toggleSelection();
	}
}
