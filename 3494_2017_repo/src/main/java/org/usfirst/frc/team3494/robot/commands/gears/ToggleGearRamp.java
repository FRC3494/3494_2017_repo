package org.usfirst.frc.team3494.robot.commands.gears;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3494.robot.Robot;

/**
 * Toggles the gear ramp.
 */
public class ToggleGearRamp extends Command {

    public ToggleGearRamp() {
        requires(Robot.gearTake);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.gearTake.getRampState().equals(DoubleSolenoid.Value.kForward)) {
            Robot.gearTake.setRamp(DoubleSolenoid.Value.kReverse);
        } else {
            Robot.gearTake.setRamp(DoubleSolenoid.Value.kForward);
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
