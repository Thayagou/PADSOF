package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.TiendaFrame;
import vistas.gestor.VentanaConfigurarSistema;

public class ControlConfigurarSistema implements ActionListener{
	private Tienda tienda;
	private TiendaFrame frame;
	private VentanaConfigurarSistema vista;
	
	public ControlConfigurarSistema(Tienda tienda) {
		this.tienda = tienda;
		this.frame = TiendaFrame.getInstance();
		this.vista = new VentanaConfigurarSistema(tienda);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		
		}
		
	}

}
