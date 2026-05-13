package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.Permiso;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;

/**
 * Esta clase representa el controlador de la ventana de gestionar categorias
 */
public class ControlGestionarCategorias implements ControladorPantalla{
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** Usuario que realiza la acción */
	private final Usuario usuario;
	/** Ventana que se muestra */
	private VentanaGestionarCategorias vista;
	/** Permiso requerido para realizar esta acción */
	private static Permiso requerido = Permiso.PRODUCTOS;
	
	/**
	 * Constructor del controlador de gestionar categorías
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario que realiza la acción
	 */
	public ControlGestionarCategorias(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		if(!usuario.tienePermiso(requerido)) {
			new VentanaMensaje("No tiene el permiso para realizar esta acción", 1);
			return;
		}
		this.vista = new VentanaGestionarCategorias();
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "En esta ventana puedes crear una nueva categoría pulsando el botón \" Crear nueva categoría\", o modificar o borrar una existente pulsando los botones al lado de cada categoría";
	}
	
	@Override
	public void mostrar() {
		vista.vaciar();
		new ControlPanelCrearCategoria(tienda, usuario, vista, this);
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
		for (Categoria c: categorias) {
			new ControlPanelCategoriaGestionar(tienda, usuario, c, vista, this);
		}
	}
}
