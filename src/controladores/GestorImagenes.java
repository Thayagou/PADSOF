package controladores;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Gestiona el almacenamiento de imágenes en el directorio de recursos.
 *
 * Responsabilidad única: recibir un fichero fuente y una clave de destino,
 * copiar el fichero con el nombre correcto y devolver el nombre resultante.
 *
 * Nomenclatura de ficheros: <tipo><id>.png ej: Producto16.png, Articulo3.png
 */
public class GestorImagenes {

	private static final String DIRECTORIO_IMAGENES = "resources/gui/";
	private static final String EXTENSION = ".png";

	private GestorImagenes() {
	}

	/**
	 * Copia el fichero de imagen origen al directorio de recursos con el nombre sobreescribiendo si ya existía.
	 *
	 * @param origen Fichero PNG seleccionado por el usuario.
	 * @param tipo   Tipo de objeto
	 * @param id     Id del objeto o algun valor único
	 * @return Nombre del fichero guardado, o null si el usuario canceló o hubo un
	 *         error.
	 */
	public static String guardarImagen(File origen, String tipo, String id) {
		String nombreDestino = tipo + id + EXTENSION;
		File destino = new File(DIRECTORIO_IMAGENES + nombreDestino);

		try {
			destino.getParentFile().mkdirs();
			Files.copy(origen.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return nombreDestino;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Construye el nombre de fichero que correspondería a un objeto
	 *
	 * @param tipo Tipo de objeto
	 * @param id   Id del objeto o algun valor único
	 * @return Nombre del fichero guardado, o null si el usuario canceló o hubo un
	 *         error.
	 */
	public static String nombreImagen(String tipo, String id) {
		return tipo + id + EXTENSION;
	}
}
