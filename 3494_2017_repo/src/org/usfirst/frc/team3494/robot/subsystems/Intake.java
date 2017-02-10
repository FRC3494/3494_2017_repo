package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Intake subsystem. Contains methods for controlling the ball intake.
 * 
 * @since 0.0.0
 */
public class Intake extends Subsystem {
	/**
	 * Talon that controls the intake. Thankfully there's only one.
	 * 
	 * @see RobotMap
	 */
	private CANTalon inMotor;

	public Intake() {
		super("Intake");
		this.inMotor = new CANTalon(RobotMap.INTAKE_MOTOR);
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
	}

	public void stopIntake() {
		this.inMotor.set(0);
	}
}
