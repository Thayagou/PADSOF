package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.VentanaConsultarEstadisticas;
import vistas.gestor.VentanaInicioGestor;

public class ControlConsultarEstadisticas implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private Gestor gestor;
	private VentanaConsultarEstadisticas vista;
	
	public ControlConsultarEstadisticas(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
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
