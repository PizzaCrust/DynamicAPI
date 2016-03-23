package net.dynamicapi.util;

/**
 * Represents a 3-dimensional position in a world
 */
public class Position {
    private final double x;
    private final double y;
    private final double z;

    public Position(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Retrieves the X position coordinate in this position.
     * @return x coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * Retrieves the Y position coordinate in this position.
     * @return y coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * Retrieves the Z position coordinate in this position.
     * @return z coordinate
     */
    public double getZ() {
        return this.z;
    }
}
