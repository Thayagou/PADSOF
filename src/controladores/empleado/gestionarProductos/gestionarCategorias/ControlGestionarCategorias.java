package controladores.empleado.gestionarProductos.gestionarCategorias;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Categoria;
import vistas.common.app.TiendaFrame;
import vistas.empleado.gestionarProductos.gestionarCategorias.VentanaGestionarCategorias;

public class ControlGestionarCategorias implements ControladorPantalla{
	private VentanaGestionarCategorias vista;
	
	public ControlGestionarCategorias(Tienda tienda, Usuario usuario) {
		this.vista = new VentanaGestionarCategorias();
		
		new ControlPanelCrearCategoria(tienda, usuario, vista);
		
		Categoria[] categorias = tienda.getAlmacen().getCategorias();
		for (Categoria c: categorias) {
			new ControlPanelCategoriaGestionar(tienda, usuario, c, vista);
		}
		
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

}
