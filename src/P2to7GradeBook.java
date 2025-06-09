public class P2to7GradeBook extends GradeBook {
    
    //overrides calculateAverage method to calculate the average of P2 to P7 grades
    @Override
    public double calculateAverage(){
        double average = (getP2Grade() + getP3Grade() + getP4Grade() + getP5Grade() + getP6Grade() + getP7Grade()) / 6;
        return average;
    }

    //overrides the resetGrades method to set P2 to P7 grades to 100
    @Override
    public void resetGrades(){
        setP2Grade(100);
        setP3Grade(100);
        setP4Grade(100);
        setP5Grade(100);
        setP6Grade(100);
        setP7Grade(100);
    }
    //overrides the setter and getter methods for P1 grade to prevent setting or getting it
    @Override
    public void setP1Grade(double grade){
        System.out.println("There is no P1 grade for this grade book!");
    }
    @Override
    public double getP1Grade(){
        System.out.println("There is no P1 grade for this grade book!");
        return 0;
    }
}
