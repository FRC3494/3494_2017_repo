package org.usfirst.frc.team3494.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Turret subsystem. Contains methods for controlling the turret.
 * 
 * @since 0.0.0
 */
public class Turret extends Subsystem implements IMotorizedSubsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private Encoder turretRingEnc;
	private Encoder shooterEnc;
	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	@Override
	public void stopAll() {
		
	}

	@Override
	public void setAll(double speed) {
		
	}
}
