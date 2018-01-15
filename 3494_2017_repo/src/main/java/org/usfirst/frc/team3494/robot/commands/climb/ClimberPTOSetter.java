package org.usfirst.frc.team3494.robot.commands.climb;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3494.robot.Robot;

/**
 * Toggles the climber from drivetrain to climb motor.
 */
public class ClimberPTOSetter extends Command {

    private boolean b;

    public ClimberPTOSetter(boolean engage) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.pto);
        requires(Robot.kompressor);
        requires(Robot.driveTrain);
        b = engage;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        SmartDashboard.putBoolean("PTO Engaged", b);
        if (b) {
            Robot.pto.engagePTO();
            Robot.kompressor.compress.stop();
            for (TalonSRX c : Robot.driveTrain.leftSide) {
                c.configPeakCurrentLimit(35, 10);
                c.enableCurrentLimit(true);
            }
            for (TalonSRX c : Robot.driveTrain.rightSide) {
                c.configPeakCurrentLimit(35, 10);
                c.enableCurrentLimit(true);
            }
        } else {
            Robot.pto.disengagePTO();
            Robot.kompressor.compress.start();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
