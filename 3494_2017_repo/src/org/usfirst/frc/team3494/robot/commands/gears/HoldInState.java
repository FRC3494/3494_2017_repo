package org.usfirst.frc.team3494.robot.commands.gears;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Holds the gear holder in the given position <em>until canceled.</em>
 */
public class HoldInState extends Command {

	public HoldInState() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearTake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.gearTake.setGrasp(Value.kForward);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.gearTake.setGrasp(Value.kReverse);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		this.end();
	}
}
