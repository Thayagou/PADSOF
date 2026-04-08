package usuario;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import exceptions.InvalidArgumentException;
import sistema.ParametroSistema;
import sistema.Sistema;

/**
 * Esta clase representa el gestor de la tienda
 */
public class Gestor extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Set de permisos del gestor*/
	private Set<Permiso> permisos  = new HashSet<>();

	/**
	 * Constructor de la clase
	 * @param nombre Nombre del gestor
	 * @param contrasena Contraseña del gestor
	 * @throws InvalidArgumentException Se lanza si los argumentos de la función son inválidos
	 */
	public Gestor(String nombre, String contrasena) throws InvalidArgumentException {
		super(nombre, contrasena);
		permisos.add(Permiso.PRODUCTOS);
	}

	/**
	 * Añade permisos a un empleado
	 * @param e Empleado al que se le añaden permisos
	 * @param permisos Permisos que se añaden 
	 * @return true si se añadieron correctamente, false en caso contrario
	 */
	public boolean anadirPermisos(Empleado e, Permiso...permisos) {
		boolean status = true;
		for(Permiso p : permisos) {
			status = e.addPermiso(p);
		}
		return status;
	}
	
	/**
	 * Quita permisos a un empleado
	 * @param e Empleado al que se le quitan permisos
	 * @param permisos Permisos que se quitan 
	 * @return true si se quitaron correctamente, false en caso contrario
	 */
	public boolean quitarPermisos(Empleado e, Permiso...permisos) {
		boolean status = true;
		for(Permiso p : permisos) {
			status = e.quitarPermiso(p);
		}
		return status;
	}
	
	/**
	 * Devuelve si el gestor tiene un cierto permiso
	 * @param p Permiso por el que se pregunta
	 * @return true si tiene el permiso, false en caso contrario
	 */
	public boolean tienePermiso(Permiso p) {
		return permisos.contains(p);
	}
	
	/**
	 * Modifica el tiempo de caducidad del carrito
	 * @param tiempo Nuevo tiempo de caducidad
	 * @return true si se modifico correctamente, false en caso contrario
	 */
	public boolean modificarTiempoCaducidadCarrito(Duration tiempo) {
		return Sistema.getInstancia().setTiempoCaducaCarrito(tiempo);
	}
	
	/**
	 * Modifica el tiempo de caducidad de una oferta
	 * @param tiempo Nuevo tiempo de caducidad
	 * @return true si se modifico correctamente, false en caso contrario
	 */
	public boolean modificarTiempoCaducidadOferta(Duration tiempo) {
		return Sistema.getInstancia().setTiempoCaducaOferta(tiempo);
	}

	
	/**
	 * Modifica los parametros del sistema de recomendacion
	 * @param activo Indica se quieren activar o desactivar los siguientes parametros
	 * @param parametros Parametros que se quieren modificar
	 * @return true si se modificaron correctamente, false en caso contrario
	 */
	public boolean establecerParametros(boolean activo, ParametroSistema... parametros) {
		boolean status = true;
		for(ParametroSistema p : parametros) {
			status = Sistema.getInstancia().gestionarParametro(p, activo);
		}
		return status;
	}
}
