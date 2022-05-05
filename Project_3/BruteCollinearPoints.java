/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] pointsclone;
    private final ArrayList<LineSegment> lineSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (checkpoints(points)) {
            throw new IllegalArgumentException("Invalid Arguments");
        }
        // int length = pointsclone.length;
        for (int k1 = 0; k1 < pointsclone.length; k1++) {
            for (int k2 = k1 + 1; k2 < pointsclone.length; k2++) {
                for (int k3 = k2 + 1; k3 < pointsclone.length; k3++) {
                    for (int k4 = k3 + 1; k4 < pointsclone.length; k4++) {
                        if (pointsclone[k1].slopeTo(points[k2]) ==
                                pointsclone[k2].slopeTo(pointsclone[k3]) &&
                                pointsclone[k2].slopeTo(pointsclone[k3]) ==
                                        pointsclone[k3].slopeTo(pointsclone[k4])) {
                            lineSegments.add(new LineSegment(pointsclone[k1], pointsclone[k4]));
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
    }

    private boolean checkpoints(Point[] points) {
        if (points == null) {
            return true;
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                return true;
            }
        }
        pointsclone = new Point[points.length];
        Arrays.sort(points);
        // System.arraycopy(points, 0, pointsclone, 0, points.length);
        // pointsclone = Arrays.copyOf(points, points.length);
        pointsclone = points.clone();
        Arrays.sort(pointsclone); // Sorts the specified array of objects into ascending order
        for (int i = 0; i < points.length - 1; i++) {
            if (pointsclone[i].compareTo(pointsclone[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

}
