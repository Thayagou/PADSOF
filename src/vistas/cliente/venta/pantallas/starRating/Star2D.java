package vistas.cliente.venta.pantallas.starRating;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Esta clase proporciona una forma de estrella. Una estrella se define por dos radios y un número
 * de puntas. Cada punta se extiende entre los dos radios. El radio interior es la distancia
 * entre el centro de la estrella y el origen de las puntas. El radio exterior es la distancia
 * entre el centro de la estrella y las puntas de las ramas.
 */
public class Star2D implements Shape {

    /** Campo starShape. Forma geométrica de la estrella. */
    private Shape starShape;
    
    /** Campo x. Coordenada X del centro de la estrella. */
    private double x;
    
    /** Campo y. Coordenada Y del centro de la estrella. */
    private double y;
    
    /** Campo innerRadius. Radio interior de la estrella (distancia al origen de las puntas). */
    private double innerRadius;
    
    /** Campo outerRadius. Radio exterior de la estrella (distancia a las puntas). */
    private double outerRadius;
    
    /** Campo branchesCount. Número de puntas de la estrella. */
    private int branchesCount;

    /**
     * Crea una nueva estrella cuyo centro se encuentra en las coordenadas especificadas.
     * El número de puntas y su longitud pueden especificarse.
     *
     * @param x coordenada X del centro de la estrella
     * @param y coordenada Y del centro de la estrella
     * @param innerRadius distancia entre el centro y el origen de las puntas
     * @param outerRadius distancia entre el centro y las puntas de las ramas
     * @param branchesCount número de puntas de la estrella; debe ser >= 3
     * @throws IllegalArgumentException Lanza la excepción en caso de parámetros incorrectos
     */
    public Star2D(double x, double y,
            double innerRadius, double outerRadius,
            int branchesCount) {
        if (branchesCount < 3) {
            throw new IllegalArgumentException("El número de puntas debe"
                    + " ser >= 3.");
        } else if (innerRadius >= outerRadius) {
            throw new IllegalArgumentException("El radio interior debe ser < "
                    + "radio exterior.");
        }

        this.x = x;
        this.y = y;
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        this.branchesCount = branchesCount;

        starShape = generateStar(x, y, innerRadius, outerRadius, branchesCount);
    }

    /**
     * generateStar.
     * Genera la forma geométrica de la estrella.
     *
     * @param x coordenada X del centro
     * @param y coordenada Y del centro
     * @param innerRadius radio interior
     * @param outerRadius radio exterior
     * @param branchesCount número de puntas
     * @return valor de tipo Shape, la forma geométrica generada
     */
    private static Shape generateStar(double x, double y,
            double innerRadius, double outerRadius,
            int branchesCount) {
        GeneralPath path = new GeneralPath();

        double outerAngleIncrement = 2 * Math.PI / branchesCount;

        double outerAngle = branchesCount % 2 == 0 ? 0.0 : -(Math.PI / 2.0);
        double innerAngle = (outerAngleIncrement / 2.0) + outerAngle;

        float x1 = (float) (Math.cos(outerAngle) * outerRadius + x);
        float y1 = (float) (Math.sin(outerAngle) * outerRadius + y);

        float x2 = (float) (Math.cos(innerAngle) * innerRadius + x);
        float y2 = (float) (Math.sin(innerAngle) * innerRadius + y);

        path.moveTo(x1, y1);
        path.lineTo(x2, y2);

        outerAngle += outerAngleIncrement;
        innerAngle += outerAngleIncrement;

        for (int i = 1; i < branchesCount; i++) {
            x1 = (float) (Math.cos(outerAngle) * outerRadius + x);
            y1 = (float) (Math.sin(outerAngle) * outerRadius + y);

            path.lineTo(x1, y1);

            x2 = (float) (Math.cos(innerAngle) * innerRadius + x);
            y2 = (float) (Math.sin(innerAngle) * innerRadius + y);

            path.lineTo(x2, y2);

            outerAngle += outerAngleIncrement;
            innerAngle += outerAngleIncrement;
        }

        path.closePath();
        return path;
    }

