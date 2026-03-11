package usuario;

import java.util.*;

public class Empleado extends Usuario{
	private Set<Permiso> permisos;
	private List<Notificacion> notificaciones;
	
	public Empleado(String nombre, String contrasena, Permiso...perms) {
		super(nombre, contrasena);
		
		if(perms.length > 3) {
			throw new IllegalArgumentException();
		}
		
		this.permisos = new HashSet<>();
		for(Permiso p : perms) {
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
	
	public boolean addNotificacion(Notificacion notificacion) {
		notificaciones.add(notificacion);
		return true;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\nPermisos: " + permisos;
	}
}
