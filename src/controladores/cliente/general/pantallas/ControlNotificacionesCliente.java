package controladores.cliente.general.pantallas;

import java.awt.event.ActionEvent;

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

public class ControlNotificacionesCliente implements ControladorPantalla {

	private Tienda tienda;
	private VentanaNotificacionesCliente vista;
	private ClienteRegistrado cliente;

	private final String[] options = { "Pedido", "Caducidad", "Valoracion", "Intercambios" };
	TipoNotificacion[] allTypes = { TipoNotificacion.PEDIDO, TipoNotificacion.CADUCIDAD, TipoNotificacion.VALORACION,
			TipoNotificacion.INTERCAMBIO };

	public ControlNotificacionesCliente(Tienda tienda, ClienteRegistrado cliente) {
		this.tienda = tienda;
		this.cliente = cliente;

		int[] indexes = getSelectedOptions();

		this.vista = new VentanaNotificacionesCliente(options, indexes);

		for (Notificacion n : cliente.getNotificaciones()) {
			if (!n.isBorrada()) {
				new ControlPanelNotificacion(tienda, cliente, n, vista, this);
			}
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().navegarA(this);

	}

	public void recargarPantalla() {
		int[] indexes = getSelectedOptions();

		this.vista = new VentanaNotificacionesCliente(options, indexes);

		for (Notificacion n : cliente.getNotificaciones()) {
			if (!n.isBorrada()) {
				new ControlPanelNotificacion(tienda, cliente, n, vista, this);
			}
		}

		vista.setControlador(this);

		TiendaFrame.getInstance().recargarPantallaActual(this);
	}

	public void refreshVista() {
		vista.refreshList();
	}

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

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case VentanaNotificacionesCliente.APPLY_BTN:
			aplicarCambios();
			new VentanaMensaje("Sus cambios se han guardado");
			break;
		}
	}

	@Override
	public JPanel getVista() {
		return vista;
	}

	@Override
	public String getExplicacion() {
		return "Aquí puedes ver tus notificaciones. Puedes marcarlas como leídas o borrarlas";
	}

}
