package Main_Package.NEAT.Genome;

import java.util.HashMap;

public class DisplayConnection {
    // a display connection class only for display purposes
    // does not effect the working of the NEAT package

    private int nGeneFrom;
    private int nGeneTo;
    private HashMap<Integer, Integer> ConnectionPair = new HashMap<>();
    private double weight;
    private boolean enabled;

    public DisplayConnection(int nGeneFrom, int gGeneTo, double weight, boolean enabled) {
        this.nGeneFrom = nGeneFrom;
        this.nGeneTo = gGeneTo;
        this.weight = weight;
        this.enabled = enabled;
    }

    // normal getters and setters


    public int getnGeneFrom() {
        return nGeneFrom;
    }

    public void setnGeneFrom(int nGeneFrom) {
        this.nGeneFrom = nGeneFrom;
    }

    public int getnGeneTo() {
        return nGeneTo;
    }

    public void setnGeneTo(int nGeneTo) {
        this.nGeneTo = nGeneTo;
    }

    public HashMap<Integer, Integer> getConnectionPair() {
        return ConnectionPair;
    }

    public void setConnectionPair(HashMap<Integer, Integer> connectionPair) {
        ConnectionPair = connectionPair;
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
}
