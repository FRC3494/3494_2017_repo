package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.commands.intake.RunIntake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	private TalonSRX upMotor;
	// pistons push
	private DoubleSolenoid piston;
	public boolean isDeployed;

	public Intake() {
		super("Intake");
		this.inMotor = new TalonSRX(RobotMap.INTAKE_MOTOR);
		this.upMotor = new TalonSRX(RobotMap.UP_MOTOR);
		this.piston = new DoubleSolenoid(RobotMap.INTAKE_PISTON_CHONE, RobotMap.INTAKE_PISTON_CHTWO);
		this.isDeployed = false;
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		this.setDefaultCommand(new RunIntake());
	}

	public void runIntake(double speed) {
		this.inMotor.set(speed);
		this.upMotor.set(speed);
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

	public void pushForward() {
		this.piston.set(DoubleSolenoid.Value.kForward);
		this.isDeployed = true;
	}

	public void retract() {
		this.piston.set(DoubleSolenoid.Value.kReverse);
		this.isDeployed = false;
	}

	public void setPiston(DoubleSolenoid.Value position) {
		this.piston.set(position);
		if (position.equals(DoubleSolenoid.Value.kForward)) {
			this.isDeployed = true;
		} else {
			this.isDeployed = true;
		}
		SmartDashboard.putBoolean("Intake Deployed", this.isDeployed);
	}
}
