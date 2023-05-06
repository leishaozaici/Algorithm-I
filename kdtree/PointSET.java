/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> set;

    public PointSET() {
        set = new TreeSet<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public int size() {
        return set.size();
    }

    public void insert(Point2D p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException();
        }
        if (!set.contains(p)) {
            set.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException();
        }
        return set.contains(p);
    }

    public void draw() {
        for (Point2D point2D : set) {
            point2D.draw();
        }
    }

    /**
     * 不要使用stream API,会报错hashcode()调用不支持
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (Objects.isNull(rect)) {
            throw new IllegalArgumentException();
        }
        Set<Point2D> point2DSet = new TreeSet<>();
        for (Point2D point2D : set) {
            if (rect.contains(point2D)) {
                point2DSet.add(point2D);
            }
        }
        return point2DSet;
    }

    public Point2D nearest(Point2D p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException();
        }
        if (set.isEmpty()) {
            return null;
        }
        double distance = Double.POSITIVE_INFINITY;
        Point2D nearPoint = null;
        for (Point2D point2D : set) {
            double tempDistance = point2D.distanceSquaredTo(p);
            if (tempDistance <= distance) {
                nearPoint = point2D;
                distance = tempDistance;
            }
        }
        return nearPoint;
    }

    /*public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        brute.draw();
        StdDraw.show();

        // process range search queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // user starts to drag a rectangle
            if (StdDraw.isMousePressed() && !isDragging) {
                x0 = x1 = StdDraw.mouseX();
                y0 = y1 = StdDraw.mouseY();
                isDragging = true;
            }

            // user is dragging a rectangle
            else if (StdDraw.isMousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
            }

            // user stops dragging rectangle
            else if (!StdDraw.isMousePressed() && isDragging) {
                isDragging = false;
            }

            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw the rectangle
            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                                     Math.max(x0, x1), Math.max(y0, y1));
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for brute-force data structure in red
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            for (Point2D p : brute.range(rect))
                p.draw();
            StdDraw.show();
            StdDraw.pause(20);
        }
    }*/
}

