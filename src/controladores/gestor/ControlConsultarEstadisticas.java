package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaConsultarEstadisticas;
import vistas.gestor.VentanaInicioGestor;

public class ControlConsultarEstadisticas implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaConsultarEstadisticas vista;
	
	public ControlConsultarEstadisticas(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaConsultarEstadisticas(tienda);
		
		frame.setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
