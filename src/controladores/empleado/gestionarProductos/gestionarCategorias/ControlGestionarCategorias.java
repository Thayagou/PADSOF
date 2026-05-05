package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.empleado.gestionarProductos.gestionarExistentes.ControlPanelProductoGestionar;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import modelo.venta.productos.Producto;
import vistas.common.PanelCategoria;
import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.gestor.gestionarEmpleados.*;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;

public class ControlGestionarCategorias implements ControladorPantalla{
	private VentanaGestionarCategorias vista;
	
	public ControlGestionarCategorias(Tienda tienda, Usuario usuario) {
		this.vista = new VentanaGestionarCategorias();
		
		new ControlPanelCrearCategoria(tienda, usuario, vista);
		
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
		for (Categoria c: categorias) {
			new ControlPanelCategoriaGestionar(tienda, usuario, c, vista);
			//vista.anadirDisplay(new PanelDisplay(0.4, 0.25));
		}
		
		/*Producto[] productos = tienda.getAlmacen().getProductosCoincidentes("");
		for (Producto p: productos) {
			new ControlPanelProductoGestionar(tienda, p, vista);
		}*/
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
