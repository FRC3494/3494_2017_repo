package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Turret subsystem. Contains methods for controlling the turret.
 * 
 * @since 0.0.0
 */
public class Turret extends PIDSubsystem implements IMotorizedSubsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Encoder shooterEnc_lower;
	private Encoder shooterEnc_upper;

	private CANTalon shooterUpper;
	private CANTalon shooterLower;
	private CANTalon unscrambler;
	private CANTalon conveyer;

	public double PIDTune;

	public Turret() {
		super("Turret", 0.01, 0, 0);
		this.shooterUpper = new CANTalon(RobotMap.TURRET_UPPER);
		this.shooterLower = new CANTalon(RobotMap.TURRET_LOWER);
		this.unscrambler = new CANTalon(RobotMap.UNSCRAMBLER);
		this.conveyer = new CANTalon(RobotMap.TURRET_CONVEYER);

		this.shooterEnc_lower = new Encoder(RobotMap.TURRET_ENCLOWER_A, RobotMap.TURRET_ENCLOWER_B);
		this.shooterEnc_upper = new Encoder(RobotMap.TURRET_ENCUPPER_A, RobotMap.TURRET_ENCUPPER_B);
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
		this.stopConveyer();
	}

	@Override
	public void setAll(double speed) {
		this.shooterLower.set(speed);
		this.shooterUpper.set(speed);
		this.unscrambler.set(speed);
		this.conveyer.set(speed);
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

	/**
	 * Runs the conveyer to move balls into the shooter. Call in conjunction
	 * with {@link Turret#shoot(double)}.
	 */
	public void convey() {
		this.conveyer.set(0.4);
	}

	public void stopConveyer() {
		this.conveyer.set(0);
	}

	public double getRate(TurretEncoders enc) {
		if (enc.equals(TurretEncoders.TOP)) {
			return this.shooterEnc_upper.getRate();
		} else {
			return this.shooterEnc_lower.getRate();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected double returnPIDInput() {
		return this.shooterEnc_upper.getRate();
	}

	/**
	 * Writes the output of the PID controller to {@linkplain Turret#PIDTune}.
	 * 
	 * @param output
	 *            The output as calculated by the PID controller.
	 */
	@Override
	protected void usePIDOutput(double output) {
		this.PIDTune = output;
	}
}
