package subsystems.SleepyStuffff.Math;

import static constants.RobotConstants.CVSmoothing;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import subsystems.SleepyStuffff.Util.Vector2d;

public class QuadrilateralTracker {
    List<Vector2d> tl = new ArrayList<>();
    List<Vector2d> bl = new ArrayList<>();
    List<Vector2d> tr = new ArrayList<>();
    List<Vector2d> br = new ArrayList<>();
    public double currentRotation = 0.0;

    public List<Vector2d> getSmoothedCorners() {
        Vector2d vtl = new Vector2d(0.0, 0.0);
        Vector2d vtr = new Vector2d(0.0, 0.0);
        Vector2d vbl = new Vector2d(0.0, 0.0);
        Vector2d vbr = new Vector2d(0.0, 0.0);

        for (int idx = 0; idx < tl.size(); idx++) {
            vtl = vtl.add(tl.get(idx));
            vtr = vtr.add(tr.get(idx));
            vbl = vbl.add(bl.get(idx));
            vbr = vbr.add(br.get(idx));
        }

        vtl = vtl.divide(CVSmoothing);
        vtr = vtr.divide(CVSmoothing);
        vbl = vbl.divide(CVSmoothing);
        vbr = vbr.divide(CVSmoothing);

        return Arrays.asList(vtl, vtr, vbl, vbr);
    }

    public double getDirection() {
        List<Vector2d> smoothedCorners = getSmoothedCorners();
        double dx = smoothedCorners.get(0).x - smoothedCorners.get(2).x;
        double dy = smoothedCorners.get(0).y - smoothedCorners.get(2).y;
        double dx2 = smoothedCorners.get(0).x - smoothedCorners.get(1).x;
        double dy2 = smoothedCorners.get(0).y - smoothedCorners.get(1).y;
        double mag1 = dx*dx + dy*dy;
        double mag2 = dx2*dx2 + dy2*dy2;
        double new_rot;
        if (mag2 > mag1 * 1.5) new_rot = Math.toDegrees(Math.atan2(dx2, dy2));
        else new_rot = Math.toDegrees(Math.atan2(dx, dy));
        currentRotation = new_rot;
        return currentRotation;
    }

    public void update(List<Vector2d> vertices) {
        if (vertices == null) {
            tl.clear();
            tr.clear();
            bl.clear();
            br.clear();
            return;
        }
        if (vertices.size() < 4) {
            tl.clear();
            tr.clear();
            bl.clear();
            br.clear();
            return;
        }

        Vector2d top = null;
        Vector2d bot = null;
        Vector2d sto = null;
        Vector2d sbo = null;

        for (Vector2d vertex : vertices) {
            if (top == null) {
                top = vertex;
            } else if (sto == null) {
                if (vertex.y >= top.y) {
                    sto = top;
                    top = vertex;
                } else {
                    sto = vertex;
                }
            } else {
                if (vertex.y >= top.y) {
                    sto = top;
                    top = vertex;
                } else if (vertex.y >= sto.y) {
                    sto = vertex;
                }
            }
            if (bot == null) {
                bot = vertex;
            } else if (sbo == null) {
                if (vertex.y <= bot.y) {
                    sbo = bot;
                    bot = vertex;
                } else {
                    sbo = vertex;
                }
            } else {
                if (vertex.y <= bot.y) {
                    sbo = bot;
                    bot = vertex;
                } else if (vertex.y <= sbo.y) {
                    sbo = vertex;
                }
            }
        }
        if (top == null || bot == null || sbo == null || sto == null) return;

        Vector2d vtl = new Vector2d(0.0,0.0);
        Vector2d vbr = new Vector2d(0.0,0.0);
        Vector2d vbl = new Vector2d(0.0,0.0);
        Vector2d vtr = new Vector2d(0.0,0.0);

        if (top.x < sto.x) {
            vtl = top;
            vtr = sto;
        } else {
            vtl = sto;
            vtr = top;
        }

        if (bot.x < sbo.x) {
            vbl = bot;
            vbr = sbo;
        } else {
            vbl = sbo;
            vbr = bot;
        }

        tl.add(vtl);
        tr.add(vtr);
        bl.add(vbl);
        br.add(vbr);

        if (tl.size() > CVSmoothing) tl.remove(0);
        if (tr.size() > CVSmoothing) tr.remove(0);
        if (bl.size() > CVSmoothing) bl.remove(0);
        if (br.size() > CVSmoothing) br.remove(0);
    }
}