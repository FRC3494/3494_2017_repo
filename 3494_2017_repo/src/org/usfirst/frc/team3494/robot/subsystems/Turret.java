package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.commands.turret.TurretCon;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
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
	private CANTalon conveyer;

	public double multi;

	public double PIDTune;

	private PIDController upperPID;
	private PIDController lowerPID;
	private PIDController[] PIDlist;

	public Turret() {
		super("Turret");
		this.shooterUpper = new CANTalon(RobotMap.TURRET_UPPER);
		this.shooterUpper.enableBrakeMode(false);
		this.shooterLower = new CANTalon(RobotMap.TURRET_LOWER);
		this.shooterLower.enableBrakeMode(false);
		this.unscrambler = new CANTalon(RobotMap.UNSCRAMBLER);
		this.conveyer = new CANTalon(RobotMap.TURRET_CONVEYER);

		this.shooterEnc_lower = new Encoder(RobotMap.TURRET_ENCLOWER_A, RobotMap.TURRET_ENCLOWER_B);
		this.shooterEnc_upper = new Encoder(RobotMap.TURRET_ENCUPPER_A, RobotMap.TURRET_ENCUPPER_B);
		this.shooterEnc_upper.setReverseDirection(true);

		// Set up PID controllers for RPM control
		this.shooterEnc_upper.setPIDSourceType(PIDSourceType.kRate);
		this.upperPID = new PIDController(0.1, 0, 0, this.shooterEnc_upper, this.shooterUpper);
		this.shooterEnc_lower.setPIDSourceType(PIDSourceType.kRate);
		this.lowerPID = new PIDController(0.1, 0, 0, this.shooterEnc_lower, this.shooterLower);
		// list for easy enable/disable/setpoints
		this.PIDlist = new PIDController[] { this.lowerPID, this.upperPID };
		for (PIDController c : this.PIDlist) {
			c.setContinuous(false);
			c.setInputRange(0, Double.MAX_VALUE);
			c.setOutputRange(-1, 1);
			c.disable();
		}
		this.multi = 1;
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		this.setDefaultCommand(new TurretCon());
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
		this.shooterUpper.set(power * multi);
		this.shooterLower.set(power);
		if (power != 0) {
			this.conveyer.set(0.5);
		} else {
			this.conveyer.set(0);
		}
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
	
	public void enablePID() {
		for (PIDController c : this.PIDlist) {
			c.enable();
		}
	}
	
	public void setSetpoint(double setpoint) {
		for (PIDController c : this.PIDlist) {
			c.setSetpoint(setpoint);
		}
	}
}
