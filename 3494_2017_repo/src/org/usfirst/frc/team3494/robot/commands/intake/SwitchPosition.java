package org.usfirst.frc.team3494.robot.commands.intake;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Deploys the intake for the start of teleop.
 * 
 * @see org.usfirst.frc.team3494.robot.subsystems.Intake
 */
public class SwitchPosition extends Command {

	public SwitchPosition() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!Robot.intake.isDeployed) {
			Robot.intake.pushForward();
		} else {
			Robot.intake.retract();
		}
		SmartDashboard.putBoolean("Intake Deployed", Robot.intake.isDeployed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
