package org.usfirst.frc.team3494.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;

/**
 * Climber subsystem. Contains methods for controlling the rope climber.
 *
 * @since 0.0.0
 */
public class Climber extends Subsystem implements IMotorizedSubsystem {

    private TalonSRX motor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Climber() {
        super("Climber");
        motor = new TalonSRX(RobotMap.CLIMBER_MOTOR);
        motor.setInverted(true);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }

    /**
     * Climbs in the specified direction.
     *
     * @param dir The direction to climb. Setting this to anything other than
     *            {@link DriveDirections#UP} or {@link DriveDirections#DOWN}
     *            will stop the climber.
     */
    public void climb(DriveDirections dir) {
        if (dir.equals(DriveDirections.UP) && !Robot.pto.getState()) {
            motor.set(ControlMode.PercentOutput, 1);
        } else if (dir.equals(DriveDirections.DOWN) && !Robot.pto.getState()) {
            motor.set(ControlMode.PercentOutput, -1);
        } else {
            // stop the climber
            motor.set(ControlMode.PercentOutput, 0);
        }
    }

    @Override
    public void stopAll() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void setAll(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Gets current draw of the climb motor.
     */
    public double getMotorCurrent() {
        return Robot.pdp.getCurrent(RobotMap.CLIMBER_MOTOR_PDP);
    }
}
