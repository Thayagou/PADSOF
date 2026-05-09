package vistas.cliente.venta.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.assets.PanelFormulario;

/**
 * Tipo: Class VentanaPago.
 */
public class VentanaPago extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo payForum. */
	private PanelFormulario payForum;
	
	/** Constante PAY_ACTION. */
	public static final String PAY_ACTION = "Pagar";

	/**
	 * Instancia un nuevo Objeto VentanaPago.
	 */
	public VentanaPago() {
		setOpaque(false);
		setLayout(new BorderLayout());

		payForum = new PanelFormulario("Realizar pago", PAY_ACTION, "Número de tarjeta:", "Nombre del titular:",
				"Fecha de vencimiento:", "Código de seguridad:");

		add(payForum, BorderLayout.CENTER);
	}
	
	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		payForum.setControlador(c);
	}
	
	/**
	 * Obtiene NumeroTarjeta.
	 *
	 * @return valor de NumeroTarjeta
	 */
	public String getNumeroTarjeta() {
		return(payForum.getCampo(0));
	}
}
