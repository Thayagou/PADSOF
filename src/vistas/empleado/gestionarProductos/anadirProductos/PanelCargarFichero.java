package vistas.empleado.gestionarProductos.anadirProductos;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import vistas.common.PanelDisplay;
import vistas.common.TiendaFrame;
import vistas.herramientas.ButtonFactory;
import vistas.herramientas.ColorPalette;
import vistas.herramientas.Fonts;

public class PanelCargarFichero extends PanelDisplay {
	private static final long serialVersionUID = 1L;
	private static final String ARCHIVOS_ACTION = "Cargar fichero de productos";
	public final static String CONFIRMAR_ACTION = "Confirmar";
	private JFileChooser seleccionador;
	private JButton btnConfirmar;

	public PanelCargarFichero() {
		super(0.08, 0.06, ARCHIVOS_ACTION);
		
		super.getClickArea().addActionListener(e -> showSeleccionar());
		JLabel label = new JLabel(ARCHIVOS_ACTION);
		label.setFont(Fonts.TITLE3.getFont());
		add(label, BorderLayout.CENTER);
		
		int height = TiendaFrame.getInstance().getHeight();
		int width = TiendaFrame.getInstance().getWidth();
		
		btnConfirmar = ButtonFactory.newRoundedButton(CONFIRMAR_ACTION, (int) (height * 0.08), (int) (width * 0.1), 0.5);
		btnConfirmar.setMaximumSize(new Dimension((int) (height * 0.08), (int) (width * 0.1)));
		btnConfirmar.setFont(Fonts.BOLD.getFont());
	    ButtonFactory.paintButton(btnConfirmar, ColorPalette.LIGHT_PURPLE, ColorPalette.WHITE);
	    ButtonFactory.addMouseMecanics(btnConfirmar, ColorPalette.PURPLE, ColorPalette.LIGHT_PURPLE);
	    btnConfirmar.setEnabled(false);

		setOpaque(false);
	}
	
	private void showSeleccionar() {
	    Window owner = SwingUtilities.getWindowAncestor(this);

	    JDialog dialog = new JDialog(owner, "Cargar fichero de productos", ModalityType.APPLICATION_MODAL);
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    dialog.setResizable(false);

	    JPanel contenido = ventanaSeleccionarArchivo();
	    dialog.add(contenido);
	    dialog.pack();
	    dialog.setLocationRelativeTo(owner);
	    dialog.setVisible(true);
	}

	private JPanel ventanaSeleccionarArchivo() {
		JPanel ventanaSeleccionar = new JPanel(new BorderLayout(0, 10));
		ventanaSeleccionar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		// Ventana pop-up para seleccionar archivo
		seleccionador = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos csv", "csv");
		seleccionador.setFileFilter(filtro);
		seleccionador.setFileSelectionMode(JFileChooser.FILES_ONLY);
		seleccionador.setControlButtonsAreShown(false);
		seleccionador.setMultiSelectionEnabled(false);

		seleccionador.addPropertyChangeListener(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY, e -> {
			btnConfirmar.setEnabled(seleccionador.getSelectedFile() != null);
		});
		
		JPanel sur = new JPanel(new FlowLayout());
	    sur.add(btnConfirmar);

		ventanaSeleccionar.add(seleccionador, BorderLayout.CENTER);
		ventanaSeleccionar.add(sur, BorderLayout.SOUTH);
		
		return ventanaSeleccionar;
	}

	public void setControlador(ActionListener c) {
		super.setControlador(c);
		btnConfirmar.addActionListener(c);
	}

	public String getNombreFichero() {
		File f = seleccionador.getSelectedFile();
		return f != null ? f.getName() : null;
	}

	public String getRutaFichero() {
		File f = seleccionador.getSelectedFile();
		return f != null ? f.getAbsolutePath() : null;
	}
}
