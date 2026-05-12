package vistas.common.components;

import javax.swing.JLabel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

/**
 * Clase InvisibleCheckBox sirve para imitar una CheckBox pero más estética y con cambio de color.
 */
public class InvisibleCheckBox extends JLabel{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Texto que muestra cuando está en estado seleccionado. */
	private String labelSelected;
	
	/** Texto que muestra cuando no está en estado seleccionado. */
	private String labelUnselected;
	
	/** Color de la paleta de colores asignada al texto en estado seleccionado */
	private ColorPalette selected;
	
	/** Color de la paleta de colores asignada al texto en estado no seleccionado */
	private ColorPalette unselected;
	
	/** Indica si está o no seleccionada */
	private boolean isSelected;
	
	/**
	 * Instancia un nuevo label InvisibleCheckBox
	 *
	 * @param labelSelected Texto que muestra cuando está en estado seleccionado.
	 * @param labelUnselected Texto que muestra cuando no está en estado seleccionado.
	 * @param selected Color de la paleta de colores asignada al texto en estado seleccionado
	 * @param unselected Color de la paleta de colores asignada al texto en estado no seleccionado
	 */
	public InvisibleCheckBox(String labelSelected, String labelUnselected, ColorPalette selected, ColorPalette unselected) {
		super(labelUnselected);
		this.labelSelected = labelSelected;
		this.labelUnselected = labelUnselected;
		this.selected = selected;
		this.unselected = unselected;
		this.isSelected = false;
		setOpaque(false);
		//setFocusPainted(false);
		//setSelected(false);
		setFocusable(false);
		setFont(Fonts.BOLD.getFont());
		setForeground(unselected.getColor());
		setEnabled(true);
		
		//setSelectedIcon(null);
		
	}
	
	/**
	 * Sirve para hacer la label invisible, al evitar que se pueda pasar el ratón en ella
	 *
	 * @param x Posición en el eje x de la pantalla
	 * @param y Posición en el eje y de la pantalla
	 * @return false siempre
	 */
	@Override
    public boolean contains(int x, int y) {
        return false; 
    }
	
	/**
	 * Setter del estado de la CheckBox
	 *
	 * @param seleccionado Nuevo valor asignado
	 */
	public void setSeleccionado(boolean seleccionado) {
	    if (this.isSelected() != seleccionado) {
	        toggleSelection();
	    }
	}
	
	/**
	 * Comprueba si la CheckBox está o no seleccionada
	 *
	 * @return true si está seleccionada, falso en caso contrario
	 */
	public boolean isSelected() { return isSelected; }
    
    /**
     * Cambia el estado de la label y actualiza su texto y color
     */
    public void toggleSelection() {
    	isSelected = !isSelected;
	    
	    this.setForeground( isSelected
	        ? selected.getColor() 
	        : unselected.getColor());
	    this.setText(isSelected
	    		? labelSelected 
	    		: labelUnselected);
	}
    
    /**
     * Cambia el estado de la CheckBox solamente si el estado a seleccionar es distinto al actual
     *
     * @param select parámetro select
     */
    public void toggleSelection(boolean select) {
    	if (select == isSelected) return;
    	else toggleSelection();
    }
}
