package sistema;

public class AsignadorId {
    private static AsignadorId instancia;
    private long contadorIds;
    
    private AsignadorId() {
        this.contadorIds = 0;
    }
    
    public static AsignadorId getInstancia() {
        if (instancia == null) {
            instancia = new AsignadorId();
        }
        return instancia;
    }
    
    public long siguienteId() {
        return ++contadorIds;
    }
}
