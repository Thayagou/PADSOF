package vistas;

import javax.swing.*;
//import java.awt.*;

public class VentanaMensaje extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaMensaje(String msj) {
		JOptionPane.showMessageDialog(null, msj);
	}
}
