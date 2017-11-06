package org.usfirst.frc.team3494.robot.subsystems;

/**
 * Interface to apply to subsystems with motors.
 *
 * @see edu.wpi.first.wpilibj.command.Subsystem
 * @since 0.0.0
 */
public interface IMotorizedSubsystem {
    /**
     * Stop <strong>all</strong> motors on the subsystem.
     */
    public void stopAll();

    /**
     * Sets all motors on a subsystem to a given speed. (Example use: driving
     * forward)
     *
     * @param speed The speed to set the motors to.
     */
    public void setAll(double speed);
}
