package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.sensors.LineBreak;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Gear holder subsystem. Contains all methods for controlling the robot's gear
 * holder.
 */
public class GearTake_2 extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	// Ramp and state
	/**
	 * The solenoid that controls the ramp on the gear intake. Should stay
	 * forward most of the time.
	 */
	private DoubleSolenoid rampenoid;

	public LineBreak lb;

	public GearTake_2() {
		super();
		this.rampenoid = new DoubleSolenoid(RobotMap.GEAR_RAMP_CHONE, RobotMap.GEAR_RAMP_CHTWO);
		this.lb = new LineBreak(0);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	/**
	 * Sets the position of the intake ramp.
	 * 
	 * @param value
	 *            The position to set the ramp to.
	 * @deprecated use {@linkplain GearTake_2#setGrasp(Value)} instead.
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
		this.rampenoid.set(value);
	}

	/**
	 * Releases the gear with a call to {@link GearTake_2#setGrasp}.
	 */
	public void releaseGear() {
		this.setGrasp(Value.kForward);
	}

	/**
	 * Closes the gear holder with a call to {@link GearTake_2#setGrasp}.
	 */
	public void closeHolder() {
		this.setGrasp(Value.kReverse);
	}

	/**
	 * Gets the state of the intake ramp solenoid. Equivalent to
	 * {@code this.rampenoid.get()}, but {@link GearTake_2#rampenoid} is
	 * private.
	 * 
	 * @return The value of {@code this.rampenoid.get()}.
	 * @deprecated use getGearState instead
	 */
	public Value getRampState() {
		return this.rampenoid.get();
	}

	/**
	 * Gets the state of the gear holder. Equivalent to
	 * {@code this.rampenoid.get()}, but {@link GearTake_2#rampenoid} is
	 * private.
	 * 
	 * @return The value of {@code this.rampenoid.get()}.
	 */
	public Value getGearState() {
		return this.rampenoid.get();
	}
}
