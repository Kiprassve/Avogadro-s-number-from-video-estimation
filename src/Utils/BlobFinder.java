package Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class BlobFinder {

    boolean[][] PixelsVisited=null;
    int Threshold;
    public BlobFinder(int x,int y,int Threshold)
    {
        PixelsVisited=new boolean[x][y];
        this.Threshold=Threshold;
    }


    public KDTree FindBlobs(BufferedImage image, int blobSize)
    {
        ResetVisitedPixels();
       KDTree blobs=new KDTree();
        for(int i=0; i<image.getWidth(); i++)
        {
            for(int j=0; j<image.getHeight(); j++)
            {
                if(!PixelsVisited[i][j]) {

                    if (OverThreshold( image, i,  j)) {
                        ArrayList<int[]> pixels=new ArrayList<>();
                        RecursivelyDefineBlob(i,j,image,pixels);
                        image.setRGB(i, j, 16777215);
                        int[] CenterOfMass= CalculateCenterOfMass(pixels);
                        image.setRGB(CenterOfMass[0], CenterOfMass[1], 16711680);
                        if(pixels.size()>=blobSize) {
                            blobs.Insert(new Blob(CenterOfMass, pixels.size()));
                            System.out.println("X:"+CenterOfMass[0]+" Y:"+CenterOfMass[1]+" size:"+pixels.size());
                        }
                    } else {
                        image.setRGB(i, j, 0);
                    }
                }
            }
        }
        return blobs;
    }
    public void RecursivelyDefineBlob(int i,int j,BufferedImage image,ArrayList<int[]> pixels) /// Change so that it returns a blob
    {
        pixels.add(new int[]{i,j});
        PixelsVisited[i][j]=true;
        int[][] variants={{1,0},{-1,0},{0,1},{0,-1}};
        int CurrentIndex=0;

        while(CurrentIndex<pixels.size())
        {
            int x=pixels.get(CurrentIndex)[0];
            int y=pixels.get(CurrentIndex)[1];

            for(int[] var:variants)
            {
                if(x+var[0]>=0&&y+var[1]>=0&&x+var[0]<PixelsVisited.length&&y+var[1]< PixelsVisited[0].length&&!PixelsVisited[x+var[0]][y+var[1]]){
                    if (OverThreshold( image, x+var[0],  y+var[1])) {
                        image.setRGB(x+var[0], y+var[1], 13158655);
                        pixels.add(new int[]{x+var[0], y+var[1]});
                    } else {image.setRGB(x+var[0], y+var[1], 0);}
                    PixelsVisited[x+var[0]][y+var[1]]=true;
                }
            }


            CurrentIndex++;
        }
    }
    public int[] CalculateCenterOfMass(ArrayList<int[]> pixels)
    {
         int[] CenterOfMass = new int[]{0,0};
        for(int i=0; i<pixels.size(); i++){
            CenterOfMass[0]+=pixels.get(i)[0];
            CenterOfMass[1]+=pixels.get(i)[1];
        }
        CenterOfMass[0]/=pixels.size();
        CenterOfMass[1]/=pixels.size();
        return CenterOfMass;
    }
    public boolean OverThreshold(BufferedImage image,int i, int j)
    {
        int pixel = image.getRGB(i, j);
        Color color = new Color(pixel);
        double lum = intensity(color);
        return lum >= Threshold;
    }

    private static double intensity(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return (299*r + 587*g + 114*b) / 1000.0;
    }

    private void ResetVisitedPixels()
    {
        for(boolean[] i: PixelsVisited)
        {
            Arrays.fill(i,false);
        }
    }



}
