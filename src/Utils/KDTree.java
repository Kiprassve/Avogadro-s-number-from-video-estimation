package Utils;

public class KDTree{
    protected Node root = null;

    public KDTree()
    {

    }
    public void Insert(Blob blob)
    {
        root = RecursiveAdd(root, blob, 0);
    }
    public Node RecursiveAdd(Node node,Blob blob,int depth)
    {
        if (node == null) {
            return new Node(blob);
        }
        int cd = depth % 2;
        if (blob.CentreOfMass[cd] < node.element.CentreOfMass[cd]) {
            node.left = RecursiveAdd(node.left, blob, depth + 1);
        } else {
            node.right = RecursiveAdd(node.right, blob, depth + 1);
        }

        return node;
    }
    public double[] GetDistanceSum(KDTree tree,double maxDistance)
    {
        double[] ans= new double[]{0,0};
        RecursiveSum(root,tree,maxDistance,ans);
        return ans;
    }

    public void RecursiveSum(Node node,KDTree tree,double maxDistance,double[] Answer)
    {

        if(node.left!=null)
        {
            RecursiveSum(node.left,tree,maxDistance,Answer);
        }
        if(node.right!=null)
        {
            RecursiveSum(node.right,tree,maxDistance,Answer);
        }
       double dist= tree.GetDistanceOfClosestPoint(node.element);
        if(dist<=maxDistance)
        {
            Answer[0]+= Math.pow(dist* 0.175 * Math.pow(10, -6),2);
            Answer[1]++;
        }


    }

    public double GetDistanceOfClosestPoint(Blob blob)
    {
        Node temp=RecursiveSerch(root,blob,0);
        if(temp==null)return -1;
        return blob.CalculateDistance(temp.element);
    }

    public Node RecursiveSerch(Node node,Blob blob,int depth)
    {
        if(node==null)return null;
        Node nextNode=null;
        Node OtherNode=null;

        if(blob.CentreOfMass[depth%2]<node.element.CentreOfMass[depth%2]) {
            nextNode=node.left;
            OtherNode=node.right;
        }else{
            nextNode=node.right;
            OtherNode=node.left;
        }

        Node temp = RecursiveSerch(nextNode,blob,depth+1);
        Node Best= Closest(blob, temp,node);

        double radiusSquared = Math.pow(blob.CalculateDistance(Best.element),2);
        double dist= blob.CentreOfMass[depth%2]-node.element.CentreOfMass[depth%2];

        if(radiusSquared>=dist*dist)
        {
            temp= RecursiveSerch(OtherNode,blob,depth+1);
            Best= Closest(blob,temp,Best);
        }
        return Best;
    }

    private Node Closest(Blob blob,Node temp,Node node)
    {
        if(temp==null&&node!=null)
        {
            return node;
        }
        if(temp!=null&&node==null)
        {
            return temp;
        }
        if(blob.CalculateDistance(temp.element)>blob.CalculateDistance(node.element))
        {
            return node;
        }
        else{return temp;}
    }

    public boolean IsEmpty()
    {
        return root==null;
    }

    protected static class Node {
        // Elementas
        protected Blob element;
        // Rodyklė į kairįjį pomedį
        protected Node left;
        // Rodyklė į dešinįjį pomedį
        protected Node right;

        protected Node() {

        }
        protected Node(Blob element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
