package com.manulaiko.tabitha.utils;

/**
 * Vector class helper
 *
 * @author S7KYuuki
 */
public class Vector
{
    /**
     * Adds 2 vectors
     *
     * @param one Vector 1
     * @param two Vector 2
     *
     * @return Vector 1 + Vector 2
     */
    public static Vector plus(Vector one, Vector two)
    {
        return new Vector(
                one.getX() + two.getX(),
                one.getY() + two.getY()
        );
    }

    /**
     * Minus 2 vectors
     *
     * @param one Vector 1
     * @param two Vector 2
     *
     * @return Vector 1 - Vector 2
     */
    public static Vector minus(Vector one, Vector two)
    {
        return new Vector(
                one.getX() - two.getX(),
                one.getY() - two.getY()
        );
    }

    /**
     * X Position
     */
    private double _x;

    /**
     * Y Position
     */
    private double _y;

    /**
     * Constructor
     */
    public Vector()
    {

    }

    /**
     * Constructor
     *
     * @param x X position
     * @param y Y Position
     */
    public Vector(double x, double y)
    {
        this._x = x;
        this._y = y;
    }

    /**
     * Returns X position
     *
     * @return X position
     */
    public double getX()
    {
        return _x;
    }

    /**
     * Returns Y position
     *
     * @return Y Position
     */
    public double getY()
    {
        return _y;
    }

    /**
     * Sets X position
     *
     * @param x New position
     */
    public void setX(double x)
    {
        this._x = x;
    }

    /**
     * Sets Y position
     *
     * @param y New position
     */
    public void setY(double y)
    {
        this._y = y;
    }

    /**
     * Returns a vector at 0:0
     *
     * @return Empty vector
     */
    public Vector reset()
    {
        return new Vector(0, 0);
    }

    /**
     * Checks the distance between this vector and another
     *
     * @param vector Vector to calculate the distance
     *
     * @return The distance between this and Point
     */
    public double distanceTo(Vector vector)
    {
        double dx = this.getX() - vector.getX();
        double dy = this.getX() - vector.getX();

        return Math.sqrt((dx * dx) + (dy * dy));
    }

    /**
     * Parses the object to a string
     *
     * @return The vector as a String
     */
    @Override public String toString()
    {
        return this.getX() + "," + this.getY();
    }

    /**
     * Checks if this vector is the same as obj
     *
     * @param obj Vector to check
     *
     * @return Whether this == obj
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Vector) {
            Vector v2d = (Vector)obj;

            return (
                    v2d.getX() == this.getX() &&
                    v2d.getY() == this.getY()
            );
        }

        return false;
    }

    /**
     * Hashes the vector
     *
     * @return Hashed vector
     */
    @Override
    public int hashCode()
    {
        return (this.getX() + " " + this.getY()).hashCode();
    }
}