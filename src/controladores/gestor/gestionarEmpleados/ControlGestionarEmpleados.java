package controladores.gestor.gestionarEmpleados;

import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import controladores.ControladorPantalla;
import modelo.sistema.Tienda;
import modelo.usuario.Empleado;
import modelo.usuario.Gestor;
import vistas.common.app.TiendaFrame;
import vistas.gestor.gestionarEmpleados.VentanaGestionarEmpleados;

// TODO: Auto-generated Javadoc
/**
 * Tipo: Class ControlGestionarEmpleados.
 */
public class ControlGestionarEmpleados  implements ControladorPantalla{
	
	/** Campo tienda. */
	private Tienda tienda;
	
	/** Campo gestor. */
	private Gestor gestor;
	
	/** Campo vista. */
	private VentanaGestionarEmpleados vista;
	
	/**
	 * Instancia un nuevo Objeto ControlGestionarEmpleados.
	 *
	 * @param tienda parámetro tienda
	 * @param gestor parámetro gestor
	 */
	public ControlGestionarEmpleados(Tienda tienda, Gestor gestor) {
		this.tienda = tienda;
		this.gestor = gestor;
		this.vista = new VentanaGestionarEmpleados();
		
		new ControlPanelNuevoEmpleado(tienda, gestor, vista);
		
		for (Empleado e: tienda.getTodosEmpleados()) {
			new ControlPanelEmpleadoGestionar(tienda, e, vista);
		}
		
		TiendaFrame.getInstance().navegarA(this);
	}
	
	/**
	 * actionPerformed.
	 *
	 * @param e parámetro e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case VentanaGestionarEmpleados.NUEVO_EMPLEADO_ACTION:
			
		}
		
	}

	/**
	 * Obtiene Vista.
	 *
	 * @return valor de Vista
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

}
