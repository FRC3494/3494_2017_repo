package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.usfirst.frc.team3494.robot.commands.auto.AngleTurn;
import org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive;
import org.usfirst.frc.team3494.robot.commands.auto.XYDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class containing methods that return valid lists to pass to
 * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
 * 
 * @since 0.0.3
 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
 */
public class AutoGenerator {
	/**
	 * Test method. Drives to XY (36, 36) (inches).
	 * 
	 * @since 0.0.3
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 * @see org.usfirst.frc.team3494.robot.commands.auto.XYDrive
	 * @return A list of commands suitable for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
	 */
	public static ArrayList<Command> autoOne() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new XYDrive(36, 36));
		return list;
	}

	/**
	 * Drives to the baseline and earns us five points (hopefully.)
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive
	 * @since 0.0.3
	 * @return A list of commands suitable for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
	 */
	public static ArrayList<Command> crossBaseLine() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DistanceDrive(240, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> gearPlaceAttempt() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DistanceDrive(93.25, UnitTypes.INCHES));
		list.add(new AngleTurn(-180));
		double rise = 20.462;
		double run = 32.463;
		list.add(new AngleTurn(Math.toDegrees(Math.atan2(20.462, 32.463))));
		double dist = 0;
		dist = Math.sqrt((rise * rise) + (run * run));
		list.add(new DistanceDrive(-dist, UnitTypes.INCHES));
		return list;
	}
}
