package sample;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {

    public enum EntityType {
        PLAYER, PLATFORM
    }

    private Entity player, platform;
    private boolean allowJump;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(480);
        settings.setHeight(800);
        settings.setTitle("Doodle Jump");
        settings.setVersion("1.0");
        settings.setIntroEnabled(false);
        settings.setMainMenuEnabled(false);
        settings.setGameMenuEnabled(false);
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Left") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).left();
                player.setScaleX(-1);
                player.translateX(75);
            }
        }, KeyCode.A);

        getInput().addAction(new UserAction("Right") {
            @Override
            protected void onAction() {
                player.getComponent(PlayerComponent.class).right();
                player.setScaleX(1);
                player.translateX(-75);
            }
        }, KeyCode.D);

        getInput().addAction(new UserAction("Up") {
            @Override
            protected void onAction() {
                if (player.getComponent(PlayerComponent.class).isNotFalling()){
                    player.getComponent(PlayerComponent.class).jump();
                }
            }
        }, KeyCode.W);
    }

    protected void initAssets() throws Exception {

    }

    @Override
    protected void initGame() {
        int lastw;
        for (int i = 0; i < 150; i++){
            int height = i * random(50, 150);
            int width = random(-50, getAppWidth() - 50);

            platform = FXGL.entityBuilder().type(EntityType.PLATFORM).at(width, -height + getAppHeight()).viewWithBBox("Platform.png").collidable().with(new PhysicsComponent()).buildAndAttach();
        }

        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);

        player = FXGL.entityBuilder().type(EntityType.PLAYER).at(getAppWidth() / 2 - 32.5, getAppHeight() - 500).viewWithBBox(texture("Doodler.png", 75, 75)).with(physics).with(new PlayerComponent()).collidable().buildAndAttach();

        getGameScene().getViewport().setBounds(0, -20000, getAppWidth(), getAppHeight());
        getGameScene().getViewport().bindToEntity(player, getAppWidth() / 2 , getAppHeight() / 2);
    }

    @Override
    protected void initPhysics() {
//        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
//
//            // order of types is the same as passed into the constructor
//            @Override
//            protected void onCollisionBegin(Entity player, Entity platform) {
//                platform.removeFromWorld();
//            }
//        });

        FXGL.onCollision(EntityType.PLAYER, EntityType.PLATFORM, (player, platform) -> {
            System.out.println(player.getY());
            if (player.getY() < -2000){
                getDisplay().showMessageBox("Je hebt 2000 meter gehaald!", () -> {
                    System.out.println("Je bent tot het einde gekomen!");
                });
            }
        });
    }

    @Override
    protected void initUI() {
        FXGL.getGameScene().setBackgroundRepeat("background.png");
    }

    protected void onUpdate() {

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
    }

    public static void main(String[] args) {
        launch(args);
    }

}

