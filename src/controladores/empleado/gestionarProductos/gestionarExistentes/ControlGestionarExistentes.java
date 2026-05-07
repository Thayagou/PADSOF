package controladores.empleado.gestionarProductos.gestionarExistentes;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import modelo.venta.productos.Stock;
import vistas.common.app.TiendaFrame;
import vistas.empleado.gestionarProductos.gestionarExistentes.VentanaGestionarExistentes;

public class ControlGestionarExistentes implements ControladorPantalla{
	private VentanaGestionarExistentes vista;
	
	public ControlGestionarExistentes(Tienda tienda, Usuario usuario) {
		this.vista = new VentanaGestionarExistentes();
		
		for (Stock s : tienda.getAlmacen().getInventario()) {
			new ControlPanelProductoGestionar(tienda, usuario, s, vista);
		}
		
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

}
