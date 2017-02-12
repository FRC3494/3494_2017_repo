package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
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
	/**
	 * Constant for the turret ring speed. Placed here for easy editing.
	 */
	private static double turretTurnPower = 0.4;

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

	public double getDistance(TurretEncoders enc) {
		if (enc.equals(TurretEncoders.RING)) {
			return this.turretRingEnc.getDistance();
		} else if (enc.equals(TurretEncoders.BOTTOM)) {
			return this.shooterEnc_lower.getDistance();
		} else {
			return this.shooterEnc_upper.getDistance();
		}
	}

	/**
	 * Turns the turret at a hardcoded speed in the specified direction.
	 * 
	 * @param dir
	 *            The direction to turn in. Defaults to right if you put in
	 *            something stupid like {@link DriveDirections#UP}.
	 */
	public void turnTurret(DriveDirections dir) {
		if (dir.equals(DriveDirections.LEFT)) {
			this.turretRing.set(turretTurnPower);
		} else {
			this.turretRing.set(-turretTurnPower);
		}
	}
}
