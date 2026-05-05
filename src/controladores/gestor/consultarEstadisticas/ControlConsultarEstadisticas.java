package controladores.gestor.consultarEstadisticas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.consultarEstadisticas.VentanaConsultarEstadisticas;

public class ControlConsultarEstadisticas implements ControladorPantalla{
	private Tienda tienda;
	private Gestor gestor;
	private VentanaConsultarEstadisticas vista;
	
	public ControlConsultarEstadisticas(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.vista = new VentanaConsultarEstadisticas(tienda);
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
