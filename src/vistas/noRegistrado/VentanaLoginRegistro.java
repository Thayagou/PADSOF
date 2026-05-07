package vistas.noRegistrado;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFormulario;
import vistas.herramientas.*;

// TODO: Auto-generated Javadoc
/**
 * Pantalla de autenticación: dos RoundedPanel lado a lado, igual que la maqueta
 * — izquierda Login, derecha Registro.
 */
public class VentanaLoginRegistro extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private PanelFormulario logInForum;
	
	private PanelFormulario signInForum;

	private double SPACING = 0.1;
	
	/**
	 * Instancia un nuevo Objeto VentanaLoginRegistro.
	 */
	public VentanaLoginRegistro() {
		setOpaque(false);
		setLayout(new BorderLayout());

		TiendaFrame t = TiendaFrame.getInstance();
		
		logInForum = new PanelFormulario("Iniciar Sesión", "Log In", new Integer[] {2}, "Nombre", "Contraseña");
		
		signInForum = new PanelFormulario("Registrarse", "Crear cuenta", new Integer[] {2, 3}, "Nombre", "Contraseña", "Confirmar contraseña");

		int spaceBetween = t.getPixelsWidth(SPACING);

		JPanel contenedor = new JPanel();
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.X_AXIS));
		contenedor.setOpaque(false);

		contenedor.add(Box.createHorizontalStrut(spaceBetween));
		contenedor.add(logInForum);
		contenedor.add(Box.createHorizontalStrut(spaceBetween));
		contenedor.add(signInForum);
		contenedor.add(Box.createHorizontalStrut(spaceBetween));

		JPanel vista = PanelFactory.getVentanaConCabecera("      Iniciar sesión / Registrarse",
				PanelFactory.wrapVertical(contenedor, spaceBetween));
		vista.setOpaque(false);

		add(vista);
	}

	/**
	 * Establece Controlador.
	 *
	 * @param c nuevo valor
	 */
	public void setControlador(ActionListener c) {
		logInForum.setControlador(c);
		signInForum.setControlador(c);
	}

	/**
	 * Obtiene LoginUsuario.
	 *
	 * @return valor de LoginUsuario
	 */
	public String getLoginUsuario() {
		return logInForum.getCampo(0);
	}

	/**
	 * Obtiene LoginPassword.
	 *
	 * @return valor de LoginPassword
	 */
	public char[] getLoginPassword() {
		return logInForum.getCampo(1).toCharArray();
	}

	/**
	 * Obtiene RegUsuario.
	 *
	 * @return valor de RegUsuario
	 */
	public String getRegUsuario() {
		return signInForum.getCampo(0);
	}

	/**
	 * Obtiene RegPassword.
	 *
	 * @return valor de RegPassword
	 */
	public char[] getRegPassword() {
		return signInForum.getCampo(1).toCharArray();
	}

	/**
	 * Obtiene RegConfirmacion.
	 *
	 * @return valor de RegConfirmacion
	 */
	public char[] getRegConfirmacion() {
		return signInForum.getCampo(2).toCharArray();
	}
}
