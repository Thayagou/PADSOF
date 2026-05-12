package vistas.common.displays;

/**
 * Interfaz que implementan aquellas ventanas que tengan paneles en ella. Permite reutilizar controladores y paneles en diferentes ventanas
 *
 * @param <D> Sublase de PanelDisplay, correspondiente con el panel añadido a la página
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
