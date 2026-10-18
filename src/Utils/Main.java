package Utils;

import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.bytedeco.ffmpeg.global.avutil;

public class Main {
    public static void main(String[] args) {
        avutil.av_log_set_level(avutil.AV_LOG_QUIET);
        int FrameCount=999;
        if(args.length>0) {FrameCount=Integer.parseInt(args[0]);}
        int Threshold= 200;
        int MinBlobSize=10;
        double MaxDelta=20;
        double DeltaT=0.5;
        int id=0;

        String fileName="atomic.avi";
        String fileName2="Brownian Motion.avi";

        InOutUtils inOutUtils= new InOutUtils(fileName);

        KDTree previousBlobs=null;

        double DistanceSum=0;
        double DistanceCount=0;
        double[] Distances = {0,0};

        while(inOutUtils.HasNextFrame()&&FrameCount>0)
        {
            System.out.println("Frame ID: "+id);

            FrameCount--;
            BufferedImage image =inOutUtils.GetNextFrame();
            if(image!=null) {

                BlobFinder blobfinder = new BlobFinder(image.getWidth(), image.getHeight(), Threshold);
                KDTree Blobs = blobfinder.FindBlobs(image, MinBlobSize);
                inOutUtils.WriteImage(image, id);

                if(previousBlobs!=null)
                {
                    Distances = Blobs.GetDistanceSum(previousBlobs,MaxDelta);
                }
                DistanceSum += Distances[0];
                DistanceCount+=Distances[1];

                id++;
                previousBlobs = Blobs;

            }
        }

        final double AverageDistance=DistanceSum/(2*DistanceCount);
        final double SelfDifusion =AverageDistance/(2*DeltaT);
        final double BotsmanConst=(6*Math.PI* 9.135*Math.pow(10,-4)*0.5*Math.pow(10,-6)*SelfDifusion);
        final double AvogadrosNumber=8.31457/(BotsmanConst/297);


        System.out.println("Ats: "+AvogadrosNumber );






    }
}