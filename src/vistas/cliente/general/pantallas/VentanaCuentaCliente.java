package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFormulario;
import vistas.herramientas.*;

public class VentanaCuentaCliente extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton cerrarSesion;
	private PanelFormulario cambiarContrasena;

	private double CS_BTN_HEIGHT = 0.04;
	private double CS_BTN_WIDTH = 0.12;
	private double MARGIN_H = 0.02;
	private double MARGIN_V = 0.02;
	
	public static final String CHANGE_BTN = "Cambiar";
	public static final String LOGOUT_BTN = "Cerrar sesión";

	public VentanaCuentaCliente() {
		setOpaque(false);
		setLayout(new BorderLayout());

		TiendaFrame t = TiendaFrame.getInstance();

		int csH = t.getPixelsHeight(CS_BTN_HEIGHT);
		int csW = t.getPixelsWidth(CS_BTN_WIDTH);

		JPanel contenido = new JPanel();
		contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
		contenido.setOpaque(false);

		int marginH = t.getPixelsWidth(MARGIN_H);
		int marginV = t.getPixelsHeight(MARGIN_V);
		contenido.setBorder(BorderFactory.createEmptyBorder(marginV, marginH, marginV, marginH));
		
		cambiarContrasena = new PanelFormulario("Cambiar contraseña", CHANGE_BTN, new Integer[] { 1, 2, 3 },
				"Contraseña antigua", "Contraseña nueva", "Confirmar nueva");
		contenido.add(cambiarContrasena);

		cerrarSesion = ButtonFactory.newRoundedButton(LOGOUT_BTN, csH, csW, 1);
		cerrarSesion.setMaximumSize(new Dimension(csW, csH));
		cerrarSesion.setAlignmentX(Component.CENTER_ALIGNMENT);
		contenido.add(cerrarSesion);
		contenido.add(Box.createVerticalStrut(marginH));

		this.add(PanelFactory.getVentanaConCabecera("Información de la cuenta", contenido));
	}

	public void setControlador(ActionListener c) {
		cerrarSesion.addActionListener(c);
		cambiarContrasena.setControlador(c);
	}

	public String getContrasenaAntigua() {
		return cambiarContrasena.getCampo(0);
	}

	public String getContrasenaNueva() {
		return cambiarContrasena.getCampo(1);
	}

	public String getConfirmacionNueva() {
		return cambiarContrasena.getCampo(2);
	}
}
