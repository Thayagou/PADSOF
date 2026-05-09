package vistas.common.assets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import vistas.common.app.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;

/**
 * Ventana de mensaje con dos opciones: Confirmar y Cancelar
 * 
 * @version 1.0
 */
public class VentanaConfirmacion extends VentanaMensaje {
	
	private static final long serialVersionUID = 1L;
	
	/* Dimensiones del boton Aceptar */
	private static final double BTN_W = 0.10;
	private static final double BTN_H = 0.05;

	private JButton btnConfirmar;
	private JButton btnCancelar;
	
	public static final String CONFIRM = "Confirmar";
	public static final String CANCEL = "Cancelar";

	/**
	 * Construye una ventana de mensaje con opciones Confirmar y Cancelar
	 *
	 * @param mensaje
	 */
	public VentanaConfirmacion(String mensaje) {
		super(mensaje, VentanaMensaje.AVISO, "Confirmar acción...");
		
		TiendaFrame t = TiendaFrame.getInstance();
		
		int btnW = t.getPixelsWidth(BTN_W);
		int btnH = t.getPixelsHeight(BTN_H);
		
		JPanel newBtnPanel = new JPanel(new FlowLayout());
		
		btnCancelar = ButtonFactory.newRoundedButton("Cancelar", btnH, btnW, 1.0);
		btnCancelar.setActionCommand(CANCEL);
		btnCancelar.setAlignmentX(Component.CENTER_ALIGNMENT);
		newBtnPanel.add(btnCancelar);
		
		btnConfirmar = ButtonFactory.newRoundedButton("Confirmar", btnH, btnW, 1.0);
		btnConfirmar.setActionCommand(CONFIRM);
		btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
		newBtnPanel.add(btnConfirmar);
		
		newBtnPanel.setBackground(ColorPalette.CARD_LIGHT.getColor());
		this.remove(this.btnPanel);
		this.add(newBtnPanel, BorderLayout.SOUTH);
	}

	public void setControlador(ActionListener c) {
		btnConfirmar.addActionListener(c);
		btnCancelar.addActionListener(c);
	}
}
