package vistas.gestor.configurarSistema;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vistas.common.app.TiendaFrame;
import vistas.common.displays.PanelDisplay;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

// TODO: Auto-generated Javadoc
/**
 * Subclase de PanelDisplay que usamos para mostrar dentro de un scroll.
 */
public class PanelParametroSistema extends PanelDisplay{
	
	/** Constante serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Porcentaje de altura de pantalla que ocupa el panel. */
	private static double MAX_HEIGHT = 0.08;
	
	/** Porcentaje de altura del panel que ocupan los componentes. */
	private static double MAX_COMP_HEIGHT = 0.9;
	
	/** Porcentaje de altura dentro del panel que ocupa el textField */
	private static double TEXT_FIELD_H = 0.5;
	
	/** String del valor actual del parámetro */
	private String valorActual;
	
	/** JTextField que contiene el valor actual del parámetro y que incluirá el modificado introducido */
	private JTextField valor;
	
	/** Botón asociado a la acción de confirmar el cambio. */
	JButton confirmarButton;
	
	/** ActionCommand de ver la información asociada al parámetro */
	public static final String INFO_ACTION = "VerInformación";
	
	/** Botón asociado a la acción de ver la información del parámetro */
	private JButton info;
	
	/**
	 * Instancia un nuevo panel que se añadirá a una ventana y que incluye toda la información necesaria para actuar sobre este.
	 *
	 * @param nombreParametro Nombre del parámetro a configurar
	 * @param valorActual Valor actual del parámetro formateado a String
	 * @param actionName Nombre de la acción de confirmar
	 */
	public PanelParametroSistema(String nombreParametro, String valorActual, String actionName) {
		super(MAX_HEIGHT, MAX_COMP_HEIGHT * MAX_HEIGHT, "");
		
		this.valorActual = valorActual;
		
		int gap = (int)((maxHeight * TEXT_FIELD_H)/2);
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W);
		Dimension size = new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H));
		
		// Crea el label del nombre del parámetro y lo coloca en WEST
		JLabel paramLabel = ButtonFactory.newLeftAlignedLabel(nombreParametro, Fonts.BOLD);
		paramLabel.setPreferredSize(size);
		add(paramLabel, BorderLayout.WEST);
		valor = new JTextField(valorActual);
		valor.setFont(Fonts.TEXT.getFont());
		
		// Crea el panel que hace display del valor del parámetro
		JPanel valorPanel = new JPanel();
		valorPanel.setOpaque(false);
		valorPanel.setLayout(new BoxLayout(valorPanel, BoxLayout.Y_AXIS));
		valorPanel.add(Box.createVerticalStrut(gap));
		valorPanel.add(valor);
		valorPanel.add(Box.createVerticalStrut(gap));
		
		add(valorPanel, BorderLayout.CENTER);	
		
		// Botón de ver la información de la pantalla
		info = ButtonFactory.newIconButton("interrogacion.png", (int)(maxCompHeight * BOTON_PERC_H), (int)(maxCompHeight * BOTON_PERC_H));
		ButtonFactory.paintButton(info, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		info.setActionCommand(actionName + " " + INFO_ACTION);
		ButtonFactory.addMouseMecanics(info, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		ButtonFactory.addHoverInfo(info, "Ver explicación del parámetro", 0);
		
		// Crea el botón de confirmar con mecánica de hovering
		confirmarButton = ButtonFactory.newRoundedButton("Confirmar", (int)(maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		ButtonFactory.paintButton(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		confirmarButton.setActionCommand(actionName);
		
		JPanel wrapperBotones = new JPanel();
		wrapperBotones.setOpaque(false);
		wrapperBotones.setLayout(new BoxLayout(wrapperBotones, BoxLayout.X_AXIS));
		wrapperBotones.add(info);
		wrapperBotones.add(Box.createHorizontalStrut(gap));
		wrapperBotones.add(confirmarButton);
		
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(size);
		
		eastPanel.setOpaque(false);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(Box.createHorizontalStrut(gap));
		eastPanel.add(wrapperBotones);
		eastPanel.add(Box.createHorizontalStrut(gap));
		
		add(eastPanel, BorderLayout.EAST);
	}
	
	/** 
	 * Establece un nuevo valor para el parámetro
	 * 
	 * @param nuevoValor String del nuevo valor establecido
	 */
	public void setNuevoValor(String nuevoValor) {
		valorActual = nuevoValor;
	}
	
	/**
	 * Recarga el panel para actualizar la información
	 */
	public void recargarPanel() {
		valor.setText(valorActual);
		revalidate();
		repaint();
	}
	
	/**
	 * Valor introducido en el TextField
	 *
	 * @return valor introducido en el textfield
	 */
	public String getValorTextField() {return valor.getText();}
	
	/**
	 * Añade un ActionListener a todos los componentes que tengan una acción asociada.
	 *
	 * @param l Control que es añadido a los componentes
	 */
	@Override
	public void setControlador(ActionListener l) {
		if (confirmarButton != null) confirmarButton.addActionListener(l);
		if (info != null) info.addActionListener(l);
	}
	
	
	
}
