package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive;
import org.usfirst.frc.team3494.robot.commands.auto.PIDAngleDrive;
import org.usfirst.frc.team3494.robot.commands.auto.PIDFullDrive;
import org.usfirst.frc.team3494.robot.commands.auto.SetGearGrasp;
import org.usfirst.frc.team3494.robot.commands.auto.XYDrive;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
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
	 * The distance of the first pull in gear placing.
	 */
	private static final double FIRST_PULL = 87.5 - 14.5;
	/**
	 * The angle to turn after the first pull in gear placing.
	 */
	private static final double ANGLE = 58.5;

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
	 * Drives to the baseline.
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive
	 * @since 0.0.3
	 * @return A list of commands suitable for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
	 */
	public static ArrayList<Command> crossBaseLine() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DistanceDrive(72, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> placeCenterGear() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(110.75));
		return list;
	}

	/**
	 * Drives forward, turns right, drives forward again.
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> gearPassiveRight() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(FIRST_PULL));
		list.add(new PIDAngleDrive(ANGLE));
		list.add(new PIDFullDrive(54));
		// list.add(new DistanceDrive(-60, UnitTypes.INCHES));
		return list;
	}

	/**
	 * Drives forward, turns left, drives forward again.
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> gearPassiveLeft() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(FIRST_PULL));
		list.add(new PIDAngleDrive(-ANGLE));
		list.add(new PIDFullDrive(54));
		// list.add(new DistanceDrive(-60, UnitTypes.INCHES));
		return list;
	}

	/**
	 * Same as {@link AutoGenerator#gearPassiveLeft()}, but drops the gear on
	 * the peg at the end.
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> activeLeftGear() {
		ArrayList<Command> list = AutoGenerator.gearPassiveLeft();
		list.add(new SetGearGrasp(Value.kForward));
		list.add(new PIDFullDrive(-10));
		return list;
	}

	/**
	 * Same as {@link AutoGenerator#gearPassiveRight()}, but drops the gear on
	 * the peg at the end.
	 * 
	 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *      Constructed Auto
	 * @return A list for use with
	 *         {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
	 *         ConstructedAuto}
	 */
	public static ArrayList<Command> activeGearRight() {
		ArrayList<Command> list = AutoGenerator.gearPassiveRight();
		list.add(new SetGearGrasp(Value.kForward));
		list.add(new PIDFullDrive(-10));
		return list;
	}
}
