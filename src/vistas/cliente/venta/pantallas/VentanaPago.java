package vistas.cliente.venta.pantallas;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.assets.PanelFormulario;

public class VentanaPago extends JPanel {

	private static final long serialVersionUID = 1L;
	private PanelFormulario payForum;
	
	public static final String PAY_ACTION = "Pagar";

	public VentanaPago() {
		setOpaque(false);
		setLayout(new BorderLayout());

		payForum = new PanelFormulario("Realizar pago", PAY_ACTION, "Número de tarjeta:", "Nombre del titular:",
				"Fecha de vencimiento:", "Código de seguridad:");

		add(payForum, BorderLayout.CENTER);
	}
	
	public void setControlador(ActionListener c) {
		payForum.setControlador(c);
	}
	
	public String getNumeroTarjeta() {
		return(payForum.getCampo(0));
	}
}
