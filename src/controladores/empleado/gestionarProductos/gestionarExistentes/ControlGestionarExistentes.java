package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import modelo.sistema.Tienda;
import modelo.usuario.Permiso;
import modelo.usuario.Usuario;
import modelo.venta.productos.Stock;
import vistas.common.assets.VentanaMensaje;
import vistas.empleado.gestionarProductos.gestionarExistentes.VentanaGestionarExistentes;

/**
 * Esta clase representa el controlador de la ventana de gestionar productos existentes
 */
public class ControlGestionarExistentes implements ControladorPantalla {
	/** Modelo de la tienda sobre el que se actúa */
	private final Tienda tienda;
	/** usuario que realiza la acción */
	private final Usuario usuario;
	/** Ventana que se muestra */
	private VentanaGestionarExistentes vista;
	/** Permiso requerido para realizar esta acción */
	private static Permiso requerido = Permiso.PRODUCTOS;
	
	/**
	 * Constructor del controlador de gestionar productos existentes
	 * @param tienda Modelo de la tienda
	 * @param usuario Usuario que reliza la acción
	 */
	public ControlGestionarExistentes(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		if(!usuario.tienePermiso(requerido)) {
			new VentanaMensaje("No tiene el permiso para realizar esta acción", 1);
			return;
		}
		this.vista = new VentanaGestionarExistentes();
		
		vista.revalidate();
		vista.repaint();
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
		return "En esta ventana puedes modificar o borrar productos existentes de la tienda, pulsando sobre uno de los botones al lado de cada producto";
	}
	
	@Override
	public void mostrar() {
		vista.vaciar();
		for (Stock s : tienda.getAlmacen().getInventario()) {
			new ControlPanelProductoGestionar(tienda, usuario, s, vista, this);
		}
		vista.revalidate();
		vista.repaint();
	}

}
