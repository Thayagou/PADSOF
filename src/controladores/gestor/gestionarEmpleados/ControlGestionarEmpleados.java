package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Gestor;
import vistas.common.TiendaFrame;
import vistas.gestor.gestionarEmpleados.VentanaGestionarEmpleados;

public class ControlGestionarEmpleados  implements ControladorPantalla{
	private Tienda tienda;
	private Gestor gestor;
	private VentanaGestionarEmpleados vista;
	
	public ControlGestionarEmpleados(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.vista = new VentanaGestionarEmpleados();
		
		for (Empleado e: tienda.getTodosEmpleados()) {
			new ControlPanelEmpleadoGestionar(tienda, e, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaGestionarEmpleados.NUEVO_EMPLEADO_ACTION:
			
		}
		
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

}
