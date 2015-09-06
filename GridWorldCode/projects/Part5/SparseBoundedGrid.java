import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A <code>BoundedGrid</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E> {
    private LinkedList<OccupantInCol>[] occupantArray;
    private int numRow;
    private int numCol;

    public class OccupantInCol {
        private Object occupant;
        private int col;

        public OccupantInCol(Object tocu, int tcol) {
            occupant = tocu;
            col = tcol;
        }

        public Object getOccupant() {
            return occupant;
        }

        public int getCol() {
            return col;
        }

        public void setOccupant(Object ocu) {
            occupant = ocu;
        }
    }

    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public SparseBoundedGrid(int rows, int cols) {
        if (rows <= 0)
            throw new IllegalArgumentException("rows <= 0");
        if (cols <= 0)
            throw new IllegalArgumentException("cols <= 0");
        numRow = rows;
        numCol = cols;
        occupantArray = (LinkedList<OccupantInCol>[]) new LinkedList<?>[numRow];
    }

    public int getNumRows() {
        return numRow;
    }

    public int getNumCols() {
        return numCol;
    }

    public boolean isValid(Location loc) {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations() {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
            for (OccupantInCol ocu : occupantArray[r])
                theLocations.add(new Location(r, ocu.getCol()));

        return theLocations;
    }

    public E get(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        for (OccupantInCol ocu : occupantArray[loc.getRow()])
            if (ocu.getCol() == loc.getCol())
                return (E) ocu.getOccupant();
        return null;
    }

    public E put(Location loc, E obj) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        if (obj == null)
            throw new NullPointerException("obj == null");

        E oldOccupant = get(loc);
        if (oldOccupant != null) {
            for (OccupantInCol ocu : occupantArray[loc.getRow()])
                if (ocu.getCol() == loc.getCol()) {
                    ocu.setOccupant(obj);
                    break;
                }
        } else {
            occupantArray[loc.getRow()].add(new OccupantInCol(obj, loc.getCol()));
        }

        return oldOccupant;
    }

    public E remove(Location loc) {
        if (!isValid(loc))
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        E oldOccupant = get(loc);
        if (oldOccupant != null)
            for (OccupantInCol ocu : occupantArray[loc.getRow()])
                if (ocu.getCol() == loc.getCol()) {
                    occupantArray[loc.getRow()].remove(ocu);
                    break;
                }
        return oldOccupant;
    }
}
