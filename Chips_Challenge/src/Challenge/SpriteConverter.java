package Challenge;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;

class SpriteConverter {

    static Image resize(Image image, int height, int width) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Resize
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        // Capture it? I think
        SnapshotParameters param = new SnapshotParameters();

        param.setFill(Color.TRANSPARENT);

        return imageView.snapshot(param, null);

    }

    static Image rotate(Image image, int direction) {

        // Read Image
        ImageView imageView = new ImageView(image);

        // Rotate
        imageView.setRotate(90 * direction);

        // Capture it? I think
        SnapshotParameters param = new SnapshotParameters();

        param.setFill(Color.TRANSPARENT);

        return imageView.snapshot(param, null);
    }

    static Image changeColour(Image image, Color colour) {

        // This is basically impossible

        int newColour = 0; // this is wrong

        ImageView im = new ImageView(image);

        try {
            BufferedImage bufferedImage = ImageIO.read((ImageInputStream) image);

            WritableImage wr = new WritableImage((int) image.getWidth(), (int) image.getHeight());

            PixelWriter pw = wr.getPixelWriter();

            for (int y = 0 ; y < image.getHeight() ; y ++ ) {
                for (int x = 0 ; x < image.getWidth() ; x ++ ) {

                    int p = bufferedImage.getRGB(x, y);

                    if (-1 == p) {
                        // Pixel is white
                        bufferedImage.setRGB(x, y, newColour);
                    }

                    pw.setArgb(x, y, bufferedImage.getRGB(x, y));

                }
            }

            return new ImageView(wr).getImage();

        } catch (Exception e) {
            // Nothing
        }

        return image;

    }

}
