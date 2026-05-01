package vistas.common;

import javax.swing.JCheckBox;

import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class InvisibleCheckBox extends JCheckBox{
	private static final long serialVersionUID = 1L;
	private String labelSelected;
	private String labelUnselected;
	private ColorPalette selected;
	private ColorPalette unselected;
	
	public InvisibleCheckBox(String labelSelected, String labelUnselected, ColorPalette selected, ColorPalette unselected) {
		super(labelUnselected);
		this.labelSelected = labelSelected;
		this.labelUnselected = labelUnselected;
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
	    boolean isSelected = this.isSelected();
	    this.setForeground( isSelected
	        ? selected.getColor() 
	        : unselected.getColor());
	    this.setText(isSelected
	    		? labelSelected 
	    		: labelUnselected);
	}
}
