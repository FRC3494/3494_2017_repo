package org.usfirst.frc.team3494.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.UnitTypes;
import org.usfirst.frc.team3494.robot.commands.drive.Drive;

/**
 * Drivetrain subsystem. Contains all methods for controlling the robot's
 * drivetrain. Also has an instance of RobotDrive (wpiDrive) if you want to use
 * that. PID control is built in with {@link edu.wpi.first.wpilibj.command.PIDSubsystem PIDSubsystem}.
 *
 * @since 0.0.0
 */
public class Drivetrain extends PIDSubsystem implements IMotorizedSubsystem {
    public Encoder encLeft;
    public Encoder encRight;
    private static final double COUNTS_OVER_INCHES = 84.5;
    /**
     * Master drive talon, left side. Setting this should set all the talons on
     * the left side of the drive train.
     *
     * @since 0.0.0
     */
    private TalonSRX driveLeftMaster;
    /**
     * Follower talon on left side.
     *
     * @since 0.0.0
     */
    private TalonSRX driveLeftFollower_One;
    /**
     * Follower talon on left side.
     *
     * @since 0.0.0
     */
    private TalonSRX driveLeftFollower_Two;
    /**
     * Master drive talon, right side. Setting this should set all the talons on
     * the left side of the drive train.
     *
     * @since 0.0.0
     */
    private TalonSRX driveRightMaster;
    /**
     * Follower talon on right side.
     *
     * @since 0.0.0
     */
    private TalonSRX driveRightFollower_One;
    /**
     * Follower talon on right side.
     *
     * @since 0.0.0
     */
    private TalonSRX driveRightFollower_Two;
    /**
     * Instance of wpiDrive for using WPI's driving code. Should <em>not</em> be
     * used for tank driving (use {@link Drivetrain#TankDrive} instead.)
     *
     * @since 0.0.0
     */
    public DifferentialDrive wpiDrive;

    public static final double RAMP = 0;

    public int inverter = 1;
    public double scaleDown = 1;

    public TalonSRX[] leftSide;
    public TalonSRX[] rightSide;

    public double PIDTune;

    public boolean teleop;

