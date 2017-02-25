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

	public void setRamp(Value value) {
		this.rampenoid.set(value);
	}

	public void setGrasp(Value value) {
		this.openandclose.set(value);
	}

	public void releaseGear() {
		this.setGrasp(Value.kForward);
	}

	public void closeHolder() {
		this.setGrasp(Value.kReverse);
	}
	
	public Value getRampState() {
		return this.rampenoid.get();
	}
	
	public Value getGearState() {
		return this.openandclose.get();
	}
}
