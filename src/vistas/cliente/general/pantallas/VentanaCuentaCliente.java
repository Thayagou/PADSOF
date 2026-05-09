package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFormulario;
import vistas.herramientas.*;

/**
 * Tipo: Class VentanaCuentaCliente.
 */
public class VentanaCuentaCliente extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo cerrarSesion. */
	private JButton cerrarSesion;
	
	/** Campo cambiarContrasena. */
	private PanelFormulario cambiarContrasena;

	/** Campo CS_BTN_HEIGHT. */
	private double CS_BTN_HEIGHT = 0.04;
	
	/** Campo CS_BTN_WIDTH. */
	private double CS_BTN_WIDTH = 0.12;
	
	/** Campo MARGIN_H. */
	private double MARGIN_H = 0.02;
	
	/** Campo MARGIN_V. */
	private double MARGIN_V = 0.02;
	
	/** Constante CHANGE_BTN. */
	public static final String CHANGE_BTN = "Cambiar";
	
	/** Constante LOGOUT_BTN. */
	public static final String LOGOUT_BTN = "Cerrar sesión";

	/**
	 * Instancia un nuevo Objeto VentanaCuentaCliente.
	 */
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

	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		cerrarSesion.addActionListener(c);
		cambiarContrasena.setControlador(c);
	}

	/**
	 * Obtiene ContrasenaAntigua.
	 *
	 * @return valor de ContrasenaAntigua
	 */
	public String getContrasenaAntigua() {
		return cambiarContrasena.getCampo(0);
	}

	/**
	 * Obtiene ContrasenaNueva.
	 *
	 * @return valor de ContrasenaNueva
	 */
	public String getContrasenaNueva() {
		return cambiarContrasena.getCampo(1);
	}

	/**
	 * Obtiene ConfirmacionNueva.
	 *
	 * @return valor de ConfirmacionNueva
	 */
	public String getConfirmacionNueva() {
		return cambiarContrasena.getCampo(2);
	}
}
