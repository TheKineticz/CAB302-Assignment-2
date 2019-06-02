package thekineticz.vectool.vec.common;

/**
 * Class representing a position pair.
 */
public class Position {
    private final Double x;
    private final Double y;

    /**
     * Creates a new position pair.
     *
     * @param x The first element of the pair.
     * @param y The second element of the pair.
     */
    public Position(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the first element of the pair.
     *
     * @return The first element of the pair.
     */
    public Double getX(){
        return x;
    }

    /**
     * Gets the second element of the pair.
     *
     * @return The second element of the pair.
     */
    public Double getY(){
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
     * Get the euclidean distance between two positions.
     *
     * @param pos2 Another position.
     * @return The distance between the points.
     */
    public double getDistance(Position pos2){
        return Math.sqrt(Math.pow(pos2.getX() - getX(), 2) + Math.pow(pos2.getY() - getY(), 2));
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
