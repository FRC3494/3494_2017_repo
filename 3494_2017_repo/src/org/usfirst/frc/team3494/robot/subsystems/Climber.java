package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
	private DoubleSolenoid pto; // zarya named it

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
			this.motor.set(0.4);
		} else if (dir.equals(DriveDirections.DOWN) && !this.driveTrainMode) {
			this.motor.set(-0.4);
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

	/**
	 * Engages or disengages the drivetrain from the climber. Note that with the
	 * drivetrain engaged controlling the climber by this subsystem becomes
	 * impossible (you must use {@link Drivetrain} instead.)
	 * 
	 * @see Drivetrain
	 * @param value
	 *            The state to set the PTO piston in.
	 */
	public void setPTO(Value value) {
		this.pto.set(value);
		if (value.equals(Value.kOff) || value.equals(Value.kForward)) {
			this.driveTrainMode = true;
		} else {
			this.driveTrainMode = false;
		}
	}

	public void disengagePTO() {
		this.setPTO(Value.kForward);
	}

	public void engagePTO() {
		this.setPTO(Value.kReverse);
	}

	public boolean getState() {
		return this.driveTrainMode;
	}
}
