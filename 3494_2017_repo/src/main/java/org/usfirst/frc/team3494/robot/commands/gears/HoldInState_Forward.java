package org.usfirst.frc.team3494.robot.commands.gears;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3494.robot.Robot;

/**
 * Holds the gear holder in the given position <em>until canceled.</em>
 */
public class HoldInState_Forward extends Command {

    public HoldInState_Forward() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.gearTake);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.gearTake.setGrasp(Value.kForward);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.gearTake.setGrasp(Value.kOff);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
