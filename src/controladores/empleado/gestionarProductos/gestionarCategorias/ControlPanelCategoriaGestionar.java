package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.TiendaFrame;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.assets.VentanaMensaje;
import vistas.common.displays.PanelCategoria;
import vistas.common.displays.VentanaConDisplay;
import vistas.empleado.gestionarProductos.gestionarCategorias.PanelCategoriaGestionarCategoria;

/**
 * Esta clase representa el controlador del panel de gestionar categorías
 */
public class ControlPanelCategoriaGestionar implements ActionListener {
	/** Categoría que se gestiona en este panel */
	private final Categoria categoria;
	/** Usuario que realiza la acción */
	private final Usuario usuario;
	/** Modelo de la tienda sobre la que se actúa */
	private final Tienda tienda;
	/** Panel que se controla */
	private PanelCategoriaGestionarCategoria panel;
	/** Constrolador de la ventana en la que se encuentra en panel */
	private final ControlGestionarCategorias padre;
	
	/**
	 * Constructor del controlador de gestionar categorías
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario que realiza la acción
	 * @param categoria Categoría que se gestiona
	 * @param vista Ventana en la que se muestra el panel
	 * @param padre Controlador de la ventana en la que se muestra el panel
	 */
	public ControlPanelCategoriaGestionar(Tienda tienda, Usuario usuario, Categoria categoria, VentanaConDisplay<? super PanelCategoria> vista, ControlGestionarCategorias padre) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.categoria = categoria;
		this.padre = padre;
		
		panel = new PanelCategoriaGestionarCategoria(categoria.getNombre());
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCategoriaGestionarCategoria.BORRAR_ACTION:
			intentarBorrar();
			break;
		case PanelCategoriaGestionarCategoria.CONFIRMAR_ACTION:
			intentarModificar();
			break;
		}
	}
	
	/**
	 * Acción que se ejecuta al intentar borrar una categoría
	 */
	private void intentarBorrar() {
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas borrar esta categoría?")) {
			try {
				tienda.getAlmacen().eliminarCategoria(usuario, categoria);
			} catch (InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			padre.mostrar();
			new VentanaMensaje("La categoría se ha borrado correctamente");
		}
	}

	/**
	 * Acción que se ejecuta el intentar modificar una categoría
	 */
	private void intentarModificar() {
		String nuevoNombre = panel.getNombreCategoria();
		if(nuevoNombre.equals("Nombre") || nuevoNombre.length() < 1) {
			new VentanaMensaje("Introduza un nuevo nombre válido para la categoría", 1);
			return;
		}
		
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas modificar esta categoría?")) {
			try {
				tienda.getAlmacen().modificarCategoria(usuario, categoria, nuevoNombre);
			} catch (InvalidArgumentException | InvalidPermitException e) {
				new VentanaMensaje(e.getMessage(), 1);
				return;
			}
			padre.mostrar();
			new VentanaMensaje("La categoría se ha modificado correctamente");
		}
	}
	
}
