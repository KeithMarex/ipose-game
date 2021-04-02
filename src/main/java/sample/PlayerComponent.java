package sample;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;

public class PlayerComponent extends Component {

    private PhysicsComponent physics;

    public void onUpdate(Entity entity, double tpf) {

    }

    public void left(){
        physics.setVelocityX(-5);
    }

    public void right(){
        physics.setVelocityX(5);

    }

    public void jump(){
        physics.setVelocityY(-100);
    }

}
