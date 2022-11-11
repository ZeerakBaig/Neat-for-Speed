public class TestingAngles {
    public static void main(String[] args)
    {
        System.out.println("Hello are you working");

        // create variable in Degree
        double a = 30;
        double b = 45;

        // convert to radians
        a = Math.toRadians(a);
        b = Math.toRadians(b);

        System.out.println("Radian values a :" + a + " b: " + b);

        // print the sine value
        System.out.println(Math.sin(a));   // 0.49999999999999994
        System.out.println(Math.sin(b));   // 0.7071067811865475

        // sin() with 0 as its argument
        System.out.println(Math.sin(0.0));
    }
}
