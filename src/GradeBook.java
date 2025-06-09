public class GradeBook{
    //instance variables
    private double p1Grade;
    private double p2Grade;
    private double p3Grade;
    private double p4Grade;
    private double p5Grade;
    private double p6Grade;
    private double p7Grade;
    
    //constructor
    public GradeBook(){
        resetGrades();
        
    }

    //sets the grades
    public void setP1Grade(double grade){
        p1Grade = grade;
    }
    public void setP2Grade(double grade){
        p2Grade = grade;
    }
    public void setP3Grade(double grade){
        p3Grade = grade;
    }
    public void setP4Grade(double grade){
        p4Grade = grade;
    }
    public void setP5Grade(double grade){
        p5Grade = grade;
    }
    public void setP6Grade(double grade){
        p6Grade = grade;
    }
    public void setP7Grade(double grade){
        p7Grade = grade;
    }

    //gets the grades
    public double getP1Grade(){
        return p1Grade;
    }
    public double getP2Grade(){
        return p2Grade;
    }
    public double getP3Grade(){
        return p3Grade;
    }
    public double getP4Grade(){
        return p4Grade;
    }
    public double getP5Grade(){
        return p5Grade;
    }
    public double getP6Grade(){
        return p6Grade;
    }
    public double getP7Grade(){
        return p7Grade;
    }
    //resets the grades to 100
    public void resetGrades(){
        p1Grade = 100;
        p2Grade = 100;
        p3Grade = 100;
        p4Grade = 100;
        p5Grade = 100;
        p6Grade = 100;
        p7Grade = 100;
    }
    //calculates the average of the grades
    public double calculateAverage(){
        double average = (p1Grade + p2Grade + p3Grade + p4Grade + p5Grade + p6Grade + p7Grade) / 7;
    
        return average;
    }
}