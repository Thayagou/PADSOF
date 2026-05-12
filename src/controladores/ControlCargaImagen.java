package controladores;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import vistas.common.app.TiendaFrame;
import vistas.common.assets.PanelCargaImagen;

/**
 * Controlador del diálogo modal de carga de imagen.
 *
 * Uso desde otro controlador:
 *
 * String nombreImagen = ControlCargaImagen.abrir("Producto", "16"); if
 * (nombreImagen != null) { producto.setImagen(nombreImagen); }
 *
 * El método abrir() bloquea el hilo llamante hasta que el usuario confirme o
 * cancele, gracias al JDialog modal.
 */
public class ControlCargaImagen implements ActionListener {

	private static final double DIALOG_W = 0.35;
	private static final double DIALOG_H = 0.45;

	private final String tipo;
	private final String id;

	private final JDialog dialogo;
	private final PanelCargaImagen vista;

	private File ficheroSeleccionado = null;
	private String resultado = null;

	/* Constructor privado: solo se instancia desde abrir() */
	private ControlCargaImagen(String tipo, String id) {
		this.tipo = tipo;
		this.id = id;

		TiendaFrame frame = TiendaFrame.getInstance();

		vista = new PanelCargaImagen(tipo);
		vista.setControlador(this);

		int w = frame.getPixelsWidth(DIALOG_W);
		int h = frame.getPixelsHeight(DIALOG_H);

		dialogo = new JDialog(frame, "Cargar imagen", true);
		dialogo.setDefaultCloseOperation(accionCancelar());
		dialogo.setSize(w, h);
		dialogo.setLocationRelativeTo(frame);
		dialogo.setResizable(false);
		dialogo.add(vista);
	}
	
	/**
	 * Método para abrir pero manteniendo el mismo nombre para el nuevo archivo
	 * 
	 * @return Nombre del fichero guardado, o null si el usuario canceló o hubo un error
	 */
	public static String abrir() {
		JFileChooser chooser = new JFileChooser();
	    chooser.setDialogTitle("Selecciona una imagen PNG");
	    chooser.setFileFilter(new FileNameExtensionFilter("Imágenes PNG", "png"));
	    chooser.setAcceptAllFileFilterUsed(false);

	    int opcion = chooser.showOpenDialog(TiendaFrame.getInstance());
	    if (opcion != JFileChooser.APPROVE_OPTION) return null;

	    File fichero = chooser.getSelectedFile();
	    String nombreSinExtension = fichero.getName().replaceFirst("\\.png$", "");

	    return GestorImagenes.guardarImagenProductFiles(fichero, nombreSinExtension, "");
	}

	/**
	 * Abre la carga de imagen y bloquea hasta que el usuario confirma o cancela.
	 *
	 * @param tipo Tipo de objeto
	 * @param id Id del objeto
	 * @return Nombre del fichero guardado, o null si el usuario canceló o hubo un error.
	 */
	public static String abrir(String tipo, String id) {
		ControlCargaImagen ctrl = new ControlCargaImagen(tipo, id);
		ctrl.dialogo.setVisible(true);
		return ctrl.resultado;
	}
	
	/**
	 * Metodo abrir pero generando una id aleatoria para la imagen
	 *
	 * @param tipo Tipo de objeto
	 * @return Nombre del fichero guardado, o null si el usuario canceló o hubo un error.
	 */
	public static String abrir(String tipo) {
		return abrir(tipo,java.util.UUID.randomUUID().toString());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Seleccionar" -> accionSeleccionar();
		case "Confirmar" -> accionConfirmar();
		case "Cancelar" -> accionCancelar();
		}
	}

	private void accionSeleccionar() {
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Selecciona una imagen PNG");
		chooser.setFileFilter(new FileNameExtensionFilter("Imágenes PNG", "png"));
		chooser.setAcceptAllFileFilterUsed(false);

		int opcion = chooser.showOpenDialog(dialogo);

		if (opcion == JFileChooser.APPROVE_OPTION) {
			ficheroSeleccionado = chooser.getSelectedFile();
			mostrarPreview(ficheroSeleccionado);
		}
	}

	private void mostrarPreview(File fichero) {
		TiendaFrame t = TiendaFrame.getInstance();
		int previewW = t.getPixelsWidth(0.19);
		int previewH = t.getPixelsHeight(0.21);

		ImageIcon sinEscalar = new ImageIcon(fichero.getAbsolutePath());
		Image img = sinEscalar.getImage().getScaledInstance(previewW, previewH, Image.SCALE_SMOOTH);
		ImageIcon imagenEscalada = new ImageIcon(img);

		vista.setPreview(imagenEscalada, fichero.getName());
	}

	private void accionConfirmar() {
		if (ficheroSeleccionado == null)
			return;

		resultado = GestorImagenes.guardarImagen(ficheroSeleccionado, tipo, id);

		if (resultado == null) {
			JOptionPane.showMessageDialog(dialogo,
					"No se pudo guardar la imagen. Comprueba los permisos del directorio.", "Error al guardar",
					JOptionPane.ERROR_MESSAGE);
		} else {
			dialogo.dispose();
		}
	}

	private int accionCancelar() {
		resultado = null;
		ficheroSeleccionado = null;
		dialogo.dispose();
		return WindowConstants.DISPOSE_ON_CLOSE;
	}
}
