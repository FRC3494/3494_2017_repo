package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Single piston gear holder.
 */
public class MonoGearTake extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private DoubleSolenoid piston;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		this.piston = new DoubleSolenoid(RobotMap.MONO_GEAR_FORWARD, RobotMap.MONO_GEAR_REVERSE);
	}

	public void setPos(Value v) {
		this.piston.set(v);
	}

	public Value getPos() {
		return this.piston.get();
	}
}
