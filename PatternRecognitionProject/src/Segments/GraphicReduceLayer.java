package Segments;

import PerceptronPackage.Vector;
import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.LinkedList;

public class GraphicReduceLayer {

    String filename;

    public GraphicReduceLayer(String filename) throws IOException {
        this.filename = filename;
    }

    public int[][] getSegment(int index) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename));
        image = cutImage(image);
        Image img = image.getScaledInstance(60, 120, 1);
        image = imageToBuffered(img);
        int[][] arr = new int[10][60];
        for (int y = 0; y < 12; y++) {
            arr[y] = new int[60];
            for (int x = 0; x < image.getWidth(); x++) {
                arr[y+index*12][x] = image.getRGB(x,y+index*12);
            }
        }
        return arr;
    }

    public static Vector getConvolutions(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        image = cutImage(image);
        Image img = image.getScaledInstance(60, 120, 1);
        image = imageToBuffered(img);
        Vector vector = new Vector(file.getName().split("[0-9]")[0]);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                int[][] arr = new int[15][15];
                for (int y = 0; y < 15; y++) {
                    for (int x = 0; x < 15; x++) {
                        int color = image.getRGB(x,y);
                        Color c = Color.getColor(String.format("", color));
                        arr[y][x] = image.getRGB(x,y);
                    }
                }
                vector.add(new Conv2D(arr).rest);
            }
        }
        return vector;
    }


    public static void saveThisData(String filename) throws IOException
    {
        BufferedImage image = ImageIO.read(new File(filename));
        image = cutImage(image);
        Image img = image.getScaledInstance(60,120,1);
        image = imageToBuffered(img);
        ImageIO.write(image, "png",new File("src/Data/Images/result1.png"));
    }

    public static BufferedImage imageToBuffered(Image image)
    {
        BufferedImage bImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return bImage;
    }

    public static BufferedImage cutImage(BufferedImage bufferedImage)
    {
        int firstY = 0;
        for(int y = 0; y < bufferedImage.getHeight(); y++)
        {
            boolean flag = false;
            for(int x = 0; x < bufferedImage.getWidth(); x++)
            {
                Color color = new Color(bufferedImage.getRGB(x,y));
                if (bufferedImage.getRGB(x,y)!=-1)
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
            else firstY++;
        }
        int lastY = bufferedImage.getHeight();
        for(int y = bufferedImage.getHeight()-1; y >= 0; y--)
        {
            boolean flag = false;
            for(int x = 0; x < bufferedImage.getWidth(); x++)
            {
                Color color = new Color(bufferedImage.getRGB(x,y));
                if (bufferedImage.getRGB(x,y)!=-1)
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
            else lastY--;
        }
        int firstX = 0;
        for(int x = 0; x < bufferedImage.getWidth(); x++)
        {
            boolean flag = false;
            for(int y = 0; y < bufferedImage.getHeight(); y++)
            {
                Color color = new Color(bufferedImage.getRGB(x,y));
                if (bufferedImage.getRGB(x,y)!=-1)
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
            else firstX++;
        }
        int lastX = bufferedImage.getWidth();
        for(int x = bufferedImage.getWidth()-1; x >= 0; x--)
        {
            boolean flag = false;
            for(int y = 0; y < bufferedImage.getHeight(); y++)
            {
                Color color = new Color(bufferedImage.getRGB(x,y));
                if (bufferedImage.getRGB(x,y)!=-1)
                {
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
            else lastX--;
        }
        return bufferedImage.getSubimage(firstX, firstY, lastX-firstX, lastY-firstY);
    }
}
