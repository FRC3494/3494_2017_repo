package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearTake extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// Ramp and state
	/**
	 * The solenoid that controls the ramp on the gear intake. Should stay
	 * forward most of the time.
	 */
	private DoubleSolenoid rampenoid;
	/**
	 * The solenoid that holds the gear or drops it.
	 */
	private DoubleSolenoid openandclose;

	public GearTake() {
		super();
		this.rampenoid = new DoubleSolenoid(RobotMap.GEAR_RAMP_CHONE, RobotMap.GEAR_RAMP_CHTWO);
		this.openandclose = new DoubleSolenoid(RobotMap.GEAR_GRASP_CHONE, RobotMap.GEAR_GRASP_CHTWO);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets the position of the intake ramp.
	 * 
	 * @param value
	 *            The position to set the ramp to.
	 */
	public void setRamp(Value value) {
		this.rampenoid.set(value);
	}

	/**
	 * Sets the position of the actual gear holder.
	 * 
	 * @param value
	 *            The position to set the holder to.
	 */
	public void setGrasp(Value value) {
		this.openandclose.set(value);
	}

	/**
	 * Releases the gear with a call to {@link GearTake#setGrasp}.
	 */
	public void releaseGear() {
		this.setGrasp(Value.kForward);
	}

	/**
	 * Closes the gear holder with a call to {@link GearTake#setGrasp}.
	 */
	public void closeHolder() {
		this.setGrasp(Value.kReverse);
	}

	/**
	 * Gets the state of the intake ramp solenoid. Equivalent to
	 * {@code this.rampenoid.get()}, but {@link GearTake#rampenoid} is private.
	 * 
	 * @return The value of {@code this.rampenoid.get()}.
	 */
	public Value getRampState() {
		return this.rampenoid.get();
	}

	/**
	 * Gets the state of the gear holder. Equivalent to
	 * {@code this.openandclose.get()}, but {@link GearTake#openandclose} is
	 * private.
	 * 
	 * @return The value of {@code this.openandclose.get()}.
	 */
	public Value getGearState() {
		return this.openandclose.get();
	}
}
