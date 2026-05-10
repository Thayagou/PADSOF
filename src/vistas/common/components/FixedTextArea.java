package vistas.common.components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

public class FixedTextArea extends JTextArea {
	
    private static final long serialVersionUID = 1L;

	public FixedTextArea() {
        setFocusable(false);
        setEditable(false);
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(false);
    }
	
	public FixedTextArea(String texto) {
		this();
		setText(texto);
	}
	
	public FixedTextArea(String texto, Font font) {
		this(texto);
		setFont(font);
	}
    
    @Override
    public boolean contains(int x, int y) {
        return false;
    }
    
    @Override
	public Dimension getPreferredSize() {
		Container parent = getParent();
		int width = (parent != null && parent.getWidth() > 0)
				? parent.getWidth()
				: 0;
 
		/*
		 * Para calcular el alto correcto con el ancho restringido,
		 * le decimos temporalmente al modelo cuanto mide.
		 * Usamos el setSize interno (no el del layout) solo para
		 * que el calculo del alto sea correcto.
		 */
		if (width > 0) {
			setSize(width, Short.MAX_VALUE);
		}
 
		Dimension d = super.getPreferredSize();
		d.width = width;
		return d;
	}
}
