package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;

public class ControlGestionarCategorias implements ActionListener{
	private VentanaGestionarCategorias vista;
	
	public ControlGestionarCategorias(Tienda tienda, Usuario usuario) {
		this.vista = new VentanaGestionarCategorias();
		
		new ControlPanelCrearCategoria(tienda, usuario, vista);
		
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
		for (Categoria c: categorias) {
			new ControlPanelCategoriaGestionar(tienda, usuario, c, vista);
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
