package controladores.empleado.gestionarProductos.anadirProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import vistas.common.TiendaFrame;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

public class ControlAnadirProductos implements ActionListener {
	private VentanaAnadirProductos vista;
	
	public ControlAnadirProductos(Tienda tienda, Usuario usuario) {
		this.vista = new VentanaAnadirProductos();
		
		new ControlPanelCargarFichero(tienda, usuario, vista);
		
		
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
