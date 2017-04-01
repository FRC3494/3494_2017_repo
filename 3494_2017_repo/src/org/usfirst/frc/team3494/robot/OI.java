package org.usfirst.frc.team3494.robot;

import org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive;
import org.usfirst.frc.team3494.robot.commands.climb.Climb;
import org.usfirst.frc.team3494.robot.commands.climb.ClimberPTOSetter;
import org.usfirst.frc.team3494.robot.commands.climb.StopClimber;
import org.usfirst.frc.team3494.robot.commands.gears.HoldInState_Forward;
import org.usfirst.frc.team3494.robot.commands.gears.SetReverse;
import org.usfirst.frc.team3494.robot.commands.gears.ToggleGearRamp;
import org.usfirst.frc.team3494.robot.commands.turret.Shoot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);
	public final XboxController xbox = new XboxController(RobotMap.XBOX_ONE);
	public final XboxController xbox_2 = new XboxController(RobotMap.XBOX_TWO);
	public final Joystick stick_l = new Joystick(3);
	public final Joystick stick_r = new Joystick(4);
	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	public JoystickButton xbox_a = new JoystickButton(xbox, 1);
	public JoystickButton xbox_a_2 = new JoystickButton(xbox_2, 1);

	public JoystickButton xbox_lt = new JoystickButton(xbox, 5);

	public JoystickButton xbox_rt = new JoystickButton(xbox, 6);
	public JoystickButton xbox_rt_2 = new JoystickButton(xbox_2, 6);

	public JoystickButton xbox_b = new JoystickButton(xbox, 2);
	public JoystickButton xbox_b_2 = new JoystickButton(xbox_2, 2);

	public JoystickButton xbox_y = new JoystickButton(xbox, 4);
	public JoystickButton xbox_y_2 = new JoystickButton(xbox_2, 4);

	public JoystickButton xbox_x = new JoystickButton(xbox, 3);
	public JoystickButton xbox_x_2 = new JoystickButton(xbox_2, 3);

	public JoystickButton xbox_select_2 = new JoystickButton(xbox_2, 7);
	public JoystickButton xbox_start_2 = new JoystickButton(xbox_2, 8);

	public OI() {
		// Ready Player One
		xbox_a.whenPressed(new DistanceDrive(-12, UnitTypes.INCHES));
		// Ready Player Two
		// Climb controls
		xbox_a_2.whileActive(new Climb(DriveDirections.UP));
		xbox_a_2.whenReleased(new StopClimber());

		xbox_b_2.whenPressed(new SetReverse());

		xbox_x_2.whileHeld(new HoldInState_Forward());

		xbox_y_2.whenPressed(new ToggleGearRamp());

		xbox_rt_2.whenPressed(new Shoot());
		xbox_rt_2.whenReleased(new Shoot(0));

		xbox_select_2.whenPressed(new ClimberPTOSetter(true));
		xbox_start_2.whileHeld(new ClimberPTOSetter(false));
	}
}
