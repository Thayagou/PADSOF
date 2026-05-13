package vistas.gestor.configurarSistema;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import vistas.common.displays.VentanaConDisplay;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Subclase de panel que se usa para mostrar por pantalla la ventana de gestionar los parámetros del sistema.
 */
public class VentanaGestionarParametrosSistema extends JPanel implements VentanaConDisplay<PanelParametroSistema> {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Panel que almacena todos los paneles PanelDisplay de la ventana de y se añade al scroll. */
	private JPanel listaParametros = new JPanel();
	
	/**
	 * Instancia una nueva ventana que incluye toda la información necesaria para actuar sobre ella.
	 */
	public VentanaGestionarParametrosSistema() {
		setLayout(new BorderLayout());
		listaParametros.setLayout(new BoxLayout(listaParametros, BoxLayout.Y_AXIS));
		listaParametros.setBackground(ColorPalette.CARD_LIGHT.getColor());

		// Crea el scroll y añade el panel con la lista de parámetro a este
		JScrollPane scroll = PanelFactory.getScroll(listaParametros);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BorderLayout());
		contenido.add(BorderLayout.CENTER, scroll);

		// Formatea la ventana para que tenga una cabecera con contenido
		add(PanelFactory.getVentanaConCabecera("      Parámetros del sistema", contenido), BorderLayout.CENTER);
	}
	
	
	/**
	 * Permite añadir nuevos paneles a la ventana dentro del panel del scroll.
	 *
	 * @param <K> clave genérica subclase del tipo de panel deseado en la ventana
	 * @param panelDisplay Panel a ser añadido
	 * @return el propio panel añadido
	 */
	@Override
	public <K extends PanelParametroSistema> PanelParametroSistema anadirDisplay(K panelDisplay) {
		listaParametros.add(panelDisplay);
		return panelDisplay;
	}
	
	/**
	 * Vacía la lista de parámetros
	 *
	 */
	public void vaciarLista() {
		listaParametros.removeAll();
	}

}
