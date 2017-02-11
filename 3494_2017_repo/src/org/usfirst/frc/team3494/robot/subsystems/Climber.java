package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Climber subsystem. Contains methods for controlling the rope climber.
 * 
 * @since 0.0.0
 */
public class Climber extends Subsystem {
	private CANTalon motor;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Climber() {
		super("Climber");
		motor = new CANTalon(RobotMap.CLIMBER_MOTOR);
	}
    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void climb(DriveDirections dir) {
    	if (dir.equals(DriveDirections.UP)) {
    		motor.set(0.4);
    	} else if (dir.equals(DriveDirections.DOWN)) {
    		motor.set(-0.4);
    	}
    }
    public void stopClimber() {
    	motor.set(0);
    }
}
