package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.UnitTypes;
import org.usfirst.frc.team3494.robot.commands.drive.Drive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Drivetrain subsystem. Contains all methods for controlling the robot's
 * drivetrain. Also has in instance of RobotDrive (wpiDrive) if you want to use
 * that.
 * 
 * @since 0.0.0
 */
public class Drivetrain extends Subsystem implements IMotorizedSubsystem {
	/**
	 * Master drive talon, left side. Setting this should set all the talons on
	 * the left side of the drive train.
	 * 
	 * @since 0.0.0
	 */
	private CANTalon driveLeftMaster;
	/**
	 * Follower talon on left side.
	 * 
	 * @since 0.0.0
	 */
	private CANTalon driveLeftFollower_One;
	/**
	 * Follower talon on left side.
	 * 
	 * @since 0.0.0
	 */
	private CANTalon driveLeftFollower_Two;
	/**
	 * Master drive talon, right side. Setting this should set all the talons on
	 * the left side of the drive train.
	 * 
	 * @since 0.0.0
	 */
	private CANTalon driveRightMaster;
	/**
	 * Follower talon on right side.
	 * 
	 * @since 0.0.0
	 */
	private CANTalon driveRightFollower_One;
	/**
	 * Follower talon on right side.
	 * 
	 * @since 0.0.0
	 */
	private CANTalon driveRightFollower_Two;
	/**
	 * Instance of wpiDrive for using WPI's driving code. Should <em>not</em> be
	 * used for tank driving (use {@link Drivetrain#TankDrive} instead.)
	 * 
	 * @since 0.0.0
	 */
	public RobotDrive wpiDrive;
	private Encoder encRight;
	private Encoder encLeft;

	private static double RAMP = 1.173; // lowest possible ramp

	public Drivetrain() {
		super("Drivetrain");

		this.driveLeftMaster = new CANTalon(RobotMap.leftTalonOne);
		this.driveLeftMaster.enableBrakeMode(true);
		this.driveLeftMaster.setVoltageRampRate(RAMP);
		this.driveLeftFollower_One = new CANTalon(RobotMap.leftTalonTwo);
		this.driveLeftFollower_One.enableBrakeMode(true);
		this.driveLeftFollower_One.setVoltageRampRate(RAMP);
		this.driveLeftFollower_Two = new CANTalon(RobotMap.leftTalonThree);
		this.driveLeftFollower_Two.enableBrakeMode(true);
		this.driveLeftFollower_Two.setVoltageRampRate(RAMP);
		// master follower
		this.driveLeftFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
		this.driveLeftFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());

		this.driveRightMaster = new CANTalon(RobotMap.rightTalonOne);
		this.driveRightMaster.enableBrakeMode(true);
		this.driveRightMaster.setVoltageRampRate(RAMP);
		this.driveRightFollower_One = new CANTalon(RobotMap.rightTalonTwo);
		this.driveRightFollower_One.enableBrakeMode(true);
		this.driveRightFollower_One.setVoltageRampRate(RAMP);
		this.driveRightFollower_Two = new CANTalon(RobotMap.rightTalonThree);
		this.driveRightFollower_Two.enableBrakeMode(true);
		this.driveRightFollower_Two.setVoltageRampRate(RAMP);
		// master follower
		this.driveRightFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveRightFollower_One.set(driveRightMaster.getDeviceID());
		this.driveRightFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveRightFollower_Two.set(driveRightMaster.getDeviceID());

		this.wpiDrive = new RobotDrive(driveLeftMaster, driveRightMaster);

		this.encRight = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);
		this.encRight.setDistancePerPulse(1 / 360);
		this.encRight.reset();

		this.encLeft = new Encoder(RobotMap.ENCODER_LEFT_A, RobotMap.ENCODER_LEFT_B, true);
		this.encLeft.setDistancePerPulse(1 / 360);
		this.encLeft.reset();
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Drive());
	}

	/**
	 * Drives the drivetrain tank drive style. The drivetrain will continue to
	 * run until stopped with a method like {@link Drivetrain#stopAll()}.
	 * 
	 * @param left
	 *            The power to drive the left side. Should be a {@code double}
	 *            between 0 and 1.
	 * @param right
	 *            The power to drive the right side. Should be a {@code double}
	 *            between 0 and 1.
	 */
	public void TankDrive(double left, double right) {
		driveLeftMaster.set(left);
		driveRightMaster.set(right);
	}

	/**
	 * Drives the drivetrain, with the value passed in for left inverted. This
	 * corrects for the left side being inverted hardware side.<br>
	 * It works if you don't think about it too hard.
	 * 
	 * @param left
	 *            The power to set the left side to. This will be multiplied by
	 *            -1 before getting to the Talons.
	 * @param right
	 *            The power to set the right side to. Just goes straight to the
	 *            Talons.
	 */
	public void adjustedTankDrive(double left, double right) {
		driveLeftMaster.set(-left);
		driveRightMaster.set(right);
	}

	/**
	 * Gets the distance the right encoder has counted in the specified unit.
	 * 
	 * @param unit
	 *            The unit type to get the distance in.
	 * @return The distance the right encoder has counted, in the specified
	 *         unit.
	 */
	public double getRightDistance(UnitTypes unit) {
		double inches = (Math.PI * 4) * (this.encRight.get() / 360);
		if (unit.equals(UnitTypes.INCHES)) {
			return inches;
		} else if (unit.equals(UnitTypes.FEET)) {
			return inches / 12.0D;
		} else if (unit.equals(UnitTypes.MILLIMETERS)) {
			return inches * 25.400;
		} else if (unit.equals(UnitTypes.CENTIMETERS)) {
			return inches * 2.540;
		} else {
			return this.encRight.get();
		}
	}

	public double getLeftDistance(UnitTypes unit) {
		double inches = (Math.PI * 4) * (this.encLeft.get() / 360.0D);
		if (unit.equals(UnitTypes.INCHES)) {
			return inches;
		} else if (unit.equals(UnitTypes.FEET)) {
			return inches / 12.0D;
		} else {
			return this.encLeft.get();
		}
	}

	/**
	 * Resets the encoder on the right side of the drivetrain.
	 */
	public void resetRight() {
		this.encRight.reset();
	}

	@Override
	public void stopAll() {
		this.TankDrive(0, 0);
	}

	@Override
	public void setAll(double speed) {
		this.TankDrive(speed, speed);
	}
}
