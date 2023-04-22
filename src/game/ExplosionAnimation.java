package game;

import javafx.animation.Transition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ExplosionAnimation extends Transition {
  private ImageView explosion;
  private double startRadius;
  private double endRadius;

  public ExplosionAnimation(ImageView explosion, double endRadius) {
    this.explosion = explosion;
    this.startRadius = 0;
    this.endRadius = endRadius;
    setCycleDuration(Duration.millis(500));
  }

  @Override
  protected void interpolate(double frac) {
    double newRadius = startRadius + (endRadius - startRadius) * frac;
    explosion.setFitHeight(newRadius * 2);
    explosion.setFitWidth(newRadius * 2);
  }
}