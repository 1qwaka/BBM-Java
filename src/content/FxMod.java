package content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import mindustry.entities.Effect;

import static arc.math.Angles.randLenVectors;

public class FxMod {
    public static final Effect

    nucImpSmoEffect = new Effect(400, e ->{{
        randLenVectors(e.id, 4, e.fin() * 13f, (x, y) -> {
            float size = e.fslope() * 4f;
            Draw.color(Color.valueOf("808000"), Color.gray, e.fin());
            Fill.circle(e.x + x, e.y + y, size/2f);
        });
    }});
}
