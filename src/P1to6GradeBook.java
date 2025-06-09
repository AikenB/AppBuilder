public class P1to6GradeBook extends GradeBook {
    
    //overrides calculateAverage method to calculate the average of P1 to P6 grades
    @Override
    public double calculateAverage(){
        double average = (getP1Grade() + getP2Grade() + getP3Grade() + getP4Grade() + getP5Grade() + getP6Grade()) / 6;
        return average;
    }

    //overrides the resetGrades method to set P1 to P6 grades to 100
    @Override
    public void resetGrades(){
        setP1Grade(100);
        setP2Grade(100);
        setP3Grade(100);
        setP4Grade(100);
        setP5Grade(100);
        setP6Grade(100);
    }

    //overrides the setter and getter methods for P7 grade to prevent setting or getting it
    @Override 
    public void setP7Grade(double grade){
        System.out.println("There is no P7 grade for this grade book!");
    }
    @Override
    public double getP7Grade(){
        System.out.println("There is no P7 grade for this grade book!");
        return 0;
    }
}
