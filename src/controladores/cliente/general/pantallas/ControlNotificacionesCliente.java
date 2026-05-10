package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.cliente.general.ControlPanelNotificacion;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Notificacion;
import modelo.usuario.TipoNotificacion;
import vistas.cliente.general.pantallas.VentanaNotificacionesCliente;
import vistas.common.app.TiendaFrame;
import vistas.common.assets.VentanaMensaje;

/**
 * Tipo: Class ControlNotificacionesCliente.
 */
public class ControlNotificacionesCliente implements ControladorPantalla {

	/** Campo tienda. */
	private Tienda tienda;
	
	/** Campo vista. */
	private VentanaNotificacionesCliente vista;
	
	/** Campo cliente. */
	private ClienteRegistrado cliente;

	/** Campo options. */
	private final String[] options = { "Pedido", "Caducidad", "Valoracion", "Intercambios" };
	
	/** Campo allTypes. */
	TipoNotificacion[] allTypes = { TipoNotificacion.PEDIDO, TipoNotificacion.CADUCIDAD, TipoNotificacion.VALORACION,
			TipoNotificacion.INTERCAMBIO };

	/**
	 * Instancia un nuevo Objeto ControlNotificacionesCliente.
	 *
	 * @param tienda parámetro tienda
	 * @param cliente parámetro cliente
	 */
	public ControlNotificacionesCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;

		int[] indexes = getSelectedOptions();

		this.vista = new VentanaNotificacionesCliente(options, indexes);

		Notificacion[] notificaciones = Arrays.copyOf(cliente.getNotificaciones(), cliente.getNotificaciones().length);
		for (int i = notificaciones.length - 1; i >= 0; i--) {
		    Notificacion n = notificaciones[i];
		    if (!n.isBorrada()) {
		        new ControlPanelNotificacion(tienda, cliente, n, vista, this);
		    }
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);

	}

	/**
	 * recargarPantalla.
	 */
	public void recargarPantalla() {
		int[] indexes = getSelectedOptions();

		this.vista = new VentanaNotificacionesCliente(options, indexes);

		Notificacion[] notificaciones = Arrays.copyOf(cliente.getNotificaciones(), cliente.getNotificaciones().length);
		for (int i = notificaciones.length - 1; i >= 0; i--) {
		    Notificacion n = notificaciones[i];
		    if (!n.isBorrada()) {
		        new ControlPanelNotificacion(tienda, cliente, n, vista, this);
		    }
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().recargarPantallaActual(this);
	}

	/**
	 * refreshVista.
	 */
	public void refreshVista() {
		vista.refreshList();
	}

	/**
	 * Obtiene SelectedOptions.
	 *
	 * @return valor de SelectedOptions
	 */
	private int[] getSelectedOptions() {
		TipoNotificacion[] selected = cliente.getIntereses();

		int[] indexes = new int[selected.length];
		for (int i = 0, count = 0; i < allTypes.length; i++) {
			for (int j = 0; j < selected.length; j++) {
				if (allTypes[i].equals(selected[j])) {
					indexes[count] = i;
					count++;
					break;
				}
			}
		}

		return indexes;
	}

	/**
	 * aplicarCambios.
	 */
	private void aplicarCambios() {
		String[] selected = vista.getSelectedOptions();
		TipoNotificacion[] notis = new TipoNotificacion[selected.length];
		for (int i = 0, count = 0; i < selected.length; i++) {
			for (int j = 0; j < options.length; j++) {
				if (selected[i].equals(options[j])) {
					notis[count] = allTypes[j];
					count++;
					break;
				}
			}
		}

		for (TipoNotificacion n : TipoNotificacion.values())
			cliente.quitarInteres(n);

		for (TipoNotificacion n : notis)
			cliente.anadirInteres(n);
	}

	/**
	 * actionPerformed.
	 *
	 * @param e parámetro e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaNotificacionesCliente.APPLY_BTN:
			aplicarCambios();
			new VentanaMensaje("Sus cambios se han guardado");
			break;
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

	/**
	 * Obtiene la explicacion de la ventana.
	 *
	 * @return valor de Explicacion
	 */
	@Override
	public String getExplicacion() {
		return "Aquí puedes ver tus notificaciones. Puedes marcarlas como leídas o borrarlas";
	}

}
