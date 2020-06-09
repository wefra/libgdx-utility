package Math;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

class VectorUtil
{

    public static Vector2 findCentroid(Vector2[] v) {
        Vector2 off = v[0];
        Vector2 p1 = new Vector2();
        Vector2 p2 = new Vector2();
        float twicearea = 0, x = 0, y = 0, f = 0;
        for (int i = 0, j = v.length - 1; i < v.length; j = i++) {
            p1 = v[i];
            p2 = v[j];
            f = (p1.x - off.x) * (p2.y - off.y) - (p2.x - off.x) * (p1.y - off.y);
            twicearea += f;
            x += (p1.x + p2.x - 2 * off.x) * f;
            y += (p1.y + p2.y - 2 * off.y) * f;
        }
        f = twicearea * 3;
        return new Vector2(x / f + off.x, y / f + off.y);
    }

    /**
     * @param deg degree
     * @param i initial vector2
     * @param c center vector2
     * @return change the initial vector2 */
    public static void rotate(float deg, Vector2 i, Vector2 c){
        float rad = deg * MathUtils.degreesToRadians; //degreesToRadians = PI / 180
        Vector2 rot = new Vector2();
        rot.x = MathUtils.cos(rad) * (i.x - c.x) - MathUtils.sin(rad) * (i.y - c.y) + c.x;
        rot.y = MathUtils.sin(rad) * (i.x - c.x) + MathUtils.cos(rad) * (i.y - c.y) + c.y;
        return rot;
    }
}