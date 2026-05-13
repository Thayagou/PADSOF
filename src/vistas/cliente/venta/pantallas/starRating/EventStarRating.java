package vistas.cliente.venta.pantallas.starRating;

/**
 * Interfaz para eventos de selección de puntuación en el componente StarRating.
 */
public interface EventStarRating {

    /**
     * selected.
     * Método invocado cuando se selecciona una puntuación de estrellas.
     *
     * @param star número de estrellas seleccionado (1-5)
     */
    public void selected(int star);
}