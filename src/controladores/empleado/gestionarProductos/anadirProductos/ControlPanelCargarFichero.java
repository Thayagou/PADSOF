package controladores.empleado.gestionarProductos.anadirProductos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.exceptions.DoubleDiscountException;
import modelo.exceptions.InvalidArgumentException;
import modelo.exceptions.InvalidPermitException;
import modelo.sistema.Tienda;
import modelo.usuario.Usuario;
import vistas.common.VentanaMensaje;
import vistas.empleado.gestionarProductos.anadirProductos.PanelCargarFichero;
import vistas.empleado.gestionarProductos.anadirProductos.VentanaAnadirProductos;

public class ControlPanelCargarFichero implements ActionListener {
	private final Tienda tienda;
	private final Usuario usuario;
	private final PanelCargarFichero panel;
	
	public ControlPanelCargarFichero(Tienda tienda, Usuario usuario, VentanaAnadirProductos vista) {
		this.tienda = tienda;
		this.usuario = usuario;
		
		panel = new PanelCargarFichero();
		panel.setControlador(this);
		
		vista.anadirDisplay(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case PanelCargarFichero.CONFIRMAR_ACTION: 
			intentarCargar();
			break;
		}
	}
	
	private void intentarCargar() {
		String nombreFichero = panel.getNombreFichero();
		if(nombreFichero.length() < 1) {
			new VentanaMensaje("Seleccione un archivo válido");
		}
		
		try {
			tienda.getAlmacen().anadirProductosDeFichero(usuario, nombreFichero);
		} catch (DoubleDiscountException | InvalidArgumentException | InvalidPermitException e) {
			new VentanaMensaje(e.getMessage());
		}
	}
}
