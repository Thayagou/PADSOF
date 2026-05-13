package vistas.common.components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTextArea;

/**
 * Área de texto no editable que se ajusta al ancho de su contenedor y calcula su altura dinámicamente.
 */
public class FixedTextArea extends JTextArea {
	
    /** Constante serialVersionUID. */
    private static final long serialVersionUID = 1L;

	/**
	 * Instancia un nuevo Objeto FixedTextArea.
	 * Configura el área de texto como no enfocable, no editable, transparente y con ajuste de línea.
	 */
	public FixedTextArea() {
        setFocusable(false);
        setEditable(false);
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(false);
    }
	
	/**
	 * Instancia un nuevo Objeto FixedTextArea con el texto especificado.
	 *
	 * @param texto Texto inicial a mostrar en el área.
	 */
	public FixedTextArea(String texto) {
		this();
		setText(texto);
	}
	
	/**
	 * Instancia un nuevo Objeto FixedTextArea con el texto y fuente especificados.
	 *
	 * @param texto Texto inicial a mostrar en el área.
	 * @param font Fuente a aplicar al texto.
	 */
	public FixedTextArea(String texto, Font font) {
		this(texto);
		setFont(font);
	}
    
    /**
     * contains.
     * Hace que el componente no detecte eventos de ratón sobre sí mismo.
     *
     * @param x Coordenada X en píxeles.
     * @param y Coordenada Y en píxeles.
     * @return true si la operación fue correcta, falso en caso contrario
     */
    @Override
    public boolean contains(int x, int y) {
        return false;
    }
    
    /**
     * Obtiene PreferredSize.
     * Calcula el tamaño preferido basándose en el ancho real del contenedor padre.
     *
     * @return valor de PreferredSize, la dimensión calculada con el ancho del padre.
     */
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