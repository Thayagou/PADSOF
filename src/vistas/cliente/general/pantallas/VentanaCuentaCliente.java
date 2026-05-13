package vistas.cliente.general.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelFormulario;
import vistas.herramientas.*;

/**
 * Panel que permite al cliente ver y modificar los datos de su cuenta.
 */
public class VentanaCuentaCliente extends JPanel {

	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Campo cerrarSesion. Botón para cerrar la sesión del usuario. */
	private JButton cerrarSesion;
	
	/** Campo cambiarContrasena. Formulario para cambiar la contraseña del usuario. */
	private PanelFormulario cambiarContrasena;

	/** Campo CS_BTN_HEIGHT. Altura del botón de cerrar sesión como porcentaje de la pantalla. */
	private double CS_BTN_HEIGHT = 0.04;
	
	/** Campo CS_BTN_WIDTH. Anchura del botón de cerrar sesión como porcentaje de la pantalla. */
	private double CS_BTN_WIDTH = 0.12;
	
	/** Campo MARGIN_H. Margen horizontal del contenido como porcentaje de la anchura de la pantalla. */
	private double MARGIN_H = 0.02;
	
	/** Campo MARGIN_V. Margen vertical del contenido como porcentaje de la altura de la pantalla. */
	private double MARGIN_V = 0.02;
	
	/** Constante CHANGE_BTN. Comando de acción para el botón de cambio de contraseña. */
	public static final String CHANGE_BTN = "Cambiar";
	
	/** Constante LOGOUT_BTN. Comando de acción para el botón de cierre de sesión. */
	public static final String LOGOUT_BTN = "Cerrar sesión";

	/**
	 * Instancia un nuevo Objeto VentanaCuentaCliente.
	 * Construye la interfaz con el formulario de cambio de contraseña y el botón de cierre de sesión.
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
	 * @param c controlador que manejará los eventos de los botones del formulario y cierre de sesión.
	 */
	public void setControlador(ActionListener c) {
		cerrarSesion.addActionListener(c);
		cambiarContrasena.setControlador(c);
	}

	/**
	 * Obtiene ContrasenaAntigua.
	 *
	 * @return valor de ContrasenaAntigua, la contraseña actual del usuario ingresada en el formulario.
	 */
	public String getContrasenaAntigua() {
		return cambiarContrasena.getCampo(0);
	}

	/**
	 * Obtiene ContrasenaNueva.
	 *
	 * @return valor de ContrasenaNueva, la nueva contraseña ingresada por el usuario.
	 */
	public String getContrasenaNueva() {
		return cambiarContrasena.getCampo(1);
	}

	/**
	 * Obtiene ConfirmacionNueva.
	 *
	 * @return valor de ConfirmacionNueva, la confirmación de la nueva contraseña ingresada.
	 */
	public String getConfirmacionNueva() {
		return cambiarContrasena.getCampo(2);
	}
}