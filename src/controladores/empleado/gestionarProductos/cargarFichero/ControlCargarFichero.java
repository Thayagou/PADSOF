package controladores.empleado.gestionarProductos.cargarFichero;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarProductos.cargarFichero.VentanaCargarFichero;

public class ControlCargarFichero implements ActionListener {
	private final Tienda tienda;
	private final Usuario usuario;
	private final VentanaCargarFichero vista;
	
	public ControlCargarFichero(Tienda tienda, Usuario usuario) {
		this.tienda = tienda;
		this.usuario = usuario;
		
		this.vista = new VentanaCargarFichero();
		vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaCargarFichero.CONFIRMAR_ACTION_NAME: 
			intentarCargar();
			break;
		}
	}
	
	private void intentarCargar() {
		String nombreFichero = vista.getNombreFichero();
		
		try {
			tienda.getAlmacen().anadirProductosDeFichero(usuario, nombreFichero);
		} catch (DoubleDiscountException | InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
		}
	}
}
