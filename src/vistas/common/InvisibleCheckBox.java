package vistas.common;

import javax.swing.JLabel;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class InvisibleCheckBox extends JLabel{
	private static final long serialVersionUID = 1L;
	private String labelSelected;
	private String labelUnselected;
	private ColorPalette selected;
	private ColorPalette unselected;
	private boolean isSelected;
	
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
	
	@Override
    public boolean contains(int x, int y) {
        return false; 
    }
	
	public void setSeleccionado(boolean seleccionado) {
	    if (this.isSelected() != seleccionado) {
	        toggleSelection();
	    }
	}
	public boolean isSelected() { return isSelected; }
    
    public void toggleSelection() {
    	isSelected = !isSelected;
	    
	    this.setForeground( isSelected
	        ? selected.getColor() 
	        : unselected.getColor());
	    this.setText(isSelected
	    		? labelSelected 
	    		: labelUnselected);
	}
    
    public void toggleSelection(boolean select) {
    	if (select == isSelected) return;
    	else toggleSelection();
    }
}
