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

public class PanelParametroSistema extends PanelDisplay{
	private static final long serialVersionUID = 1L;
	private static double MAX_HEIGHT = 0.08;
	private static double MAX_COMP_HEIGHT = 0.9;
	private static double TEXT_FIELD_H = 0.5;
	
	private JTextField valor;
	JButton confirmarButton;
	
	public PanelParametroSistema(String nombreParametro, String valorActual, String actionName) {
		super(MAX_HEIGHT, MAX_COMP_HEIGHT * MAX_HEIGHT, "");
		
		int gap = (int)((maxHeight * TEXT_FIELD_H)/2);
		int maxWidth = TiendaFrame.getInstance().getPixelsWidth(BOTON_PERC_W);
		Dimension size = new Dimension(maxWidth, (int)(maxCompHeight * BOTON_PERC_H));
		
		JLabel paramLabel = ButtonFactory.newLeftAlignedLabel(nombreParametro, Fonts.BOLD);
		paramLabel.setPreferredSize(size);
		add(paramLabel, BorderLayout.WEST);
		valor = new JTextField(valorActual);
		valor.setFont(Fonts.TEXT.getFont());
		
		JPanel valorPanel = new JPanel();
		valorPanel.setOpaque(false);
		valorPanel.setLayout(new BoxLayout(valorPanel, BoxLayout.Y_AXIS));
		valorPanel.add(Box.createVerticalStrut(gap));
		valorPanel.add(valor);
		valorPanel.add(Box.createVerticalStrut(gap));
		
		add(valorPanel, BorderLayout.CENTER);	
		
		confirmarButton = ButtonFactory.newRoundedButton("Confirmar", (int)(maxCompHeight * BOTON_PERC_H), maxCompHeight, 0.75f);
		//f.newRoundedButton("Modificar información y permisos", 0,0, 0.5f);
		ButtonFactory.paintButton(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
		ButtonFactory.addMouseMecanics(confirmarButton, ColorPalette.LIGHT_PURPLE, ColorPalette.PURPLE);
		confirmarButton.setActionCommand(actionName);
		
		//modButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
		
		JPanel eastPanel = new JPanel();
		eastPanel.setPreferredSize(size);
		
		eastPanel.setOpaque(false);
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
		eastPanel.add(Box.createHorizontalStrut(gap));
		eastPanel.add(confirmarButton);
		eastPanel.add(Box.createHorizontalStrut(gap));
		
		add(eastPanel, BorderLayout.EAST);
	}
	
	public String getValorTextField() {return valor.getText();}
	
	@Override
	public void setControlador(ActionListener l) {
		if (confirmarButton != null) confirmarButton.addActionListener(l);
	}
	
	
	
}
