package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Producto;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarProductos.VentanaGestionarExistentes;

public class ControlGestionarExistentes implements ControladorPantalla{
	private Tienda tienda;
	private Usuario usuario;
	private VentanaGestionarExistentes vista;
	
	public ControlGestionarExistentes(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		this.vista = new VentanaGestionarExistentes();
		
		Producto[] productos = tienda.getAlmacen().getProductosCoincidentes("");
		
		for (Producto p: productos) {
			new ControlPanelProductoGestionar(tienda, p, vista);
		}
		
		vista.revalidate();
		vista.repaint();
		
		 TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
