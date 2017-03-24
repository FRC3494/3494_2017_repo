package org.usfirst.frc.team3494.robot.commands.auto.tests;

import org.usfirst.frc.team3494.robot.Robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		Robot.driveTrain.leftSide[0].changeControlMode(TalonControlMode.Speed);
		Robot.driveTrain.leftSide[0].setProfile(0);
		Robot.driveTrain.leftSide[0].setF(0);
		Robot.driveTrain.leftSide[0].setP(1);
		Robot.driveTrain.leftSide[0].setI(0);
		Robot.driveTrain.leftSide[0].setD(0);

		Robot.driveTrain.rightSide[0].changeControlMode(TalonControlMode.Speed);
		Robot.driveTrain.rightSide[0].setProfile(0);
		Robot.driveTrain.rightSide[0].setF(0);
		Robot.driveTrain.rightSide[0].setP(1);
		Robot.driveTrain.rightSide[0].setI(0);
		Robot.driveTrain.rightSide[0].setD(0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		SmartDashboard.putNumber("left: ", Robot.driveTrain.leftSide[0].getSpeed());
		SmartDashboard.putNumber("right:", Robot.driveTrain.rightSide[0].getSpeed());
		Robot.driveTrain.leftSide[0].set(2000);
		Robot.driveTrain.leftSide[1].set(0.4);
		Robot.driveTrain.leftSide[2].set(0.4);
		Robot.driveTrain.rightSide[0].set(-2500);
		Robot.driveTrain.rightSide[1].set(-0.4);
		Robot.driveTrain.rightSide[2].set(-0.4);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
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
