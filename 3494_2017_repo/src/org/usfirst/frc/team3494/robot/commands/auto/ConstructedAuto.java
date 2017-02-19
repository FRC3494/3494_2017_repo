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
		// Add Commands here:
		// e.g. addSequential(new Command1());
		// addSequential(new Command2());
		// these will run in order.
		for (Command c : commands) {
			this.addSequential(c);
		}

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		// addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.
	}
}
