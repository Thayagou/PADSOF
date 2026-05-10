package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.common.components.InvisibleCheckBox;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

// TODO: Auto-generated Javadoc
/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelArticuloSeleccion extends PanelArticulo {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo checkBox. */
	private InvisibleCheckBox checkBox;

	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombre parámetro nombre
	 * @param foto parámetro foto
	 * @param descripcion parámetro descripcion
	 * @param interesadoEn parámetro interesadoEn
	 * @param estimacion parámetro estimacion
	 * @param estado parámetro estado
	 * @param actionName parámetro actionName
	 * @param categorias parámetro categorias
	 */
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

	/**
	 * toggleSelection.
	 */
	public void toggleSelection() {
		checkBox.toggleSelection();
	}
	
	/**
	 * Comprueba si es Selected.
	 *
	 * @return true si es Selected, falso en caso contrario
	 */
	public boolean isSelected() {
		return checkBox.isSelected();
	}
}
