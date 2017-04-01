package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Turret subsystem. Contains methods for controlling the turret.
 * 
 * @since 0.0.0
 */
public class Turret extends Subsystem implements IMotorizedSubsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Encoder shooterEnc_lower;
	private Encoder shooterEnc_upper;

	private CANTalon shooterUpper;
	private CANTalon shooterLower;

	private CANTalon unscrambler;

	public Turret() {
		super("Turret");
		this.shooterUpper = new CANTalon(RobotMap.TURRET_UPPER);
		this.shooterLower = new CANTalon(RobotMap.TURRET_LOWER);
		this.unscrambler = new CANTalon(RobotMap.UNSCRAMBLER);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	@Override
	public void stopAll() {
		this.shooterLower.set(0);
		this.shooterUpper.set(0);
		this.unscrambler.set(0);
	}

	@Override
	public void setAll(double speed) {
		this.shooterLower.set(speed);
		this.shooterUpper.set(speed);
		this.unscrambler.set(speed);
	}

	public double getDistance(TurretEncoders enc) {
		if (enc.equals(TurretEncoders.BOTTOM)) {
			return this.shooterEnc_lower.getDistance();
		} else {
			return this.shooterEnc_upper.getDistance();
		}
	}

	/**
	 * Runs the turret at the specified power. Screw you, negative numbers are
	 * not allowed.
	 * 
	 * @param power
	 *            The power to run the shooter at. It will be run by absolute
	 *            value first.
	 */
	public void shoot(double power) {
		power = Math.abs(power);
		this.shooterUpper.set(power);
		this.shooterLower.set(power);
	}
}
