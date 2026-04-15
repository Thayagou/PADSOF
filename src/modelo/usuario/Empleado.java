package modelo.usuario;

import java.io.Serializable;
import java.util.*;

import modelo.exceptions.InvalidArgumentException;

/**
 * Esta clase representa un empleado de la tienda
 */
public class Empleado extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	/**Permisos que tiene el empleado*/
	private Set<Permiso> permisos = new HashSet<>();
	/**Lista de notificaciones del empleado*/
	private List<Notificacion> notificaciones = new ArrayList<>();
	/**Indica si el empleado esta de alta o no*/
	private boolean deAlta;
	
	/**
	 * Constructor de la clase
	 * @param nombre Nombre del empleado
	 * @param contrasena Contraseña del empleado
	 * @param perms Permisos concedidos al empleado
	 * @throws InvalidArgumentException Se lanza si los argumentos son inválidos
	 */
	public Empleado(String nombre, String contrasena, Permiso...perms) 
			throws InvalidArgumentException {
		super(nombre, contrasena);
		
		if(perms.length > 3) {
			throw new IllegalArgumentException();
		}

		for(Permiso p : perms) {
			this.permisos.add(p);
		}
		
		deAlta = true;
	}
	
	/**
	 * Devuelve si esta de alta el empleado
	 * @return true si esta de alta, false en caso contrario
	 */
	public boolean estaDeAlta() {
		return deAlta;
	}
	
	/**
	 * Da de baja a un empleado
	 */
	public void darDeBaja() {
		deAlta = false;
	}
	
	/**
	 * da de baja a un empleado
	 */
	public void darDeAlta() {
		deAlta = true;
	}
	
	/**
	 * Sustituye los permisos del empleado
	 * @param permisos Nuevos permisos del empleado
	 */
	public void setPermisos(Permiso[] permisos) {
		this.permisos.removeAll(this.permisos);
		for(Permiso p : permisos) {
			this.permisos.add(p);
		}
	}
	
	/**
	 * Añade un permiso al empleado
	 * @param p Permiso a añadir
	 * @return true si se añadió correctamente, false en caso contrario
	 */
	public boolean addPermiso(Permiso p) {
		return this.permisos.add(p);
	}
	
	/**
	 * Quita un permiso al empleado
	 * @param p Permiso a quitar
	 * @return true si se quitó correctamente, false en caso contrario
	 */
	public boolean quitarPermiso(Permiso p) {
		return permisos.remove(p);
	}
	
	/**
	 * Devuelve los permisos del empleado
	 * @return Set con los permisos del empleado
	 */
	public Set<Permiso> getPermisos(){
		return this.permisos;
	}
	
	/**
	 * Comprueba si el empleado tiene un permiso
	 * @return true en caso de que lo tenga, false en caso contrario
	 */
	public boolean tienePermiso(Permiso p) {
		return this.permisos.contains(p);
	}
	
	/**
	 * Devuelve la lista con las notificaciones del empleado
	 * @return lista de notificaciones
	 */
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
	/**
	 * Envía una notificación al empleado si posee el permiso asociado
	 * @param mensaje Mensaje de la notificacion
	 * @param tipo Tipo de notificacion
	 * @return true si se envió correctamente, false en caso contrario
	 */
	public boolean enviarNotificacion(String mensaje, TipoNotificacion tipo) {
		if(tipo.equals(TipoNotificacion.INTERCAMBIO) || tipo.equals(TipoNotificacion.VALORACION)) {
			if(!this.permisos.contains(Permiso.INTERCAMBIOS))
				return false;
		} else if (tipo.equals(TipoNotificacion.PEDIDO)) {
			if(!this.permisos.contains(Permiso.PEDIDOS))
				return false;
		} else if (tipo.equals(TipoNotificacion.CADUCIDAD) || tipo.equals(TipoNotificacion.PRODUCTO_AGOTADO)) {
			if(!this.permisos.contains(Permiso.PRODUCTOS))
				return false;
		} else {
			return false;
		}
		
		Notificacion notificacion = new Notificacion(mensaje, tipo);
		notificaciones.add(notificacion);
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nPermisos: " + permisos + "\nNotificaciones: " + notificaciones;
	}
}
