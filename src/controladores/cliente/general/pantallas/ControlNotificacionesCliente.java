package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.JPanel;

import controladores.ControladorPantalla;
import controladores.TiendaFrame;
import controladores.cliente.general.ControlPanelNotificacion;
import modelo.sistema.Tienda;
import modelo.usuario.ClienteRegistrado;
import modelo.usuario.Notificacion;
import modelo.usuario.TipoNotificacion;
import vistas.cliente.general.pantallas.VentanaNotificacionesCliente;
import vistas.common.assets.VentanaMensaje;

/**
 * Controlador de la ventana de notificaciones del cliente, gestiona la visualización y configuración de intereses.
 */
public class ControlNotificacionesCliente implements ControladorPantalla {

	/** Campo tienda. Referencia al modelo de la tienda. */
	private Tienda tienda;
	
	/** Campo vista. Ventana de notificaciones asociada a este controlador. */
	private VentanaNotificacionesCliente vista;
	
	/** Campo cliente. Cliente registrado que visualiza sus notificaciones. */
	private ClienteRegistrado cliente;

	/** Campo options. Nombres de los tipos de notificación disponibles para configuración. */
	private final String[] options = { "Pedido", "Caducidad", "Valoracion", "Intercambios" };
	
	/** Campo allTypes. Tipos de notificación correspondientes a cada opción. */
	TipoNotificacion[] allTypes = { TipoNotificacion.PEDIDO, TipoNotificacion.CADUCIDAD, TipoNotificacion.VALORACION,
			TipoNotificacion.INTERCAMBIO };

	/**
	 * Instancia un nuevo Objeto ControlNotificacionesCliente.
	 * Inicializa la vista con las notificaciones del cliente y los intereses seleccionados.
	 *
	 * @param tienda Referencia al modelo de la tienda.
	 * @param cliente Cliente registrado que visualiza sus notificaciones.
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
	 * Recarga la pantalla completa con los datos actualizados del cliente.
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
	 * Refresca la vista actual de notificaciones.
	 */
	public void refreshVista() {
		vista.refreshList();
	}

	/**
	 * Obtiene SelectedOptions.
	 * Calcula los índices de los intereses seleccionados por el cliente.
	 *
	 * @return valor de SelectedOptions, array con los índices de los tipos seleccionados.
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
	 * Guarda los cambios de configuración de intereses seleccionados por el cliente.
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
	 * Gestiona el evento de aplicar cambios en la configuración de notificaciones.
	 *
	 * @param e Evento de acción recibido.
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
	 * @return valor de Vista, el panel de la ventana de notificaciones.
	 */
	@Override
	public JPanel getVista() {
		return vista;
	}

	/**
	 * Obtiene la explicacion de la ventana.
	 *
	 * @return valor de Explicacion, descripción de la funcionalidad de notificaciones.
	 */
	@Override
	public String getExplicacion() {
		return "Aquí puedes ver tus notificaciones. Puedes marcarlas como leídas o borrarlas";
	}

}