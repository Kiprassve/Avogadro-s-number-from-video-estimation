package Utils;

import java.util.ArrayList;

public class Blob {
    public  int[] CentreOfMass;
    public int Mass;
    public Blob(int[] CentreOfMass, int Mass )
    {
        this.CentreOfMass=CentreOfMass;
        this.Mass=Mass;
    }

    public double CalculateDistance(Blob blob)
    {
        return Math.sqrt(Math.pow((CentreOfMass[0]-blob.CentreOfMass[0]),2)+Math.pow((CentreOfMass[1]-blob.CentreOfMass[1]),2));
    }
}
