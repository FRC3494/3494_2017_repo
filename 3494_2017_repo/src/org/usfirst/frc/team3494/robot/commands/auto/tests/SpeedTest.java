package org.usfirst.frc.team3494.robot.commands.auto.tests;

import org.usfirst.frc.team3494.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Test for CAN Talon speed mode.
 */
public class SpeedTest extends Command {
	public SpeedTest() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		for (CANTalon t : Robot.driveTrain.leftSide) {
			t.changeControlMode(TalonControlMode.Speed);
			t.setProfile(0);
			t.setF(0);
			t.setP(0.1);
			t.setI(0);
			t.setD(0);
		}
		for (CANTalon t : Robot.driveTrain.rightSide) {
			t.changeControlMode(TalonControlMode.Speed);
			t.setProfile(0);
			t.setF(0);
			t.setP(0.1);
			t.setI(0);
			t.setD(0);
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		for (CANTalon t : Robot.driveTrain.leftSide) {
			t.set(1000);
		}
		for (CANTalon t : Robot.driveTrain.rightSide) {
			t.set(-1000);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false; // ten seconds ish
	}

	// Called once after isFinished returns true
	protected void end() {
		for (CANTalon t : Robot.driveTrain.leftSide) {
			t.changeControlMode(TalonControlMode.PercentVbus);
		}
		for (CANTalon t : Robot.driveTrain.rightSide) {
			t.changeControlMode(TalonControlMode.PercentVbus);
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
