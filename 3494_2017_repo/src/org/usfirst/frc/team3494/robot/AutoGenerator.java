package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive;
import org.usfirst.frc.team3494.robot.commands.auto.XYDrive;

import edu.wpi.first.wpilibj.command.Command;

public class AutoGenerator {
	public static ArrayList<Command> autoOne() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new XYDrive(36, 36));
		return list;
	}
	public static ArrayList<Command> crossBaseLine() {
		ArrayList<Command> list = new ArrayList<Command>();
		list.add(new DistanceDrive(93.25, UnitTypes.INCHES)); // ffs the baseline is far away
		return null;
	}
}
