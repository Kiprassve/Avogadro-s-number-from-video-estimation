package Utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class InOutUtils {

    private FFmpegFrameGrabber frameGrabber;
    private int CurrentFrame;
    Java2DFrameConverter converter;
    private int FrameCount;
    public InOutUtils(String fileName)
    {
        File videoFile = new File(fileName);
         frameGrabber = new FFmpegFrameGrabber(videoFile.getAbsoluteFile());
         try{frameGrabber.start();}catch (FrameGrabber.Exception e){System.out.println(e.getMessage());}
         CurrentFrame=0;
         converter = new Java2DFrameConverter();
         FrameCount=frameGrabber.getLengthInFrames();
    }

    public BufferedImage GetNextFrame()
    {

        BufferedImage image = null;
        try {
            frameGrabber.setFrameNumber(CurrentFrame);
            Frame frame = frameGrabber.grab();
             image = converter.convert(frame);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        CurrentFrame++;
             return   image;
    }
    public BufferedImage GetFrame(int FrameNumber)
    {
        BufferedImage image = null;
        try{frameGrabber.start();

            frameGrabber.setFrameNumber(FrameNumber);
            Frame frame = frameGrabber.grab();
            image = converter.convert(frame);
            frameGrabber.stop();}
        catch (FrameGrabber.Exception e){System.out.println(e.getMessage());}
        return image;
    }
    public Boolean HasNextFrame()
    {
        return CurrentFrame < FrameCount;
    }


    public void WriteImage(BufferedImage image,int id)
    {
        if(image!=null)
            try{
                ImageIO.write(image, "png", new File("Frames/Frame " + String.format(""+id) + ".png"));}
            catch (IOException ex){System.out.println(ex.getMessage());}
    }
    public void WriteOutBlobs(ArrayList<Blob> blobs, int id)
    {
        System.out.println("Picture ID: "+id);
        for(Blob blob:blobs)
        {
            System.out.println(String.format("Size: %d Xcord: %d Ycord: %d",blob.Mass,blob.CentreOfMass[0],blob.CentreOfMass[1]));
        }
    }
}
