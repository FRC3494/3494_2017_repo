package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber subsystem. Contains methods for controlling the rope climber.
 *
 * @since 0.0.0
 */
public class Climber extends Subsystem implements IMotorizedSubsystem {

	private CANTalon motor;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	public Climber() {
		super("Climber");
		this.motor = new CANTalon(RobotMap.CLIMBER_MOTOR);
		this.motor.setInverted(true);
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
		if (dir.equals(DriveDirections.UP) && !Robot.pto.getState()) {
			this.motor.set(0.35);
		} else if (dir.equals(DriveDirections.DOWN) && !Robot.pto.getState()) {
			this.motor.set(-0.35);
		} else {
			// stop the climber
			this.motor.set(0);
		}
	}

	@Override
	public void stopAll() {
		this.motor.set(0);
	}

	@Override
	public void setAll(double speed) {
		this.motor.set(speed);
	}

	public double getMotorCurrent() {
		return Robot.pdp.getCurrent(RobotMap.CLIMBER_MOTOR_PDP);
	}
}
