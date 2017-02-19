package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber subsystem. Contains methods for controlling the rope climber.
 * 
 * @since 0.0.0
 */
public class Climber extends Subsystem implements IMotorizedSubsystem {
	
	private Talon motor;
	private boolean driveTrainMode;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Climber() {
		super("Climber");
		this.motor = new Talon(RobotMap.CLIMBER_MOTOR);
		this.driveTrainMode = false;
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Climbs in the specified direction.
	 * 
	 * @param dir
	 *            The direction to climb. Setting this to anything other than
	 *            {@link DriveDirections#UP} or {@link DriveDirections#DOWN}
	 *            will stop the climber.
	 */
	public void climb(DriveDirections dir) {
		if (dir.equals(DriveDirections.UP) && !this.driveTrainMode) {
			motor.set(0.4);
		} else if (dir.equals(DriveDirections.DOWN) && !this.driveTrainMode) {
			motor.set(-0.4);
		} else {
			// stop the climber
			motor.set(0);
		}
	}

	@Override
	public void stopAll() {
		motor.set(0);
	}

	@Override
	public void setAll(double speed) {
		motor.set(speed);
	}
}
