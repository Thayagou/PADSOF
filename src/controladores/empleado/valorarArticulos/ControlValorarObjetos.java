package controladores.empleado.valorarArticulos;

import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.wallapop.Valoracion;
import vistas.common.app.TiendaFrame;
import vistas.empleado.valorarArticulos.VentanaValorarObjetos;

public class ControlValorarObjetos implements ControladorPantalla {
	private VentanaValorarObjetos vista;

	public ControlValorarObjetos(Tienda tienda, Empleado empleado) {
		this.vista = new VentanaValorarObjetos();
		for(Valoracion v : tienda.getHistorial().getValoracionesPendientes()) {
			new ControlPanelValorarObjetos(tienda, v.getArticulo(), empleado, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public JPanel getVista() {
		return vista;
	}
}
