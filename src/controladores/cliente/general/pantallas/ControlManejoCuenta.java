package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controladores.ControladorPantalla;
import controladores.noRegistrado.ControlInicioSinRegistrar;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import vistas.cliente.general.pantallas.VentanaCuentaCliente;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

public class ControlManejoCuenta implements ActionListener, ControladorPantalla {

	private Tienda tienda;
	private VentanaCuentaCliente vista;
	private ClienteRegistrado cliente;
	
	public ControlManejoCuenta(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;
		
		this.vista = new VentanaCuentaCliente();
		
		vista.setControlador(this);
		
		TiendaFrame.getInstance().navegarA(this);
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

	@Override
	public JPanel getVista() {
		return vista;
	}
}
