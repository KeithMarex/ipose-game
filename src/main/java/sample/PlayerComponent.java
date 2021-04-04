package sample;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    public void onUpdate(Entity entity, double tpf) {
    }

    public void left(){
        physics.setVelocityX(-100);
    }

    public void right(){
        physics.setVelocityX(100);
    }

    public void jump(){
        physics.setVelocityY(-600);
        FXGL.play("jump.wav");
    }

    public boolean isNotFalling(){
        return (physics.getVelocityY() == 0);
    }

}
