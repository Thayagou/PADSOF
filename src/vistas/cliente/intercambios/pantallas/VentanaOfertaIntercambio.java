package vistas.cliente.intercambios.pantallas;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelArticulo;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.PanelFactory;

/**
 * Pantalla que muestra dos columnas con los artículos que se ofrecen y los que se reciben en un intercambio, con botones de acción.
 */
public class VentanaOfertaIntercambio extends JPanel {
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Campo button1. Primer botón de acción (generalmente para aceptar o cancelar). */
	private JButton button1;
	
	/** Campo button2. Segundo botón de acción (generalmente para rechazar o confirmar). */
	private JButton button2;
	
	/** Campo mios. Panel con los artículos que el usuario ofrece (columna izquierda). */
	private JPanel mios;
	
	/** Campo suyos. Panel con los artículos que el usuario recibe (columna derecha). */
	private JPanel suyos;
	
	/** Constante BTN_WIDTH. Anchura de los botones como porcentaje de la pantalla. */
	private static final double BTN_WIDTH = 0.1;
	
	/** Constante BTN_HEIGHT. Altura de los botones como porcentaje de la pantalla. */
	private static final double BTN_HEIGHT = 0.07;
	
	/** Constante SPACE_BETWEEN. Espacio entre componentes como porcentaje de la pantalla. */
	private static final double SPACE_BETWEEN = 0.04;
	
	/**
	 * Instancia un nuevo Objeto VentanaOfertaIntercambio con un solo botón (modo cancelar).
	 *
	 * @param btn1 Texto y comando de acción para el botón único (generalmente "Cancelar").
	 */
	public VentanaOfertaIntercambio(String btn1) {
		this(btn1, null);
	}

	/**
	 * Instancia un nuevo Objeto VentanaOfertaIntercambio con dos botones (modo aceptar/rechazar).
	 *
	 * @param btn1 Texto y comando para el primer botón (generalmente "Aceptar").
	 * @param btn2 Texto y comando para el segundo botón (generalmente "Rechazar").
	 */
	public VentanaOfertaIntercambio(String btn1, String btn2) {
		setOpaque(false);
		setLayout(new BorderLayout());
		
		/* Panel de scrolls de objetos */
		JPanel scrolls = new JPanel(new GridLayout(1, 2));
		
		mios = new JPanel();
		mios.setLayout(new BoxLayout(mios, BoxLayout.Y_AXIS));
		mios.setBackground(ColorPalette.WHITE.getColor());
		
		suyos = new JPanel();
		suyos.setLayout(new BoxLayout(suyos, BoxLayout.Y_AXIS));
		suyos.setBackground(ColorPalette.WHITE.getColor());
		
		JScrollPane scrollMios = PanelFactory.getScroll(mios);
		JPanel panelMios = new JPanel(new BorderLayout());
		panelMios.add(scrollMios);
		
		JScrollPane scrollSuyos = PanelFactory.getScroll(suyos);
		JPanel panelSuyos = new JPanel(new BorderLayout());
		panelSuyos.add(scrollSuyos);
		
		scrolls.add(PanelFactory.getVentanaConCabecera("Darás: ", panelMios));
		scrolls.add(PanelFactory.getVentanaConCabecera("Recibirás: ", panelSuyos));
		
		add(scrolls, BorderLayout.CENTER);
		if(btn2 != null)
			add(botonAceptarRechazar(btn1, btn2), BorderLayout.SOUTH);
		else
			add(botonCancelar(btn1), BorderLayout.SOUTH);
		
		refreshLists();
	}
	
