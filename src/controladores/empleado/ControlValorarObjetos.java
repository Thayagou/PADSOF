package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaValorarObjetos;

public class ControlValorarObjetos implements ActionListener{
	private Tienda tienda;
	private VentanaValorarObjetos vista;

	public ControlValorarObjetos(Tienda tienda) {
		this.tienda = tienda;
		this.vista = new VentanaValorarObjetos();
		this.vista.setControlador(this);
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
