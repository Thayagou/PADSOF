package vistas.herramientas;

import java.awt.Font;

import vistas.common.TiendaFrame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public enum Fonts {
	TITLE("Arial", Font.BOLD, 0.05),
    SUBTITLE("Arial", Font.BOLD, 0.04),
    TITLE3("Arial", Font.BOLD, 0.03),
    BOLD("Arial", Font.BOLD, 0.02),
    SMALL("Arial", Font.PLAIN, 0.015),
    SMALL_BOLD("Arial", Font.BOLD, 0.015),
    LIGHT("Arial", Font.ROMAN_BASELINE, 0.02),
    TEXT("Arial", Font.PLAIN, 0.02);
	
	private Font font;
	
	private Fonts(String name, int style, double relativeSize) {
		TiendaFrame frame= TiendaFrame.getInstance();
		this.font = new Font(name, style, (int) (frame.getHeight() * relativeSize));
	}

	public Font getFont() { return this.font;}

	/**
	 * Trunca un texto para que quepa dentro de un ancho máximo en píxeles,
	 * añadiendo "..." al final si es necesario.
	 * 
	 * @param text      Texto original
	 * @param maxWidth  Ancho máximo disponible en píxeles
	 * @param font      Fuente con la que se medirá el texto
	 * @param component Componente en el que se pone el texto
	 * @return          Texto truncado (o el original si ya cabe)
	 */
	public static String truncar(String text, int maxWidth, Font font, JComponent component) {
		if (text == null || text.isEmpty()) return "";
		
	    FontMetrics fm = component.getFontMetrics(font);
	    
	    if (fm.stringWidth(text) <= maxWidth) return text;
	    
	    String ellipsis = "...";
	    int ellipsisWidth = fm.stringWidth(ellipsis);
	    if (maxWidth <= ellipsisWidth) {
	        return ellipsis;
	    }
	    
	    int available = maxWidth - ellipsisWidth;
	    int left = 0, right = text.length();
	    
	    while (left < right) {
	        int mid = (left + right + 1) / 2;
	        if (fm.stringWidth(text.substring(0, mid)) <= available) left = mid;
	        else right = mid - 1;
	    }
	    return text.substring(0, left) + ellipsis;
	}
	
	/**
	 * Configura un componente (JLabel, JButton, etc.) para que su texto se trunque dinámicamente
	 * con "..." cuando el componente se redimensione y el texto completo no quepa.
	 *
	 * @param comp          Componente que mostrará el texto (debe implementar setText)
	 * @param textoCompleto Texto original completo
	 * @param font          Fuente a usar para medir el texto
	 * @param padding       Píxeles de margen interno a restar del ancho del componente (opcional, default 0)
	 */
	@Deprecated
	public static void configurarColumnaConTextoDinamico(JPanel columna, JLabel label, String textoCompleto, Font font, int paddingX) {
	    Runnable actualizar = () -> {
	        int anchoColumna = columna.getWidth();
	        if (anchoColumna <= 0) return;
	        int anchoDisponible = anchoColumna - paddingX; // paddingX es margen interno que desees
	        String truncado = Fonts.truncar(textoCompleto, anchoDisponible, font, label);
	        if (!truncado.equals(label.getText())) {
	            label.setText(truncado);
	        }
	    };
	    
	    // Primera actualización tras el layout
	    SwingUtilities.invokeLater(actualizar);
	    
	    // Listener en la columna (no en el label)
	    columna.addComponentListener(new ComponentAdapter() {
	        @Override
	        public void componentResized(ComponentEvent e) {
	            actualizar.run();
	        }
	    });
	}
}


