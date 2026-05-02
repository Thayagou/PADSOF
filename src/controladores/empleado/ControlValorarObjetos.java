package controladores.empleado;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.wallapop.Valoracion;
import vistas.common.TiendaFrame;
import vistas.empleado.VentanaValorarObjetos;

public class ControlValorarObjetos implements ActionListener{
	private VentanaValorarObjetos vista;

	public ControlValorarObjetos(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaValorarObjetos();
		for(Valoracion v : tienda.getHistorial().getValoracionesPendientes()) {
			new ControlPanelValorarObjetos(tienda, v.getArticulo(), empleado, vista);
		}
		
		TiendaFrame.getInstance().setVistaActual(vista);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
