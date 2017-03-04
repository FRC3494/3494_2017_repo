package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Conveyer subsystem. Contains all methods for controlling the robot's
 * conveyer.
 * 
 * @since 0.0.4
 */
public class Conveyer extends Subsystem implements IMotorizedSubsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Talon convey;

	public Conveyer() {
		this.convey = new Talon(RobotMap.CONVEYER_M);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	@Override
	public void stopAll() {
		this.convey.stopMotor();
	}

	@Override
	public void setAll(double speed) {
		this.convey.set(speed);
	}

	/**
	 * Runs the conveyer in the specified direction. Only takes
	 * {@link org.usfirst.frc.team3494.robot.DriveDirections#FORWARD
	 * DriveDirections.FORWARD} and
	 * {@link org.usfirst.frc.team3494.robot.DriveDirections#BACKWARD
	 * DriveDirections.BACKWARD} (anything else will stop the conveyer.).
	 * 
	 * @see org.usfirst.frc.team3494.robot.DriveDirections
	 * @param dir
	 *            The direction to run the
	 */
	public void runConveyer(DriveDirections dir) {
		if (dir.equals(DriveDirections.FORWARD)) {
			this.setAll(0.5);
		} else if (dir.equals(DriveDirections.BACKWARD)) {
			this.setAll(-0.5);
		} else {
			this.stopAll();
		}
	}
}
