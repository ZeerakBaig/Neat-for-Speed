package Main_Package.NEAT.Genome;

// Cloneable interface allows the implementing class to have its objects to be cloned instead of using new operator
public class Gene implements Cloneable{

    // unique number associated to a gene
    private int innovation_number;
    // weight of the connection
    private double weight;
    // check whether the gene is active or not
    private boolean enabled;
    //the two nodes which is connected by a connection gene
    private int nodeFrom;
    private int nodeTo;

    // for graphics currently not done
    private double x_coordinate;
    private double y_coordinate;

    // constructor for our gene class
    public Gene(int innovation_number, double weight, boolean enabled, int nodeFrom, int nodeTo) {
        this.innovation_number = innovation_number;
        this.weight = weight;
        this.enabled = enabled;
        this.nodeFrom = nodeFrom;
        this.nodeTo = nodeTo;
    }

    // implementing cloneable interface method
    // method used to clone a gene (we will be working with copies of genes not changing the original gene)
    @Override
    protected Gene clone()
    {
        // used to assign a gene to new object without instantiation
        Gene newGene = new Gene(innovation_number, weight, enabled, nodeFrom, nodeTo);
        return newGene;
    }
    // implementing to string method which simply displays details of the gene
    @Override
    public String toString()
    {
        String strGeneDetails = "Gene (Innovation number = " + innovation_number + ", Node From = " + nodeFrom + " Node To ="+ nodeTo + " Weight =" + weight + "Enabled =" + enabled +")";
        return strGeneDetails;
    }


    // getters and setters
    public int getInnovation_number() {
        return innovation_number;
    }

    public void setInnovation_number(int innovation_number) {
        this.innovation_number = innovation_number;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getNodeFrom() {
        return nodeFrom;
    }

    public void setNodeFrom(int nodeFrom) {
        this.nodeFrom = nodeFrom;
    }

    public int getNodeTo() {
        return nodeTo;
    }

    public void setNodeTo(int nodeTo) {
        this.nodeTo = nodeTo;
    }

    public double getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(double x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public double getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(double y_coordinate) {
        this.y_coordinate = y_coordinate;
    }


}
