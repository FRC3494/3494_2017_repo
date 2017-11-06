package org.usfirst.frc.team3494.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3494.robot.RobotMap;

/**
 * Subsystem that controls the climber's PTO. Done to help minimize latency.
 * It's not right, but we do what we must because we can.
 */
public class ClimberPTO extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private boolean driveTrainMode;
    private Solenoid pto; // zarya named it

    public ClimberPTO() {
        super("Climber PTO");
        pto = new Solenoid(RobotMap.CLIMBER_PTO);
        pto.set(true);
        driveTrainMode = false;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    public boolean getState() {
        return driveTrainMode;
    }

    /**
     * Engages or disengages the drivetrain from the climber. Note that with the
     * drivetrain engaged controlling the climber by this subsystem becomes
     * impossible (you must use {@link Drivetrain} instead.)
     *
     * @param value The state to set the PTO piston in.
     * @see Drivetrain
     */
    public void setPTO(boolean value) {
        pto.set(value);
        driveTrainMode = value;
    }

    public void disengagePTO() {
        setPTO(false);
    }

    public void engagePTO() {
        setPTO(true);
    }

}