    public Drivetrain() {
        super("Drivetrain", 0.4, 0, 0.5);
        // int maxAmps = 50;
        // create left talons
        driveLeftMaster = new TalonSRX(RobotMap.leftTalonOne);
        driveLeftMaster.setNeutralMode(NeutralMode.Brake);

        driveLeftFollower_One = new TalonSRX(RobotMap.leftTalonTwo);
        driveLeftFollower_One.setNeutralMode(NeutralMode.Brake);

        driveLeftFollower_Two = new CANTalon(RobotMap.leftTalonThree);
        driveLeftFollower_Two.setNeutralMode(NeutralMode.Brake);
        // master follower
        // this.driveLeftFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
        // this.driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
        // this.driveLeftFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
        // this.driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());
        // create list
        leftSide = new TalonSRX[]{driveLeftMaster, driveLeftFollower_One, driveLeftFollower_Two};

        driveRightMaster = new TalonSRX(RobotMap.rightTalonOne);
        driveRightMaster.setNeutralMode(NeutralMode.Brake);

        driveRightFollower_One = new TalonSRX(RobotMap.rightTalonTwo);
        driveRightFollower_One.setNeutralMode(NeutralMode.Brake);

        driveRightFollower_Two = new TalonSRX(RobotMap.rightTalonThree);
        driveRightFollower_Two.setNeutralMode(NeutralMode.Brake);
        // master follower
        // this.driveRightFollower_One.changeControlMode(TalonControlMode.Follower);
        // this.driveRightFollower_One.set(driveRightMaster.getDeviceID());
        // this.driveRightFollower_Two.changeControlMode(TalonControlMode.Follower);
        // this.driveRightFollower_Two.set(driveRightMaster.getDeviceID());
        // list time!
        rightSide = new TalonSRX[]{driveRightMaster, driveRightFollower_One, driveLeftFollower_Two};

        teleop = false;

        this.encRight = new Encoder(RobotMap.ENCODER_RIGHT_A, RobotMap.ENCODER_RIGHT_B);
        this.encRight.setDistancePerPulse(1 / 256);
        this.encRight.setReverseDirection(true);
        this.encRight.reset();


        this.encLeft = new Encoder(RobotMap.ENCODER_LEFT_A, RobotMap.ENCODER_LEFT_B);
        this.encLeft.setDistancePerPulse(1 / 256);
        this.encLeft.reset();

        // PID control
        PIDTune = 0;
        double outRange = 0.65;
        disable();
        if (getSetpoint() != 0) {
            setSetpoint(0);
        }
        setInputRange(-180, 180);
        setOutputRange(-outRange, outRange);
        getPIDController().setContinuous(true);
        setPercentTolerance(0.5);
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
     * @param left  The power to drive the left side. Should be a {@code double}
     *              between 0 and 1.
     * @param right The power to drive the right side. Should be a {@code double}
     *              between 0 and 1.
     */
    public void TankDrive(double left, double right) {
        double leftScale = Robot.prefs.getDouble("left side multiplier", 1.0D);
        double rightScale = Robot.prefs.getDouble("right side multiplier", 1.0D);
        driveLeftMaster.set(ControlMode.PercentOutput, left * scaleDown * leftScale);
        driveRightMaster.set(ControlMode.PercentOutput, right * scaleDown * rightScale);
        driveLeftFollower_One.set(ControlMode.PercentOutput, left * scaleDown * leftScale);
        driveRightFollower_One.set(ControlMode.PercentOutput, right * scaleDown * rightScale);
        driveLeftFollower_Two.set(ControlMode.PercentOutput, left * scaleDown * leftScale);
        driveRightFollower_Two.set(ControlMode.PercentOutput, right * scaleDown * rightScale);
    }

    /**
     * Drives the drivetrain, with the value passed in for left inverted. This
     * corrects for the left side being inverted hardware side.<br>
     * It works if you don't think about it too hard.
     *
     * @param left  The power to set the left side to. This will be multiplied by
     *              -1 before getting to the Talons.
     * @param right The power to set the right side to. Just goes straight to the
     *              Talons.
     */
    public void adjustedTankDrive(double left, double right) {
        TankDrive(-left, right);
    }

    /**
     * Arcade drive method for differential drive platform.
     *
     * @param xSpeed        The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param zRotation     The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                      positive.
     * @param squaredInputs If set, decreases the input sensitivity at low speeds.
     * @author Worcester Polytechnic Institute
     */
    public void ArcadeDrive(double xSpeed, double zRotation, boolean squaredInputs) {
        xSpeed = limit(xSpeed);
        xSpeed = applyDeadband(xSpeed, 0.02);

        zRotation = limit(zRotation);
        zRotation = applyDeadband(zRotation, 0.02);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        if (squaredInputs) {
            xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
            zRotation = Math.copySign(zRotation * zRotation, zRotation);
        }

        double leftMotorOutput;
        double rightMotorOutput;

        double maxInput = Math.copySign(Math.max(Math.abs(xSpeed), Math.abs(zRotation)), xSpeed);

        if (xSpeed >= 0.0) {
            // First quadrant, else second quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            } else {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            }
        } else {
            // Third quadrant, else fourth quadrant
            if (zRotation >= 0.0) {
                leftMotorOutput = xSpeed + zRotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = xSpeed - zRotation;
            }
        }

        driveLeftMaster.set(ControlMode.PercentOutput, limit(leftMotorOutput));
        driveRightMaster.set(ControlMode.PercentOutput, -limit(rightMotorOutput));
    }

    /**
     * Gets the distance the right encoder has counted in the specified unit.
     *
     * @param unit The unit type to get the distance in.
     * @return The distance the right encoder has counted, in the specified
     * unit.
     */
    public double getRightDistance(UnitTypes unit) {
        double encCountsTalon = encRight.get();
        double inches = (1 / COUNTS_OVER_INCHES) * encCountsTalon;
        if (unit.equals(UnitTypes.INCHES)) {
            return inches;
        } else if (unit.equals(UnitTypes.FEET)) {
            return inches / 12.0D;
        } else if (unit.equals(UnitTypes.MILLIMETERS)) {
            return inches * 25.400;
        } else if (unit.equals(UnitTypes.CENTIMETERS)) {
            return inches * 2.540;
        } else {
            return encCountsTalon;
        }
    }

    /**
     * Gets the distance the left encoder has counted in the specified unit.
     *
     * @param unit The unit type to get the distance in.
     * @return The distance the left encoder has counted, in the specified unit.
     */
    public double getLeftDistance(UnitTypes unit) {
        double talonEncDist = encLeft.get();
        double inches = (1 / COUNTS_OVER_INCHES) * talonEncDist;
        if (unit.equals(UnitTypes.INCHES)) {
            return inches;
        } else if (unit.equals(UnitTypes.FEET)) {
            return inches / 12.0D;
        } else if (unit.equals(UnitTypes.MILLIMETERS)) {
            return inches * 25.400;
        } else if (unit.equals(UnitTypes.CENTIMETERS)) {
            return inches * 2.540;
        } else {
            return talonEncDist;
        }
    }

    /**
     * Gets the average distance the encoders have counted in the specified unit.
     *
     * @param unit The unit type to get the distance in.
     * @return The average distance the encoders have counted, in the specified unit.
     */
    public double getAvgDistance(UnitTypes unit) {
        return (getLeftDistance(unit) + getRightDistance(unit)) / 2;
    }

    /**
     * Resets the encoder on the right side of the drivetrain.
     */
    public void resetRight() {
        this.encRight.reset();
        driveRightMaster.getSensorCollection().setQuadraturePosition(0, 10);
    }

    /**
     * Resets the encoder on the left side of the drivetrain.
     */
    public void resetLeft() {
        this.encLeft.reset();
        driveLeftMaster.getSensorCollection().setQuadraturePosition(0, 10);
    }

    @Override
    public void stopAll() {
        this.TankDrive(0, 0);
    }

    @Override
    public void setAll(double speed) {
        TankDrive(speed, speed);
    }

    /**
     * Returns {@code true} if the drivetrain is inverted (the gear holder is
     * considered forward.)
     *
     * @return {@code true} if the drivetrain is inverted.
     */
    public boolean getInverted() {
        return inverter == -1;
    }

    @Override
    protected double returnPIDInput() {
        if (!teleop) {
            return Robot.ahrs.getYaw();
        } else {
            return Robot.ahrs.getAngle();
        }
    }

    @Override
    protected void usePIDOutput(double output) {
        PIDTune = output;
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

    /**
     * Returns 0.0 if the given value is within the specified range around zero. The remaining range
     * between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     * @author Worcester Polytechnic Institute
     */
    protected double applyDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }
}
