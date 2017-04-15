package org.usfirst.frc.team3494.robot.commands.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Creates an auto program from a passed-in list of commands.
 *
 * @since 0.0.3
 */
public class ConstructedAuto extends CommandGroup {
	/**
	 * Constructor.
	 *
	 * @param commands
	 *            The list of commands to make an auto sequence from, <b>in
	 *            order.</b>
	 */
	public ConstructedAuto(ArrayList<Command> commands) {
		for (Command c : commands) {
			this.addSequential(c);
		}
	}
}
