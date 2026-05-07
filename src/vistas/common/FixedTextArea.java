package vistas.common;

import javax.swing.JTextArea;

public class FixedTextArea extends JTextArea {
	
	    private static final long serialVersionUID = 1L;

		public FixedTextArea() {
	        setFocusable(false);
	        setEditable(false);
	        setOpaque(false);
	        setLineWrap(true);
	        setWrapStyleWord(true);
	    }
		
		public FixedTextArea(String texto) {
			this();
			setText(texto);
		}
	    
	    @Override
	    public boolean contains(int x, int y) {
	        return false;
	    }
}