    /**
     * Establece el radio interior de la estrella, es decir, la distancia entre su
     * centro y el origen de las puntas. El radio interior siempre debe ser
     * menor que el radio exterior.
     *
     * @param innerRadius distancia entre el centro y el origen de las puntas
     * @throws IllegalArgumentException si el radio interior es >= radio exterior
     */
    public void setInnerRadius(double innerRadius) {
        if (innerRadius >= outerRadius) {
            throw new IllegalArgumentException("El radio interior debe ser <"
                    + " radio exterior.");
        }

        this.innerRadius = innerRadius;
        starShape = generateStar(getX(), getY(), innerRadius, getOuterRadius(),
                getBranchesCount());
    }

    /**
     * Establece la coordenada X del centro de la estrella.
     *
     * @param x coordenada X del centro de la estrella
     */
    public void setX(double x) {
        this.x = x;
        starShape = generateStar(x, getY(), getInnerRadius(), getOuterRadius(),
                getBranchesCount());
    }

    /**
     * Establece la coordenada Y del centro de la estrella.
     *
     * @param y coordenada Y del centro de la estrella
     */
    public void setY(double y) {
        this.y = y;
        starShape = generateStar(getX(), y, getInnerRadius(), getOuterRadius(),
                getBranchesCount());
    }

    /**
     * Establece el radio exterior de la estrella, es decir, la distancia entre su
     * centro y las puntas de las ramas. El radio exterior siempre debe ser
     * mayor que el radio interior.
     *
     * @param outerRadius distancia entre el centro y las puntas de las ramas
     * @throws IllegalArgumentException si el radio interior es >= radio exterior
     */
    public void setOuterRadius(double outerRadius) {
        if (innerRadius >= outerRadius) {
            throw new IllegalArgumentException("El radio exterior debe ser > "
                    + "radio interior.");
        }

        this.outerRadius = outerRadius;
        starShape = generateStar(getX(), getY(), getInnerRadius(), outerRadius,
                getBranchesCount());
    }

    /**
     * Establece el número de puntas de la estrella. Una estrella siempre debe
     * tener al menos 3 puntas.
     *
     * @param branchesCount número de puntas
     * @throws IllegalArgumentException Lanza la excepción en caso de parámetros incorrectos
     */
    public void setBranchesCount(int branchesCount) {
        if (branchesCount <= 2) {
            throw new IllegalArgumentException("El número de puntas debe"
                    + " ser >= 3.");
        }

        this.branchesCount = branchesCount;
        starShape = generateStar(getX(), getY(), getInnerRadius(),
                getOuterRadius(), branchesCount);
    }

    /**
     * Devuelve la coordenada X del centro de la estrella.
     *
     * @return coordenada X del centro de la estrella
     */
    public double getX() {
        return x;
    }

    /**
     * Devuelve la coordenada Y del centro de la estrella.
     *
     * @return coordenada Y del centro de la estrella
     */
    public double getY() {
        return y;
    }

    /**
     * Devuelve la distancia entre el centro de la estrella y el origen de las
     * puntas.
     *
     * @return radio interior de la estrella
     */
    public double getInnerRadius() {
        return innerRadius;
    }

    /**
     * Devuelve la distancia entre el centro de la estrella y las puntas de las
     * ramas.
     *
     * @return radio exterior de la estrella
     */
    public double getOuterRadius() {
        return outerRadius;
    }

    /**
     * Devuelve el número de puntas de la estrella.
     *
     * @return número de puntas, siempre >= 3
     */
    public int getBranchesCount() {
        return branchesCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle getBounds() {
        return starShape.getBounds();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Rectangle2D getBounds2D() {
        return starShape.getBounds2D();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(double x, double y) {
        return starShape.contains(x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Point2D p) {
        return starShape.contains(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(double x, double y, double w, double h) {
        return starShape.intersects(x, y, w, h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean intersects(Rectangle2D r) {
        return starShape.intersects(r);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(double x, double y, double w, double h) {
        return starShape.contains(x, y, w, h);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Rectangle2D r) {
        return starShape.contains(r);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return starShape.getPathIterator(at);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return starShape.getPathIterator(at, flatness);
    }
}