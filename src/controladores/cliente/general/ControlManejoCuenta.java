package controladores.cliente.general;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.*;
import vistas.cliente.general.VentanaCuentaCliente;
import vistas.common.TiendaFrame;
import vistas.common.VentanaMensaje;

public class ControlManejoCuenta implements ActionListener {

	private Tienda tienda;
	private VentanaCuentaCliente vista;
	private ClienteRegistrado cliente;
	
	public ControlManejoCuenta(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		this.vista = new VentanaCuentaCliente();
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "Cambiar":
			try{
				cliente.cambiarContrasena(vista.getContrasenaAntigua(), vista.getContrasenaNueva(), vista.getConfirmacionNueva());
			} catch (Exception ex) {
				new VentanaMensaje(ex.getMessage());
			}
			break;
		case "Cerrar Sesión":
			SwingUtilities.invokeLater(() -> new ControlInicioSinRegistrar(tienda));
		}
	}
}
