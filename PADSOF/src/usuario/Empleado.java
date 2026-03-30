package usuario;

import java.io.Serializable;
import java.util.*;

public class Empleado extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set<Permiso> permisos;
	private List<Notificacion> notificaciones = new ArrayList<>();
	private boolean deAlta;
	
	public Empleado(String nombre, String contrasena, Permiso...perms) 
			throws IllegalArgumentException {
		super(nombre, contrasena);
		
		if(perms.length > 3) {
			throw new IllegalArgumentException();
		}
		
		this.permisos = new HashSet<>();
		for(Permiso p : perms) {
			this.permisos.add(p);
		}
		
		deAlta = true;
	}
	
	public boolean estaDeAlta() {
		return deAlta;
	}
	
	public void darDeBaja() {
		deAlta = false;
	}
	
	public void darDeAlta() {
		deAlta = true;
	}
	
	public void setPermisos(Permiso[] permisos) {
		this.permisos.removeAll(this.permisos);
		for(Permiso p : permisos) {
			this.permisos.add(p);
		}
	}
	
	public boolean addPermiso(Permiso p) {
		return this.permisos.add(p);
	}
	

	public boolean quitarPermiso(Permiso p) {
		return permisos.remove(p);
	}
	
	public Set<Permiso> getPermisos(){
		return this.permisos;
	}
	
	public boolean tienePermiso(Permiso p) {
		return this.permisos.contains(p);
	}
	
	public List<Notificacion> getNotificaciones() {
		return notificaciones;
	}
	
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
		return super.toString() + "\nPermisos: " + permisos;
	}
}
