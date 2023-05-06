/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class KdTree {
    private Node root;
    private int size;
    private Point2D nearestPoint;
    private double nearestDist;

    private static class Node {
        private Point2D p;      // the point
        private boolean ori;
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Node parent, boolean ori, Point2D p, Node lb, Node rt) {
            this.p = p;
            this.lb = lb;
            this.rt = rt;
            this.ori = ori;
            if (parent != null) {
                if (parent.ori) {
                    if (parent.p.x() > p.x()) {
                        this.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(),
                                               parent.rect.ymax());
                    }
                    else {
                        this.rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(),
                                               parent.rect.ymax());
                    }
                }
                else {
                    if (parent.p.y() > p.y()) {
                        this.rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(),
                                               parent.rect.xmax(),
                                               parent.p.y());
                    }
                    else {
                        this.rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(),
                                               parent.rect.ymax());
                    }
                }
            }
            else {
                this.rect = new RectHV(0, 0, 1, 1);
            }
        }
    }


    /** switch the factor */
    private boolean switchOrientation(boolean orientation) {
        if (orientation) {
            orientation = false;
        }
        else {
            orientation = true;
        }
        return orientation;
    }


    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException();
        }
        root = insert(null, root, p, true);
    }

    private Node insert(Node parent, Node node, Point2D p, boolean ori) {
        if (node == null) {
            size++;
            return new Node(parent, ori, p, null, null);
        }
        if (Objects.equals(node.p, p)) {
            return node;
        }
        double cmp = compare(p, node.p, ori);
        if (cmp < 0) node.lb = insert(node, node.lb, p, switchOrientation(ori));
        else node.rt = insert(node, node.rt, p, switchOrientation(ori));
        return node;

    }

    /** customized comparator with given points and and orientation */
    private double compare(Point2D p, Point2D q, boolean ori) {
        if (ori) {
            return p.x() - q.x();
        }
        else {
            return p.y() - q.y();
        }
    }

    public boolean contains(Point2D p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException();
        }
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D p) {
        if (node == null) {
            return false;
        }
        if (Objects.equals(node.p, p)) {
            return true;
        }
        double cmp = compare(p, node.p, node.ori);
        if (cmp < 0) {
            return contains(node.lb, p);
        }
        else {
            return contains(node.rt, p);
        }
    }

    public void draw() {
        draw(root);
    }

    /** recursive helper function for draw */
    private void draw(Node node) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();
            if (node.ori) StdDraw.setPenColor(StdDraw.RED);
            else StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            if (node.ori) {
                StdDraw.line(node.p.x(), 0, node.p.x(), 1);
            }
            else {
                StdDraw.line(0, node.p.y(), 1, node.p.y());
            }
            draw(node.lb);
            draw(node.rt);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (Objects.isNull(rect)) {
            throw new IllegalArgumentException();
        }
        List<Point2D> pointsInside = new LinkedList<>();
        range(root, pointsInside, rect);
        return pointsInside;
    }

    /** range helper function */
    private void range(Node node, List<Point2D> pointsInside, RectHV rect) {
        if (node != null && rect.intersects(node.rect)) {
            if (rect.contains(node.p)) {
                pointsInside.add(node.p);
            }
            range(node.lb, pointsInside, rect);
            range(node.rt, pointsInside, rect);
        }

    }

    public Point2D nearest(Point2D p) {
        if (Objects.isNull(p)) {
            throw new IllegalArgumentException();
        }
        nearestPoint = null;
        nearestDist = Double.POSITIVE_INFINITY;
        if (root != null) {
            nearest(root, p);
        }
        return nearestPoint;
    }

    /** recursive helper function for nearest */
    private void nearest(Node node, Point2D p) {
        if (node != null && node.rect.distanceSquaredTo(p) < nearestDist) {
            double dist = node.p.distanceSquaredTo(p);
            if (dist < nearestDist) {
                nearestDist = dist;
                nearestPoint = node.p;
            }
            double cmp = compare(p, node.p, node.ori);
            if (cmp < 0) {
                nearest(node.lb, p);
                nearest(node.rt, p);
            }
            else {
                nearest(node.rt, p);
                nearest(node.lb, p);
            }

        }
    }
}
