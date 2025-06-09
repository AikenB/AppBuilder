public class MathCalculator {
    
    private double slope;
    private double yIntercept;

    //calculates the Slope of two points
    public double calculateSlope(double x1, double y1, double x2, double y2) {

        slope = (y2 - y1) / (x2 - x1);
        return slope;
    }

    //calculates the linear equation from 2 points
    public String calculateLinearEquation(double x1, double y1, double x2, double y2) {
        slope = calculateSlope(x1, y1, x2, y2);
        yIntercept = y1 - (slope * x1); //subsitutes a point x1, y1  into the equation y = mx + b to get b
        
        //decides how it will return it based on whether the y intercept is positive or negative
        if (yIntercept >= 0) {
            return slope + "x + " + yIntercept;
        }
        else if (yIntercept < 0){
            return slope + "x - " + Math.abs(yIntercept);
        }
        else {
            return "no equation found";
        }
    }

    //calculates the linear equation from a point and slope
    public String calculateWithPointSlope(double x1, double y1, double m) {
        //we are using the point slope formula y - y1 = m(x - x1) to get the linear equation
        slope = m;
        //according to the point slope formula, y = m(x - x1) + y1, so this calculates the y intercept component of the equation
        yIntercept = -1 * slope * x1 + y1; 

        //decides how it will return it based on whether the y intercept is positive or negative
        if (yIntercept >= 0) {
            return slope + "x + " + yIntercept;
        }
        else if (yIntercept < 0 ) {
            return slope + "x - " + Math.abs(yIntercept);
        }
        else {
            return "no equation found";
        } 
    }
    
    public double getSlope(){ //gets the slope since the variable for slope is private
        return slope;
    }
    public double getYIntercept(){ //gets the y intercept since the variable for y intercept is private
        return yIntercept;
    }

    //without parameters
    double input1;
    double input2;
    public void exampleMethod(){
        System.out.println(input1);
        System.out.println(input2);
    }
    //with parameters
    public void exampleMethod(double input1, double input2){
        System.out.println(input1);
        System.out.println(input2);
    }
    

}