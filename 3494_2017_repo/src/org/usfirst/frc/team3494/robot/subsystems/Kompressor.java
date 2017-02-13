package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Compressor subsystem. Named this way to carry on legacy of past years.
 */
public class Kompressor extends Subsystem {
	public Compressor compress;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public Kompressor() {
		super("Kompressor");
		this.compress = new Compressor(RobotMap.COMPRESSOR);
		this.compress.setClosedLoopControl(true);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

