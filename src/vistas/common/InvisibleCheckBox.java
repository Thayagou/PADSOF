package vistas.common;

import javax.swing.JCheckBox;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class InvisibleCheckBox extends JCheckBox{
	private static final long serialVersionUID = 1L;
	private ColorPalette selected;
	private ColorPalette unselected;
	
	public InvisibleCheckBox(String label, ColorPalette selected, ColorPalette unselected) {
		super(label);
		this.selected = selected;
		this.unselected = unselected;
		setOpaque(false);
		setFocusPainted(false);
		setSelected(false);
		setFocusable(false);
		setFont(Fonts.BOLD.getFont());
		setForeground(ColorPalette.GREY.getColor());
		setEnabled(true);
	}
	
	@Override
    public boolean contains(int x, int y) {
        return false; 
    }
    
    public void toggleSelection() {
	    this.setSelected(!this.isSelected());
	    this.setForeground(this.isSelected() 
	        ? selected.getColor() 
	        : unselected.getColor());
	}
}
