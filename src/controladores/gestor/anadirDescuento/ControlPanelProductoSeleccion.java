package controladores.gestor.anadirDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import modelo.sistema.Tienda;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.displays.PanelCategoriaSeleccion;
import vistas.common.displays.PanelProducto;
import vistas.common.displays.PanelProductoSeleccion;
import vistas.common.displays.VentanaConDisplay;

/**
 * Clase controladora del panel correspondiente a la selección de productos
 */
public class ControlPanelProductoSeleccion implements ActionListener {
	
	/** Campo producto. */
	private Producto producto;
	
	/** Panel que se muestra por pantalla y del que se obtiene la información pertinente. */
	private PanelProductoSeleccion panel;
	
	/** Campo superControl. */
	private ControlGestionSeleccion<? super Producto> superControl;
	
	/**
	 * Instancia un nuevo Controlador, que crea la vista y todos los paneles asociados.
	 *
	 * @param tienda Tienda sobre la que se actúa y muestran datos.
	 * @param producto Producto de la tienda asociado al panel
	 * @param seleccionado Texto que se muestra cuando está seleccionado
	 * @param desSeleccionado Texto que se muestra cuando no
	 * @param superControl Controlador central que gestiona la selección general entre paneles
	 * @param vista Ventana a la que se añaden el panel creado
	 */
	public ControlPanelProductoSeleccion(Tienda tienda, Producto producto, String seleccionado, String desSeleccionado, ControlGestionSeleccion<? super Producto> superControl, VentanaConDisplay<? super PanelProducto> vista) {
		this.producto = producto;
		this.superControl = superControl;
		
		String imageName = producto.getImagen();
		if (imageName == null || imageName.isBlank()) imageName = "producto.png";
		
		ArrayList<String> categorias = new ArrayList<>();
		for(Categoria c : producto.getCategorias()) {
			categorias.add(c.getNombre());
		}
		
		panel = new PanelProductoSeleccion(producto.getNombre(), producto.getDescripcion(), imageName, producto.getPuntuacionMedia(), producto.getPrecio(), seleccionado, desSeleccionado, categorias.toArray(new String[0]));
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	/**
	 * Getter del panel asociado
	 *
	 * @return PanelDisplay correspondiente
	 */
	public PanelProductoSeleccion getPanel() { return panel; }
	
	/**
	 * Método que maneja todas las posibles acciones realizadas sobre la vista que maneja el controlador
	 * 
	 * Permite seleccionar y quitar la selección del producto, advirtiendo de ello al controlador central
	 *
	 * @param e Evento de acción lanzado por un componente Swing
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaSeleccion.INCLUIR_ACTION:
			superControl.setSeleccionado(producto, panel, !panel.isSeleccionado());
			break;
		}
	}

}
