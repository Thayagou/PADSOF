package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.VentanaGestionarEmpleados;

public class ControlGestionarEmpleados  implements ActionListener{
	private Tienda tienda;
	private Gestor gestor;
	private TiendaFrame frame;
	private VentanaGestionarEmpleados vista;
	
	public ControlGestionarEmpleados(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaGestionarEmpleados(tienda);
		
		frame.setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
