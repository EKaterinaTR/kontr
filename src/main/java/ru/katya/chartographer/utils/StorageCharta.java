package ru.katya.chartographer.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.katya.chartographer.models.Charta;
import ru.katya.chartographer.models.ChartaPart;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


@Component
public class StorageCharta {

    @Value("${storage}")
    private String storagePath;


    public Charta createCharta(Charta charta) {
        String name = UUID.randomUUID() + ".bmp";
        BufferedImage img = createImage(charta.getWidth(), charta.getHeight());
        saveImage(img, getPathFile(name));
        charta.setName(name);
        return charta;
    }

    private BufferedImage createImage(int sizeX, int sizeY) {
        BufferedImage image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                image.setRGB(x, y, Color.BLACK.getRGB());
            }
        }
        return image;
    }

    private void saveImage(BufferedImage image, String path) {
        try {
            ImageIO.write(image, "bmp", new File("pp.bmp"));
            ImageIO.write(image, "bmp", new File(path));
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private String getPathFile(String name) {
        return storagePath.substring(1) + "/" + name;
    }

    public void getChartaPart(ChartaPart chartaPart, OutputStream outputStream) {
        try {
            BufferedImage image = ImageIO.read(new File(getPathFile(chartaPart.getCharta().getName())));
            ImageIO.write(createPartCharta(image, chartaPart), "bmp", outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private BufferedImage createPartCharta(BufferedImage image, ChartaPart chartaPart) {
        BufferedImage partImage = new BufferedImage(chartaPart.getWidth(), chartaPart.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < chartaPart.getWidth(); x++) {
            for (int y = 0; y < chartaPart.getHeight(); y++) {
                partImage.setRGB(x, y, image.getRGB(x + chartaPart.getX(), y + chartaPart.getY()));
            }
        }
        return partImage;
    }

    public void changeCharte(ChartaPart chartaPart, BufferedImage newPartCharta) {
        try {
            BufferedImage image = ImageIO.read(new File(getPathFile(chartaPart.getCharta().getName())));
            ImageIO.write(newVersionCharta(image, chartaPart, newPartCharta),
                    "bmp",
                    new File(getPathFile(chartaPart.getCharta().getName())));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private BufferedImage getImage(MultipartFile file) {
        try {
            return ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new NoBMPFileException();
        }
    }

    private BufferedImage newVersionCharta(BufferedImage oldImage, ChartaPart chartaPart, BufferedImage newPart) {
        BufferedImage newImage = oldImage;
        for (int x = 0; x < chartaPart.getWidth(); x++) {
            for (int y = 0; y < chartaPart.getHeight(); y++) {
                newImage.setRGB(x + chartaPart.getX(), y + chartaPart.getY(), newPart.getRGB(x, y));
            }
        }
        return newImage;
    }


    public void delete(Charta charta) {
        File file = new File(getPathFile(charta.getName()));
        file.delete();
    }
}
