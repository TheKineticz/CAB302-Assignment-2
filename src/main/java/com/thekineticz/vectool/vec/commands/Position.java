package com.thekineticz.vectool.vec.commands;

/**
 * Class representing a position pair.
 * @param <E> The type of the pair.
 */
public class Position<E> {
    private final E x;
    private final E y;

    /**
     * Creates a new position pair.
     *
     * @param x The first element of the pair.
     * @param y The second element of the pair.
     */
    public Position(E x, E y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the first element of the pair.
     *
     * @return The first element of the pair.
     */
    public E getX(){
        return x;
    }

    /**
     * Gets the second element of the pair.
     *
     * @return The second element of the pair.
     */
    public E getY(){
        return y;
    }

    /**
     * Get the hash code of the pair.
     *
     * @return The hash code of the pair.
     */
    @Override
    public int hashCode(){
        return x.hashCode() ^ y.hashCode();
    }

    /**
     * Checks for equality between the pair and another object
     *
     * @param o The other object.
     * @return The equality value.
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Position)){
            return false;
        }

        Position pos2 = (Position) o;
        return x.equals(pos2.x) && y.equals(pos2.y);
    }

    /**
     * Gets the pair in string form.
     *
     * @return The pair in string form.
     */
    @Override
    public String toString(){
        return String.format("%s %s", x.toString(), y.toString());
    }
}
