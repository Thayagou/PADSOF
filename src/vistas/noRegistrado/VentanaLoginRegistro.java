package vistas.noRegistrado;

import java.awt.*;
import javax.swing.*;
import controladores.noRegistrado.ControlLogin;
import controladores.noRegistrado.ControlRegistrarse;
import vistas.*;

/**
 * Pantalla de autenticación: dos RoundedPanel lado a lado, igual que la maqueta
 * — izquierda Login, derecha Registro.
 */
public class VentanaLoginRegistro extends JPanel {
	private static final long serialVersionUID = 1L;

	// ── Login ──
	private JTextField loginUsuarioField;
	private JPasswordField loginPassField;
	private JButton botonLogin;

	// ── Registro ──
	private JTextField regUsuarioField;
	private JPasswordField regPassField;
	private JPasswordField regConfirmField;
	private JButton botonRegistrar;

	public VentanaLoginRegistro() {
		setOpaque(false);
		setLayout(new GridBagLayout());

		TiendaFrame t = TiendaFrame.getInstance();

		JPanel contenedor = new JPanel(new GridLayout(1, 2, t.getPixelsWidth(0.04), 0));
		contenedor.setOpaque(false);
		contenedor.setBorder(BorderFactory.createEmptyBorder(t.getPixelsHeight(0.06), t.getPixelsWidth(0.05),
				t.getPixelsHeight(0.06), t.getPixelsWidth(0.05)));

		contenedor.add(buildPanelLogin(t));
		contenedor.add(buildPanelRegistro(t));

		add(contenedor);
	}

	// ──────────────────────────────────────────────
	// Panel izquierdo — Iniciar sesión
	// ──────────────────────────────────────────────
	private JPanel buildPanelLogin(TiendaFrame t) {
		loginUsuarioField = new JTextField(15);
		loginUsuarioField.setFont(t.getTextFont());
		loginPassField = new JPasswordField(15);
		loginPassField.setFont(t.getTextFont());
		botonLogin = new ButtonFactory().newButton("Log In");
		botonLogin.setBackground(ColorPalette.PURPLE.getColor());
		botonLogin.setForeground(ColorPalette.WHITE.getColor());
		botonLogin.setOpaque(true);
		botonLogin.setBorderPainted(false);
		botonLogin.setActionCommand("Log In");
		new ButtonFactory().addMouseMecanics(botonLogin, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

		return buildCard(t, "Iniciar sesión con usuario", new String[] { "Nombre de Usuario:", "Contraseña:" },
				new JComponent[] { loginUsuarioField, loginPassField }, botonLogin);
	}

	// ──────────────────────────────────────────────
	// Panel derecho — Registrar nuevo usuario
	// ──────────────────────────────────────────────
	private JPanel buildPanelRegistro(TiendaFrame t) {
		regUsuarioField = new JTextField(15);
		regUsuarioField.setFont(t.getTextFont());
		regPassField = new JPasswordField(15);
		regPassField.setFont(t.getTextFont());
		regConfirmField = new JPasswordField(15);
		regConfirmField.setFont(t.getTextFont());
		botonRegistrar = new ButtonFactory().newButton("Sign In");
		botonRegistrar.setBackground(ColorPalette.PURPLE.getColor());
		botonRegistrar.setForeground(ColorPalette.WHITE.getColor());
		botonRegistrar.setOpaque(true);
		botonRegistrar.setBorderPainted(false);
		botonRegistrar.setActionCommand("Crear cuenta");
		new ButtonFactory().addMouseMecanics(botonRegistrar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);

		return buildCard(t, "Registrar nuevo usuario",
				new String[] { "Nombre de Usuario:", "Contraseña:", "Confirmar contraseña:" },
				new JComponent[] { regUsuarioField, regPassField, regConfirmField }, botonRegistrar);
	}

	// ──────────────────────────────────────────────
	// Constructor genérico de tarjeta redondeada
	// ──────────────────────────────────────────────
	private JPanel buildCard(TiendaFrame t, String titulo, String[] labels, JComponent[] fields, JButton boton) {
		RoundedPanel card = new RoundedPanel(20);
		card.setBackground(ColorPalette.WHITE.getColor());
		card.setLayout(new BorderLayout());

		// — Cabecera de color —
		JLabel header = new JLabel(titulo, JLabel.CENTER);
		header.setFont(t.getTitle3Font());
		header.setForeground(ColorPalette.WHITE.getColor());
		header.setOpaque(true);
		header.setBackground(ColorPalette.BG_BLUE.getColor());
		header.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));

		// — Cuerpo con campos —
		JPanel body = new JPanel();
		body.setOpaque(false);
		body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
		body.setBorder(BorderFactory.createEmptyBorder(16, 24, 8, 24));

		for (int i = 0; i < labels.length; i++) {
			JLabel lbl = new JLabel(labels[i]);
			lbl.setFont(t.getTextFont());
			lbl.setAlignmentX(LEFT_ALIGNMENT);
			fields[i].setAlignmentX(LEFT_ALIGNMENT);
			fields[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, fields[i].getPreferredSize().height));
			body.add(lbl);
			body.add(Box.createVerticalStrut(4));
			body.add(fields[i]);
			body.add(Box.createVerticalStrut(12));
		}

		// — Botón centrado —
		boton.setAlignmentX(CENTER_ALIGNMENT);
		body.add(Box.createVerticalStrut(4));
		body.add(boton);
		body.add(Box.createVerticalStrut(16));

		card.add(header, BorderLayout.NORTH);
		card.add(body, BorderLayout.CENTER);

		return card;
	}

	// ──────────────────────────────────────────────
	// Asignar controladores
	// ──────────────────────────────────────────────
	public void setControladorLogin(ControlLogin c) {
		botonLogin.addActionListener(c);
	}

	public void setControladorRegistro(ControlRegistrarse c) {
		botonRegistrar.addActionListener(c);
	}

	// ──────────────────────────────────────────────
	// Getters para los controladores
	// ──────────────────────────────────────────────
	public String getLoginUsuario() {
		return loginUsuarioField.getText();
	}

	public char[] getLoginPassword() {
		return loginPassField.getPassword();
	}

	public String getRegUsuario() {
		return regUsuarioField.getText();
	}

	public char[] getRegPassword() {
		return regPassField.getPassword();
	}

	public char[] getRegConfirmacion() {
		return regConfirmField.getPassword();
	}
}