	/**
	 * botonAceptarRechazar.
	 * Construye el panel con dos botones para aceptar o rechazar una oferta.
	 *
	 * @param btn1 Texto y comando para el botón de aceptar.
	 * @param btn2 Texto y comando para el botón de rechazar.
	 * @return valor de tipo JPanel, el panel con los dos botones.
	 */
	private JPanel botonAceptarRechazar(String btn1, String btn2) {
		int spaceBetween = TiendaFrame.getInstance().getPixelsWidth(SPACE_BETWEEN);
		int btnW = TiendaFrame.getInstance().getPixelsWidth(BTN_WIDTH);
		int btnH = TiendaFrame.getInstance().getPixelsHeight(BTN_HEIGHT);

		/* Panel de botones de abajo */
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.setOpaque(true);
		btnPanel.setBackground(ColorPalette.DARK_BLUE.getColor());
		
		JPanel btnInterior = new JPanel(new GridLayout(1,2, spaceBetween, 0));
		button1 = ButtonFactory.newRoundedButton(btn1, btnH, btnW, 1);
		button1.setActionCommand(btn1);
		button2 = ButtonFactory.newRoundedButton(btn2, btnH, btnW, 1);
		button2.setActionCommand(btn2);
		btnInterior.add(button1);
		btnInterior.add(button2);
		btnInterior.setOpaque(false);
		
		btnPanel.add(btnInterior, BorderLayout.CENTER);
		
		JPanel finalP = new JPanel(new BorderLayout());
		finalP = PanelFactory.wrapVertical(PanelFactory.wrapHorizontal(btnPanel, spaceBetween), spaceBetween/3);
		finalP.setOpaque(true);
		finalP.setBackground(ColorPalette.DARK_BLUE.getColor());
		
		return finalP;
	}
	
	/**
	 * botonCancelar.
	 * Construye el panel con un solo botón para cancelar la operación.
	 *
	 * @param btn1 Texto y comando para el botón de cancelar.
	 * @return valor de tipo JPanel, el panel con el botón.
	 */
	private JPanel botonCancelar(String btn1) {
		int spaceBetween = TiendaFrame.getInstance().getPixelsWidth(SPACE_BETWEEN);
		int btnW = TiendaFrame.getInstance().getPixelsWidth(BTN_WIDTH);
		int btnH = TiendaFrame.getInstance().getPixelsHeight(BTN_HEIGHT);

		/* Panel de botones de abajo */
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.setOpaque(true);
		btnPanel.setBackground(ColorPalette.DARK_BLUE.getColor());
		
		JPanel btnInterior = new JPanel();
		button1 = ButtonFactory.newRoundedButton(btn1, btnH, btnW, 1);
		button1.setActionCommand(btn1);
		btnInterior = PanelFactory.wrapVertical(PanelFactory.wrapHorizontal(button1, spaceBetween), spaceBetween/3);
		btnInterior.setOpaque(false);
		
		btnPanel.add(btnInterior, BorderLayout.CENTER);
		
		return btnPanel;
	}
	
	/**
	 * refreshLists.
	 * Refresca la interfaz para mostrar los cambios en las listas de artículos.
	 */
	private void refreshLists() {
		mios.revalidate();
		mios.repaint();
		
		suyos.revalidate();
		suyos.repaint();
	}
	
	/**
	 * anadirMio.
	 * Añade un artículo a la columna de artículos que el usuario ofrece.
	 *
	 * @param p Panel del artículo a añadir a la columna izquierda.
	 */
	public void anadirMio(PanelArticulo p) {
		mios.add(p);
	}
	
	/**
	 * anadirSuyo.
	 * Añade un artículo a la columna de artículos que el usuario recibe.
	 *
	 * @param p Panel del artículo a añadir a la columna derecha.
	 */
	public void anadirSuyo(PanelArticulo p) {
		suyos.add(p);
	}
	
	/**
	 * Establece Controlador.
	 * Asigna el controlador a los botones de la ventana si existen.
	 *
	 * @param c controlador que manejará los eventos de los botones.
	 */
	public void setControlador(ActionListener c) {
		if(button1 != null) button1.addActionListener(c);
		if(button2 != null) button2.addActionListener(c);
	}
}