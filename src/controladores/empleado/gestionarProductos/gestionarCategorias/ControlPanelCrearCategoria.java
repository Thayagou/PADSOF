package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controladores.TiendaFrame;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarProductos.gestionarCategorias.PanelCrearCategoria;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;

/**
 * Esta clase representa el controlador del panel para crear categorías
 */
public class ControlPanelCrearCategoria implements ActionListener {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** usuario que realiza la acción */
	private final Usuario usuario;
	/** Panel que se controla */
	private final PanelCrearCategoria panel;
	/** Controlador de la ventana en la que se muestra el panel */
	private final ControlGestionarCategorias padre;
	
	/**
	 * Construcor del controlador del panel para crear categorías
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario que ejecuta la acción
	 * @param vista Ventana en la que se muestra el panel
	 * @param padre Controlador de la ventana en la que se muestra el panel
	 */
	public ControlPanelCrearCategoria(Tienda tienda, Usuario usuario, VentanaGestionarCategorias vista, ControlGestionarCategorias padre) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.padre = padre;
		
		panel = new PanelCrearCategoria();
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case PanelCrearCategoria.CONFIRMAR_ACTION:
			intentarCrear();
			break;
		}
	}
	
	/**
	 * Acción que se ejecuta el intentar crear una categoría
	 */
	private void intentarCrear() {
		String nombre = panel.getNombreCategoria();
		if(nombre.equals("Nombre") || nombre.length() < 1) {
			new VentanaMensaje("Introduzca un nombre válido para la nueva categoría", 1);
			return;
		}
		
		if(TiendaFrame.getConfirmacionUsuario("¿Estás seguro de que deseas crear esta categoría?"))
		try {
			tienda.getAlmacen().anadirCategoria(usuario, nombre);
		} catch (InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage(), 1);
			return;
		}
		padre.mostrar();
		new VentanaMensaje("La categoría se ha añadido correctamente");
	}

}
