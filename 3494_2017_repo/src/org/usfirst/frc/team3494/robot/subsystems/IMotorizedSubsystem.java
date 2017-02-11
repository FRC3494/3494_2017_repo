package org.usfirst.frc.team3494.robot.subsystems;

/**
 * Interface to apply to subsystems with motors.
 * 
 * @since 0.0.0
 * @see edu.wpi.first.wpilibj.command.Subsystem
 */
public interface IMotorizedSubsystem {
	/**
	 * Stop <strong>all</strong> motors on the subsystem.
	 */
	public void stopAll();

	/**
	 * Sets all motors on a subsystem to a given speed. (Example use: driving
	 * forward)
	 */
	public void setAll(double speed);
}
