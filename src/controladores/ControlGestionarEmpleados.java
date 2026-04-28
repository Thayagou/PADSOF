package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaGestionarEmpleados;

public class ControlGestionarEmpleados  implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaGestionarEmpleados vista;
	
	public ControlGestionarEmpleados(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaGestionarEmpleados(tienda);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
