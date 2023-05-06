/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 ****************************************************************************
 */

import java.util.ArrayList;
import java.util.Arrays;

// finds all Line segments containing 4 or more
public class FastCollinearPoints {
    private static final int N = 4;
    private final ArrayList<LineSegment> ls = new ArrayList<>();
    private Point[] sortedBySlopePoints;
    private Point[] sortedPoints;

    /**
     * finds all line segments containing 4 or more points
     * As Arrays.sort() is guaranteed to be stable:
     * equal elements will not be reordered as a result of the sort.
     * So the input points array is already sorted by points natural order
     */
    public FastCollinearPoints(Point[] points) {

        if (isInvalid(points)) {
            throw new IllegalArgumentException();
        }
        if (points.length < 4) {
            return;
        }

        for (Point p : sortedPoints) {
            // copy it for each p, to keep the original natural order
            sortedBySlopePoints = sortedPoints.clone();
            Arrays.sort(sortedBySlopePoints, p.slopeOrder()); // the first one is p itself
            int k = 2; // any two point could form a line
            int begin = 1;
            int end = 1;
            double slopeA;
            
            double slopeB = p.slopeTo(sortedBySlopePoints[1]);
            for (int i = 1; i < sortedBySlopePoints.length - 1; i++) {
                slopeA = slopeB;
                slopeB = p.slopeTo(sortedBySlopePoints[i + 1]);

                if (Double.compare(slopeA, slopeB) == 0) {
                    k++;
                    end = i + 1;
                    if (i + 1 < sortedBySlopePoints.length - 1) {
                        continue;
                    }
                }

                if (k >= N) { // a valid collinear
                    addSegments(p, begin, end);
                }
                k = 2;
                begin = i + 1;

            }
        }

    }

    /**
     * check if new found collinear points already exist in the LineSegment
     * 1. Since the every possible segment is created by points it contains,
     * 2. and we iterate through the sortedPoints to find segment
     * 3. so every non-duplicate new segment is created from its smallest point member
     * 4. any duplicate segment is created later by its other member other than the smallest
     * if not, add in
     *
     * @param start, end
     */
    private void addSegments(Point cur, int start, int end) {
        if (cur.compareTo(sortedBySlopePoints[start]) > 0) {
            return;
        }
        ls.add(new LineSegment(cur, sortedBySlopePoints[end]));
    }


    /**
     * return true
     * if the argument to the constructor is null,
     * if any point in the array is null,
     * or if the argument to the constructor contains a repeated point.
     *
     * @return boolean
     */
    private boolean isInvalid(Point[] points) {
        if (points == null) {
            return true;
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                return true;
            }
        }
        sortedPoints = new Point[points.length];
        sortedPoints = points.clone();
        Arrays.sort(sortedPoints); // Sorts the specified array of objects into ascending order
        for (int i = 0; i < sortedPoints.length - 1; i++) {
            if (sortedPoints[i].compareTo(sortedPoints[i + 1]) == 0) {
                return true;
            }
        }
        sortedBySlopePoints = new Point[points.length];
        return false;
    }

    /**
     * the number of line segments
     */
    public int numberOfSegments() {
        return ls.size();
    }


    /**
     * the line segments
     * include each maximal line segment containing 4 (or more) points exactly once.
     * For example, if 5 points appear on a line segment in the order p→q→r→s→t,
     * then do not include the subsegments p→s or q→t.
     */
    public LineSegment[] segments() {
        return ls.toArray(new LineSegment[ls.size()]);
    }


}


