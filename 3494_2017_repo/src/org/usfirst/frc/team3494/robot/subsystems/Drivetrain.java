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
	 */
	private CANTalon driveLeftMaster;
	/**
	 * Follower talon on left side.
	 */
	private CANTalon driveLeftFollower_One;
	/**
	 * Follower talon on left side.
	 */
	private CANTalon driveLeftFollower_Two;
	/**
	 * Master drive talon, right side. Setting this should set all the talons on
	 * the left side of the drive train.
	 */
	private CANTalon driveRightMaster;
	/**
	 * Follower talon on right side.
	 */
	private CANTalon driveRightFollower_One;
	/**
	 * Follower talon on right side.
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

	public Drivetrain() {
		super("Drivetrain");

		this.driveLeftMaster = new CANTalon(RobotMap.leftTalonOne);
		this.driveLeftFollower_One = new CANTalon(RobotMap.leftTalonTwo);
		this.driveLeftFollower_Two = new CANTalon(RobotMap.leftTalonThree);
		// master follower
		this.driveLeftFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
		this.driveLeftFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());

		this.driveRightMaster = new CANTalon(RobotMap.rightTalonOne);
		this.driveRightFollower_One = new CANTalon(RobotMap.rightTalonTwo);
		this.driveRightFollower_Two = new CANTalon(RobotMap.rightTalonThree);
		// master follower
		this.driveRightFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveRightFollower_One.set(driveRightMaster.getDeviceID());
		this.driveRightFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.driveRightFollower_Two.set(driveRightMaster.getDeviceID());

		this.wpiDrive = new RobotDrive(driveLeftMaster, driveRightMaster);

		this.encRight = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);
		this.encRight.setDistancePerPulse(1 / 1440);
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
	 * run until stopped with a method like {@link Drivetrain#StopDrive()}.
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
	public void adjustedTankDrive(double left, double right) {
		driveLeftMaster.set(-left);
		driveRightMaster.set(right);
	}

	public double getRightDistance(UnitTypes unit) {
		double inches = (Math.PI * 4) * (1 / this.encRight.getDistance());
		if (unit.equals(UnitTypes.INCHES)) {
			return (Math.PI * 4) * (1 / this.encRight.getDistance());
		} else if (unit.equals(UnitTypes.FEET)) {
			return inches / 12;
		} else {
			return this.encRight.getDistance();
		}
	}

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
