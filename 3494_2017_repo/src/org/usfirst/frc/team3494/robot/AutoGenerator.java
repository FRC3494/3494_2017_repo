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
	private static final double FIRST_PULL = 86.5 - 29;

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
		list.add(new DistanceDrive(72, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> placeCenterGear() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(110.5));
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
	public static ArrayList<Command> gearPlaceAttempt() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(FIRST_PULL));
		list.add(new PIDAngleDrive(65));
		list.add(new PIDFullDrive(54));
		// list.add(new DistanceDrive(-60, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> gearPlaceAttemptLeft() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new PIDFullDrive(FIRST_PULL));
		list.add(new PIDAngleDrive(-65));
		list.add(new PIDFullDrive(54));
		// list.add(new DistanceDrive(-60, UnitTypes.INCHES));
		return list;
	}

	public static ArrayList<Command> activeLeftGear() {
		ArrayList<Command> list = AutoGenerator.gearPlaceAttemptLeft();
		list.add(new SetGearGrasp(Value.kForward));
		list.add(new PIDFullDrive(-10));
		return list;
	}

	public static ArrayList<Command> activeGearRight() {
		ArrayList<Command> list = AutoGenerator.gearPlaceAttempt();
		list.add(new SetGearGrasp(Value.kForward));
		list.add(new PIDFullDrive(-10));
		return list;
	}
}
