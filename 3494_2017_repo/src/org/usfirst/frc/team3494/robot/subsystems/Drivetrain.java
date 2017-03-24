package org.usfirst.frc.team3494.robot.subsystems;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.UnitTypes;
import org.usfirst.frc.team3494.robot.commands.drive.Drive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Drivetrain subsystem. Contains all methods for controlling the robot's
 * drivetrain. Also has in instance of RobotDrive (wpiDrive) if you want to use
 * that.
 * 
 * @since 0.0.0
 */
public class Drivetrain extends PIDSubsystem implements IMotorizedSubsystem {
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

	public static final double RAMP = 48;

	public int inverter = -1;
	public double scaleDown = 1;

	public CANTalon[] leftSide;
	public CANTalon[] rightSide;

	public double PIDTune;

	public Drivetrain() {
		super("Drivetrain", 0.035, 0, 0);
		// int maxAmps = 50;
		// create left talons
		this.driveLeftMaster = new CANTalon(RobotMap.leftTalonOne);
		this.driveLeftMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.driveLeftMaster.configEncoderCodesPerRev(360);
		this.driveLeftMaster.setEncPosition(0);
		this.driveLeftMaster.enableBrakeMode(true);
		this.driveLeftMaster.setVoltageRampRate(RAMP);
		this.driveLeftFollower_One = new CANTalon(RobotMap.leftTalonTwo);
		this.driveLeftFollower_One.enableBrakeMode(true);
		this.driveLeftFollower_One.setVoltageRampRate(RAMP);
		this.driveLeftFollower_Two = new CANTalon(RobotMap.leftTalonThree);
		this.driveLeftFollower_Two.enableBrakeMode(true);
		this.driveLeftFollower_Two.setVoltageRampRate(RAMP);
		// master follower
		// this.driveLeftFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
		// this.driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
		// this.driveLeftFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
		// this.driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());
		// create list
		this.leftSide = new CANTalon[] { this.driveLeftMaster, this.driveLeftFollower_One, this.driveLeftFollower_Two };

		this.driveRightMaster = new CANTalon(RobotMap.rightTalonOne);
		this.driveRightMaster.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.driveRightMaster.reverseSensor(true);
		this.driveRightMaster.setEncPosition(0);
		this.driveRightMaster.configEncoderCodesPerRev(360);
		this.driveRightMaster.enableBrakeMode(true);
		this.driveRightMaster.setVoltageRampRate(RAMP);
		this.driveRightFollower_One = new CANTalon(RobotMap.rightTalonTwo);
		this.driveRightFollower_One.enableBrakeMode(true);
		this.driveRightFollower_One.setVoltageRampRate(RAMP);
		this.driveRightFollower_Two = new CANTalon(RobotMap.rightTalonThree);
		this.driveRightFollower_Two.enableBrakeMode(true);
		this.driveRightFollower_Two.setVoltageRampRate(RAMP);
		// master follower
		// this.driveRightFollower_One.changeControlMode(TalonControlMode.Follower);
		// this.driveRightFollower_One.set(driveRightMaster.getDeviceID());
		// this.driveRightFollower_Two.changeControlMode(TalonControlMode.Follower);
		// this.driveRightFollower_Two.set(driveRightMaster.getDeviceID());
		// list time!
		this.rightSide = new CANTalon[] { this.driveRightMaster, this.driveRightFollower_One,
				this.driveLeftFollower_Two };

		this.wpiDrive = new RobotDrive(driveLeftMaster, driveRightMaster);
		this.wpiDrive.setExpiration(Integer.MAX_VALUE);
		this.wpiDrive.setSafetyEnabled(false);

		/*
		 * this.encRight = new Encoder(RobotMap.ENCODER_RIGHT_A,
		 * RobotMap.ENCODER_RIGHT_B); this.encRight.setDistancePerPulse(1 /
		 * 360); this.encRight.reset();
		 */

		/*
		 * this.encLeft = new Encoder(RobotMap.ENCODER_LEFT_A,
		 * RobotMap.ENCODER_LEFT_B); this.encLeft.setDistancePerPulse(1 / 360);
		 * this.encLeft.setReverseDirection(true); this.encLeft.reset();
		 */
		// PID control
		this.PIDTune = 0;
		double outRange = 0.5;
		this.getPIDController().setInputRange(-180, 180);
		this.getPIDController().setOutputRange(-outRange, outRange);
		this.getPIDController().setContinuous(false);
		this.getPIDController().setPercentTolerance(20);
	}
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		this.setDefaultCommand(new Drive());
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
		double leftScale = Robot.prefs.getDouble("left side multiplier", 1.0D);
		double rightScale = Robot.prefs.getDouble("right side multiplier", 1.0D);
		this.driveLeftMaster.set(left * this.scaleDown * leftScale);
		this.driveRightMaster.set(right * this.scaleDown * rightScale);
		this.driveLeftFollower_One.set(left * this.scaleDown * leftScale);
		this.driveRightFollower_One.set(right * this.scaleDown * rightScale);
		this.driveLeftFollower_Two.set(left * this.scaleDown * leftScale);
		this.driveRightFollower_Two.set(right * this.scaleDown * rightScale);
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
		this.TankDrive(-left, right);
	}

	/**
	 * Arcade drive implements single stick driving. This function lets you
	 * directly provide joystick values from any source.
	 *
	 * @param moveValue
	 *            The value to use for forwards/backwards
	 * @param rotateValue
	 *            The value to use for the rotate right/left
	 * @param squaredInputs
	 *            If set, decreases the sensitivity at low speeds
	 * @author Worcester Polytechnic Institute
	 */
	public void ArcadeDrive(double moveValue, double rotateValue, boolean squaredInputs) {
		double leftMotorSpeed;
		double rightMotorSpeed;

		moveValue = limit(moveValue);
		rotateValue = limit(rotateValue);

		if (squaredInputs) {
			// square the inputs (while preserving the sign) to increase fine
			// control
			// while permitting full power
			if (moveValue >= 0.0) {
				moveValue = moveValue * moveValue;
			} else {
				moveValue = -(moveValue * moveValue);
			}
			if (rotateValue >= 0.0) {
				rotateValue = rotateValue * rotateValue;
			} else {
				rotateValue = -(rotateValue * rotateValue);
			}
		}

		if (moveValue > 0.0) {
			if (rotateValue > 0.0) {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = Math.max(moveValue, rotateValue);
			} else {
				leftMotorSpeed = Math.max(moveValue, -rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			}
		} else {
			if (rotateValue > 0.0) {
				leftMotorSpeed = -Math.max(-moveValue, rotateValue);
				rightMotorSpeed = moveValue + rotateValue;
			} else {
				leftMotorSpeed = moveValue - rotateValue;
				rightMotorSpeed = -Math.max(-moveValue, -rotateValue);
			}
		}
		this.TankDrive(leftMotorSpeed, -rightMotorSpeed);
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
		double encCountsTalon = this.driveRightMaster.getPosition();
		double inches = (Math.PI * 4) * (encCountsTalon);
		if (unit.equals(UnitTypes.INCHES)) {
			return inches;
		} else if (unit.equals(UnitTypes.FEET)) {
			return inches / 12.0D;
		} else if (unit.equals(UnitTypes.MILLIMETERS)) {
			return inches * 25.400;
		} else if (unit.equals(UnitTypes.CENTIMETERS)) {
			return inches * 2.540;
		} else {
			return this.driveRightMaster.getPosition();
		}
	}

	/**
	 * Gets the distance the left encoder has counted in the specified unit.
	 * 
	 * @param unit
	 *            The unit type to get the distance in.
	 * @return The distance the left encoder has counted, in the specified unit.
	 */
	public double getLeftDistance(UnitTypes unit) {
		double talonEncDist = this.driveLeftMaster.getPosition();
		double inches = (Math.PI * 4) * (talonEncDist);
		if (unit.equals(UnitTypes.INCHES)) {
			return inches;
		} else if (unit.equals(UnitTypes.FEET)) {
			return inches / 12.0D;
		} else if (unit.equals(UnitTypes.MILLIMETERS)) {
			return inches * 25.400;
		} else if (unit.equals(UnitTypes.CENTIMETERS)) {
			return inches * 2.540;
		} else {
			return this.driveLeftMaster.getPosition();
		}
	}

	public double getAvgDistance(UnitTypes unit) {
		return (this.getLeftDistance(unit) + this.getRightDistance(unit)) / 2;
	}

	/**
	 * Resets the encoder on the right side of the drivetrain.
	 */
	public void resetRight() {
		// this.encRight.reset();
		this.driveRightMaster.setPosition(0);
	}

	/**
	 * Resets the encoder on the left side of the drivetrain.
	 */
	public void resetLeft() {
		// this.encLeft.reset();
		this.driveLeftMaster.setPosition(0);
	}

	@Override
	public void stopAll() {
		this.TankDrive(0, 0);
	}

	@Override
	public void setAll(double speed) {
		this.TankDrive(speed, speed);
	}

	/**
	 * Returns {@code true} if the drivetrain is inverted (the gear holder is
	 * considered forward.)
	 * 
	 * @return {@code true} if the drivetrain is inverted.
	 */
	public boolean getInverted() {
		return this.inverter == -1;
	}

	/**
	 * Stage-sets the drivetrain. Please, for the love of all that is holy call
	 * {@link Drivetrain#snapBackToReality()} after this.
	 * 
	 * @param left
	 *            The left power
	 * @param right
	 *            The right power
	 */
	public void stagedTankDrive(double left, double right) {
		this.driveLeftMaster.set(left);
		this.driveRightMaster.set(right);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.driveLeftFollower_One.set(left);
		this.driveRightFollower_One.set(right);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.driveLeftFollower_Two.set(left);
		this.driveRightFollower_Two.set(right);
	}

	public void initStaging() {
		// change all to be percent Vbus
		for (CANTalon t : this.leftSide) {
			t.changeControlMode(TalonControlMode.PercentVbus);
		}
		for (CANTalon t : this.rightSide) {
			t.changeControlMode(TalonControlMode.PercentVbus);
		}
	}

	public void snapBackToReality() {
		// left reset
		this.driveLeftFollower_One.changeControlMode(TalonControlMode.Follower);
		this.driveLeftFollower_Two.changeControlMode(TalonControlMode.Follower);
		this.driveLeftFollower_One.set(this.driveLeftMaster.getDeviceID());
		this.driveLeftFollower_Two.set(this.driveLeftMaster.getDeviceID());
		// right reset
		this.driveRightFollower_One.changeControlMode(TalonControlMode.Follower);
		this.driveRightFollower_Two.changeControlMode(TalonControlMode.Follower);
		this.driveRightFollower_One.set(this.driveRightMaster.getDeviceID());
		this.driveRightFollower_Two.set(this.driveRightMaster.getDeviceID());
	}

	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		this.PIDTune = output;
	}

	private static double limit(double num) {
		if (num > 1.0) {
			return 1.0;
		}
		if (num < -1.0) {
			return -1.0;
		}
		return num;
	}
}
