
public class Arm {
    
    
    private double maxSpeedDegreesPerSecond = 120;
    private double upperAngleLimitDegrees;
    private double lowerAngleLimitDegrees;

    public Arm(double upperAngleLimitDegrees, double lowerAngleLimitDegrees) {
        // Constructor code here
        // Initialize arm parameters
        this.upperAngleLimitDegrees = upperAngleLimitDegrees;
        this.lowerAngleLimitDegrees = lowerAngleLimitDegrees;
    }

    public double getMaxSpeedDegreesPerSecond() {
        return maxSpeedDegreesPerSecond;
    }
    public void setMaxSpeedDegreesPerSecond(double degreesPerSecond) {
        maxSpeedDegreesPerSecond = degreesPerSecond;
    }
    public double getUpperAngleLimitDegrees() {
        return upperAngleLimitDegrees;
    }
    public double getLowerAngleLimitDegrees() {
        return lowerAngleLimitDegrees;
    }


    public void moveToAngle(double angleDegrees) {
        if (angleDegrees < upperAngleLimitDegrees && angleDegrees > lowerAngleLimitDegrees) {
            System.out.println("Arm moving to " + angleDegrees + " degrees");
        }
        else System.out.println("Error: Angle out of range");
        
    }
    public void checkSpeed(double speedDegreesPerSecond) {
        if (speedDegreesPerSecond > maxSpeedDegreesPerSecond) {
            System.out.println("Error: Speed exceeds maximum limit");
        }
        else System.out.println("Speed is within limits");
    }


}

