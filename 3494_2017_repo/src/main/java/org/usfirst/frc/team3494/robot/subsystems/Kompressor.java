package org.usfirst.frc.team3494.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3494.robot.RobotMap;

/**
 * Compressor subsystem. Named this way to carry on legacy of past years.
 */
public class Kompressor extends Subsystem {
    public Compressor compress;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Kompressor() {
        super("Kompressor");
        compress = new Compressor(RobotMap.COMPRESSOR);
        compress.setClosedLoopControl(true);
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
}
