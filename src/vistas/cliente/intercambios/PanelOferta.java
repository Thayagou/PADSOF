package vistas.cliente.intercambios;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.*;

public class PanelOferta extends PanelDisplay {

	private static final long serialVersionUID = 1L;

	/* Proporciones del panel heredadas de PanelDisplay */
	private static final double FOTO_H_PERC    = 0.99;
	private static final double FOTO_W_PERC    = 0.09;
	private static final double MAX_HEIGHT     = 0.16;

	/* Espacio vertical entre las 4 filas del GridLayout */
	private static final double ROW_GAP        = 0.005;

	/* Espacio horizontal entre el avatar y el texto de la fila 1 */
	private static final double AVATAR_GAP     = 0.008;

	/* Ancho maximo disponible para el texto del centro (aprox.) */
	private static final double CENTER_TEXT_W  = 0.50;

	/* Parametros del boton "Ver oferta" */
	private static final double BTN_WIDTH      = 0.08;
	private static final double BTN_WRAP_GAP   = 0.03;
	private static final double BTN_ROUNDNESS  = 1.0;

	private JButton botonSuperior;
	private JButton botonInferior;

	/**
	 * @param usuario    Nombre del usuario que hace la oferta.
	 * @param fotoPerfil Nombre del fichero de imagen de perfil.
	 * @param foto       Nombre del fichero de imagen del objeto principal.
	 * @param pide       Array de nombres de objetos que pide a cambio.
	 * @param ofrece     Array de nombres de objetos que ofrece.
	 * @param actionName ActionCommand que se dispara al pulsar el panel.
	 * @param btn1		ActionCommand que se dispara al pulsar el boton superior.
	 * @param btn2 		ActionCommand que se dispara al pulsar el boton inferior.
	 */
	public PanelOferta(String usuario, String fotoPerfil, String foto,
			String[] pide, String[] ofrece, String actionName, String btn1, String btn2) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, foto, actionName);

		TiendaFrame t = TiendaFrame.getInstance();

		int rowGap    = t.getPixelsHeight(ROW_GAP);
		int avatarGap = t.getPixelsWidth(AVATAR_GAP);
		int textMaxW  = t.getPixelsWidth(CENTER_TEXT_W);
		int btnWidth  = t.getPixelsWidth(BTN_WIDTH);
		int btnHeight = maxCompHeight;
		int wrapGap   = t.getPixelsHeight(BTN_WRAP_GAP);

		this.add(buildCentro(t, usuario, fotoPerfil, pide, ofrece, rowGap, avatarGap, textMaxW), BorderLayout.CENTER);
		this.add(buildEast(btn1, btn2, btnHeight, btnWidth, wrapGap), BorderLayout.EAST);
	}
	
	public PanelOferta(String usuario, String fotoPerfil, String foto,
			String[] pide, String[] ofrece, String actionName, String btn1) {
		super(MAX_HEIGHT, FOTO_H_PERC * MAX_HEIGHT, FOTO_W_PERC, foto, actionName);

		TiendaFrame t = TiendaFrame.getInstance();

		int rowGap    = t.getPixelsHeight(ROW_GAP);
		int avatarGap = t.getPixelsWidth(AVATAR_GAP);
		int textMaxW  = t.getPixelsWidth(CENTER_TEXT_W);
		int btnWidth  = t.getPixelsWidth(BTN_WIDTH);
		int btnHeight = maxCompHeight;
		int wrapGap   = t.getPixelsHeight(BTN_WRAP_GAP);

		this.add(buildCentro(t, null, fotoPerfil, pide, ofrece, rowGap, avatarGap, textMaxW), BorderLayout.CENTER);
		this.add(buildEast(btn1, btnHeight, btnWidth, wrapGap), BorderLayout.EAST);
	}

	/* Construye el panel central con las 4 filas. */
	private JPanel buildCentro(TiendaFrame t, String usuario, String fotoPerfil,
			String[] pide, String[] ofrece,
			int rowGap, int avatarGap, int textMaxW) {

		JPanel centro = new JPanel(new GridLayout(4, 1, 0, rowGap));
		centro.setOpaque(false);

		centro.add(buildFilaUsuario(t, usuario, fotoPerfil, ofrece, avatarGap, textMaxW));
		centro.add(buildFilaTexto(String.join(", ", ofrece), textMaxW, Fonts.TEXT));
		if(usuario != null) centro.add(buildFilaTexto("Pide a cambio:", textMaxW, Fonts.BOLD));
		else centro.add(buildFilaTexto("Pides a cambio:", textMaxW, Fonts.BOLD));
		centro.add(buildFilaTexto(String.join(", ", pide), textMaxW, Fonts.TEXT));

		return centro;
	}

	/*
	 * Fila 1: avatar a la izquierda (WEST), texto "Usuario ofrece:" en el centro.
	 * Usa BorderLayout segun la especificacion.
	 */
	private JPanel buildFilaUsuario(TiendaFrame t, String usuario, String fotoPerfil,
			String[] ofrece, int avatarGap, int textMaxW) {

		JPanel fila = new JPanel(new BorderLayout());
		fila.setOpaque(false);

		/* Avatar circular */
		JPanel avatar = PanelFactory.buildAvatar();
		fila.add(avatar, BorderLayout.WEST);

		/* Espacio entre avatar y texto */
		fila.add(Box.createHorizontalStrut(avatarGap), BorderLayout.CENTER);

		/* Texto "Usuario1 ofrece:" truncado al ancho disponible */
		String textoOfrece;
		if(usuario==null) textoOfrece = "Tú ofreces:";
		else textoOfrece = usuario + " ofrece:";
		
		JLabel lblOfrece = ButtonFactory.newLabel(textoOfrece, Fonts.BOLD);
		lblOfrece.setText(Fonts.truncar(textoOfrece, textMaxW, Fonts.BOLD.getFont(), lblOfrece));

		JPanel wrapperTexto = new JPanel(new BorderLayout());
		wrapperTexto.setOpaque(false);
		wrapperTexto.add(Box.createHorizontalStrut(avatarGap), BorderLayout.WEST);
		wrapperTexto.add(lblOfrece, BorderLayout.CENTER);

		fila.add(wrapperTexto, BorderLayout.CENTER);

		return fila;
	}

	private JPanel buildFilaTexto(String texto, int textMaxW, Fonts fuente) {
		JPanel fila = new JPanel(new BorderLayout());
		fila.setOpaque(false);

		JLabel lbl = ButtonFactory.newLabel(texto, fuente);
		lbl.setText(Fonts.truncar(texto, textMaxW, fuente.getFont(), lbl));

		fila.add(lbl, BorderLayout.WEST);
		return fila;
	}

	/* Construye el boton de accion envuelto en un wrapVertical. */
	private JPanel buildEast(String btn1, String btn2, int btnHeight, int btnWidth, int wrapGap) {
		botonSuperior = ButtonFactory.newRoundedButton(btn1, btnHeight, btnWidth, BTN_ROUNDNESS);
		botonSuperior.setActionCommand(btn1);
		
		botonInferior = ButtonFactory.newRoundedButton(btn2, btnHeight, btnWidth, BTN_ROUNDNESS);
		botonInferior.setActionCommand(btn2);
		
		JPanel container = new JPanel(new GridLayout(2, 1, 0, wrapGap));
		container.setOpaque(false);
		container.add(botonSuperior);
		container.add(botonInferior);

		JPanel wrapper = PanelFactory.wrapVertical(container, wrapGap);

		return PanelFactory.wrapHorizontal(wrapper, wrapGap);
	}
	
	private JPanel buildEast(String btn1, int btnHeight, int btnWidth, int wrapGap) {
		botonSuperior = ButtonFactory.newRoundedButton(btn1, btnHeight, btnWidth, BTN_ROUNDNESS);
		botonSuperior.setActionCommand(btn1);
		
		JPanel container = new JPanel(new BorderLayout());
		container.setOpaque(false);
		container.add(botonSuperior);

		JPanel wrapper = PanelFactory.wrapVertical(container, wrapGap);

		return PanelFactory.wrapHorizontal(wrapper, wrapGap);
	}

	@Override
	public void setControlador(ActionListener l) {
		super.setControlador(l);
		if (botonSuperior != null) botonSuperior.addActionListener(l);
		if (botonInferior != null) botonInferior.addActionListener(l);
	}
}