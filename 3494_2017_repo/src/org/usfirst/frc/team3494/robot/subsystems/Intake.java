package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intake subsystem. Contains methods for controlling the ball intake.
 * 
 * @since 0.0.0
 */
public class Intake extends Subsystem implements IMotorizedSubsystem {
	/**
	 * Talon that controls the intake. Thankfully there's only one.
	 * 
	 * @see RobotMap
	 */
	private TalonSRX inMotor;
	private CANTalon upMotor;

	public Intake() {
		super("Intake");
		this.inMotor = new TalonSRX(RobotMap.INTAKE_MOTOR);
		this.upMotor = new CANTalon(RobotMap.UP_MOTOR);
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void runIntake(double speed) {
		this.inMotor.set(speed);
		if (speed > 0) {
			this.upMotor.set(speed - 0.3);
		} else if (speed < 0) {
			this.upMotor.set(speed + 0.3);
		} else {
			// no
			this.stopAll();
		}
	}

	@Override
	public void stopAll() {
		this.inMotor.set(0);
		this.upMotor.set(0);
	}

	@Override
	public void setAll(double speed) {
		this.inMotor.set(speed);
		this.upMotor.set(speed);
	}
}
