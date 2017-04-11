package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem that controls the climber's PTO. Done to help minimize latency.
 * It's not right, but we do what we must because we can.
 */
public class ClimberPTO extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private boolean driveTrainMode;
	private DoubleSolenoid pto; // zarya named it

	public ClimberPTO() {
		super("Climber PTO");
		this.pto = new DoubleSolenoid(RobotMap.CLIMBER_PTO_FORWARD, RobotMap.CLIMBER_PTO_BACKARD);
		this.pto.set(Value.kForward);
		this.driveTrainMode = false;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public boolean getState() {
		return this.driveTrainMode;
	}

	/**
	 * Engages or disengages the drivetrain from the climber. Note that with the
	 * drivetrain engaged controlling the climber by this subsystem becomes
	 * impossible (you must use {@link Drivetrain} instead.)
	 * 
	 * @see Drivetrain
	 * @param value
	 *            The state to set the PTO piston in.
	 */
	public void setPTO(Value value) {
		this.pto.set(value);
		if (value.equals(Value.kOff) || value.equals(Value.kReverse)) {
			this.driveTrainMode = true;
		} else {
			this.driveTrainMode = false;
		}
	}

	public void disengagePTO() {
		this.setPTO(Value.kForward);
	}

	public void engagePTO() {
		this.setPTO(Value.kReverse);
	}

}
