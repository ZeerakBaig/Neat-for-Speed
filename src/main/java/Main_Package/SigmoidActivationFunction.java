package Main_Package;

public class SigmoidActivationFunction implements  ActivationFunction{
    @Override
    public double activate(double x) {
        double activation = 1D/ (1D + Math.exp(-4.9 * x));
        return activation;
    }
}
