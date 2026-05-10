package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import vistas.common.displays.PanelCategoria;
import vistas.common.displays.PanelCategoriaSeleccion;
import vistas.common.displays.VentanaConDisplay;

/**
 * Clase controladora de la los paneles que permiten la selección de categorías
 */
public class ControlPanelCategoriaSeleccion  implements ActionListener {
	
	/** Categoría asociada al panel */
	private Categoria categoria;
	
	/** Panel que se muestra por pantalla y que permite su selección */
	private PanelCategoriaSeleccion panel;
	
	/** Controlador que gestiona la selección de las categorías y sus paneles asociados */
	private ControlGestionSeleccion<? super Categoria> superControl;
	
	/**
	 * Instancia un nuevo Controlador, que crea el panel asociado.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param categoria Categoría asociada al panel
	 * @param superControl Controlador que gestiona la selección de las categorías y sus paneles asociados
	 * @param vista Ventana a la que se añaden el panel creado
	 */
	public ControlPanelCategoriaSeleccion(Tienda tienda, Categoria categoria, ControlGestionSeleccion<? super Categoria> superControl, VentanaConDisplay<? super PanelCategoria> vista) {
		this.categoria = categoria;
		this.superControl = superControl;
		
		panel = new PanelCategoriaSeleccion(categoria.getNombre());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * Permite la selección y deselección de la categoría, alertando al controlador central de ello
	 * 
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaSeleccion.INCLUIR_ACTION:
			superControl.setSeleccionado(categoria, panel, !panel.isSeleccionado());
			break;
		}
	}
	
	/**
	 * Getter del panel asociado
	 *
	 * @return PanelDisplay correspondiente
	 */
	public PanelCategoriaSeleccion getPanel() { return panel; }

}
