package vistas.common.displays;

// TODO: Auto-generated Javadoc
/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de.
 *
 * @param <D> parámetro genérico
 */
public interface VentanaConDisplay<D extends PanelDisplay> {
	
	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	public <K extends D> D anadirDisplay(K panelDisplay);
}
