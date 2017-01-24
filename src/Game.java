import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Game extends Application {

    private static final double width = 600, height = 400;

    private static final String PLAYER_IMAGE =
            "http://icons.iconarchive.com/icons/martin-berube/character/24/Policeman-icon.png";

    private Image playerImg;
    private Node player;

    private boolean running, goNorth, goSouth, goEast, goWest;

    @Override
    public void start(Stage stage) throws Exception {
        playerImg = new Image(PLAYER_IMAGE);
        player = new ImageView(playerImg);

        Group group = new Group(player);

        moveHeroTo(width / 2, height / 2);

        Scene scene = new Scene(group, width, height, Color.rgb(66, 134, 244));

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case W:
                    goNorth = true;
                    break;
                case S:
                    goSouth = true;
                    break;
                case A:
                    goWest = true;
                    break;
                case D:
                    goEast = true;
                    break;
                case SHIFT:
                    running = true;
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case W:
                    goNorth = false;
                    break;
                case S:
                    goSouth = false;
                    break;
                case A:
                    goWest = false;
                    break;
                case D:
                    goEast = false;
                    break;
                case SHIFT:
                    running = false;
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;

                if (goNorth) dy -= 1;
                if (goSouth) dy += 1;
                if (goEast) dx += 1;
                if (goWest) dx -= 1;
                if (running) {
                    dx *= 3;
                    dy *= 3;
                }

                moveHeroBy(dx, dy);
            }
        };
        timer.start();
    }

    private void moveHeroBy(int dx, int dy) {
        if (dx == 0 && dy == 0) return;

        final double cx = player.getBoundsInLocal().getWidth() / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        double x = cx + player.getLayoutX() + dx;
        double y = cy + player.getLayoutY() + dy;

        moveHeroTo(x, y);
    }

    private void moveHeroTo(double x, double y) {
        final double cx = player.getBoundsInLocal().getWidth() / 2;
        final double cy = player.getBoundsInLocal().getHeight() / 2;

        if (x - cx >= 0 &&
                x + cx <= width &&
                y - cy >= 0 &&
                y + cy <= height) {
            player.relocate(x - cx, y - cy);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
