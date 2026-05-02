package controladores.gestor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.VentanaConfigurarSistema;

public class ControlConfigurarSistema implements ActionListener{
	private Tienda tienda;
	private Gestor gestor;
	private TiendaFrame frame;
	private VentanaConfigurarSistema vista;
	
	public ControlConfigurarSistema(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		this.gestor = gestor;
		this.vista = new VentanaConfigurarSistema(tienda);
		frame.setVistaActual(vista);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
