package vistas.common.displays;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JPanel;

import controladores.TiendaFrame;
import vistas.common.components.InvisibleCheckBox;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

/**
 * Subclase de PanelArticulo que usamos para mostrar los artículos a seleccionar para un intercambio dentro de un scroll.
 */
public class PanelArticuloSeleccion extends PanelArticulo {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Checkbox de selección de producto */
	private InvisibleCheckBox checkBox;
	
	/**
	 * Instancia un nuevo panel de selección de artículo que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombre Nombre del artículo
	 * @param foto Foto del artículo
	 * @param descripcion Descripción del artículo
	 * @param interesadoEn Artículos en los que el dueño pueda estar interesado
	 * @param estimacion Estimación asignada al artículo
	 * @param estado Estado asignado al artículo
	 * @param actionName Acción asociada a presionar el botón del panel
	 * @param categorias Categorías a las que pertenece el artículo
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
	 * Cambia el estado de la CheckBox guardada
	 */
	public void toggleSelection() {
		checkBox.toggleSelection();
	}
	
	/**
	 * Comprueba si la checkBox está seleccionado
	 *
	 * @return true si es Selected, falso en caso contrario
	 */
	public boolean isSelected() {
		return checkBox.isSelected();
	}
}
