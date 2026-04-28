package vistas.noRegistrado;

import javax.swing.*;

import controladores.noRegistrado.ControlBarraNoRegistrado;
import controladores.noRegistrado.ControlLogin;
import vistas.*;

import java.awt.*;

public class VentanaLogin extends FondoNoRegistrado {

	private static final long serialVersionUID = 1L;
	private JTextField usuarioField;
	private JPasswordField passField;
	private JButton botonEntrar;

	public VentanaLogin(ControlBarraNoRegistrado ctrlBarra) {
		super();

		TiendaFrame t = TiendaFrame.getInstance();
		
	    usuarioField = new JTextField(15);
	    usuarioField.setFont(Fonts.TEXT.getFont());
	    passField = new JPasswordField(15);
	    passField.setFont(Fonts.TEXT.getFont());
	    botonEntrar = new ButtonFactory().newButton("Log In");

	    JPanel card = new RoundedPanel(20);
	    card.setBackground(ColorPalette.WHITE.getColor());
	    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

	    JLabel title = new JLabel("Iniciar sesión", JLabel.CENTER);
	    title.setFont(t.getTitle3Font());
	    title.setForeground(ColorPalette.WHITE.getColor());
	    title.setOpaque(true);
	    title.setBackground(ColorPalette.BG_BLUE.getColor());
	    title.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));
	    title.setAlignmentX(CENTER_ALIGNMENT);

	    botonEntrar.setAlignmentX(CENTER_ALIGNMENT);

	    card.add(title);
	    card.add(Box.createVerticalStrut(12));
	    card.add(labeledField("Nombre de Usuario:", usuarioField, card));
	    card.add(labeledField("Contraseña:", passField, card));
	    card.add(Box.createVerticalStrut(12));
	    card.add(botonEntrar);
	    card.add(Box.createVerticalStrut(16));

	    JPanel wrapper = new JPanel(new GridBagLayout());
	    wrapper.setOpaque(false);
	    wrapper.add(card);
	    add(wrapper, BorderLayout.CENTER);
		
		initBarra(ctrlBarra);
		
	}
	
	private JPanel labeledField(String texto, JComponent campo, JPanel card) {
	    JPanel fila = new JPanel();
	    fila.setLayout(new BoxLayout(fila, BoxLayout.Y_AXIS));
	    fila.setOpaque(false);
	    fila.setBorder(BorderFactory.createEmptyBorder(4, 24, 4, 24));
	    fila.add(new JLabel(texto));
	    fila.add(campo);
	    return fila;
	}
	
	public void setControlador(ControlLogin c) {
		botonEntrar.addActionListener(c);
	}
	
	public String getNombreUsuario() {
		return usuarioField.getText();
	}
	
	public char[] getPassword() {
		return passField.getPassword();
	}
}
