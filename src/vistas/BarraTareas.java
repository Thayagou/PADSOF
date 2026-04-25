package vistas;

import java.awt.*;
import javax.swing.*;

public class BarraTareas extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public BarraTareas() {
		JButton notificaciones = new JButton("Notificaciones");
		setBackground(new Color(60, 0, 160));
        setPreferredSize(new Dimension(0, 50));
	}
	
}
