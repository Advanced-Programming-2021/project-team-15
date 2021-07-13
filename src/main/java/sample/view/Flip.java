package sample.view;

import javafx.animation.Transition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Flip extends Transition {
    private ImageView node;
    private Image backOfCard;
    private Image frontImage;
    private boolean frontToBack = false;
    private  boolean rightToLeft= false;

    @Override
    protected void interpolate(double v) {
        if (frontToBack) {
            if (!rightToLeft) flipFrontToBackUpsideDown(v);
            else flipFrontToBackRightToLeft(v);
        } else {
            if (!rightToLeft) flipBackToFrontUpsideDown(v);
            else flipBackToFrontRightToLeft(v);
        }
    }

    public void setNode(ImageView imageView) {
        this.node = imageView;
    }

    public void setFrontToBack(boolean frontToBack) {
        this.frontToBack = frontToBack;
    }


    private void flipFrontToBackUpsideDown(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setImage(frontImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleY(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
           node.setImage(backOfCard);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleY(v);
        }
    }

    private void flipBackToFrontUpsideDown(double v) {
        if (v <= 0.5) {
            v *= 2;
           node.setImage(backOfCard);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleY(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
           node.setImage(frontImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleY(v);
        }
    }

    private void flipFrontToBackRightToLeft(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setImage(frontImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleX(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
            node.setImage(backOfCard);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleX(v);
        }
    }

    private void flipBackToFrontRightToLeft(double v) {
        if (v <= 0.5) {
            v *= 2;
            node.setImage(backOfCard);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * v);
            ((DropShadow) node.getEffect()).setOffsetY(-10 * v);
            ((DropShadow) node.getEffect()).setHeight(50 * v);
            ((DropShadow) node.getEffect()).setWidth(50 * v);
            node.setScaleX(1 - v);
        } else {
            v -= 0.5;
            v *= 2;
            node.setImage(frontImage);
            ((DropShadow) node.getEffect()).setOffsetX(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setOffsetY(-10 * (1 - v));
            ((DropShadow) node.getEffect()).setHeight(50 * (1 - v));
            ((DropShadow) node.getEffect()).setWidth(50 * (1 - v));
            node.setScaleX(v);
        }
    }

    public void setFrontImage(Image cardImage) {
        this.frontImage= cardImage;
    }

    public void setBackOfCard(Image backOfCard) {
       this.backOfCard= backOfCard;
    }

    public ImageView getNode() {
        return node;
    }

    public Image getBackOfCard() {
        return backOfCard;
    }

    public Image getFrontImage() {
        return frontImage;
    }

    public boolean isFrontToBack() {
        return frontToBack;
    }

    public boolean isRightToLeft() {
        return rightToLeft;
    }

    public void setRightToLeft(boolean rightToLeft) {
        this.rightToLeft = rightToLeft;
    }
}

