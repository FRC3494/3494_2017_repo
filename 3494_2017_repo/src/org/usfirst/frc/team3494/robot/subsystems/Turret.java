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
	private Encoder turretRingEnc;
	private Encoder shooterEnc_lower;
	private Encoder shooterEnc_upper;
	private CANTalon shooterUpper;
	private CANTalon shooterLower;
	private CANTalon turretRing;
	public Turret() {
		super("Turret");
		this.turretRing = new CANTalon(RobotMap.TURRET_RING);
		this.shooterUpper = new CANTalon(RobotMap.TURRET_UPPER);
		this.shooterLower = new CANTalon(RobotMap.TURRET_LOWER);
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
		this.turretRing.set(0);
	}

	@Override
	public void setAll(double speed) {
		this.shooterLower.set(speed);
		this.shooterUpper.set(speed);
		this.turretRing.set(speed);
	}
}
