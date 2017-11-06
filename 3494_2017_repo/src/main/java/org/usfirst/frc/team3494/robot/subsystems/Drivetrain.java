package org.usfirst.frc.team3494.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
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

    public static final double RAMP = 0;

    public int inverter = 1;
    public double scaleDown = 1;

    public CANTalon[] leftSide;
    public CANTalon[] rightSide;

    public double PIDTune;

    public boolean teleop;

    public Drivetrain() {
        super("Drivetrain", 0.4, 0, 0.5);
        // int maxAmps = 50;
        // create left talons
        driveLeftMaster = new CANTalon(RobotMap.leftTalonOne);
        driveLeftMaster.enableBrakeMode(true);
        driveLeftMaster.setVoltageRampRate(RAMP);

        driveLeftFollower_One = new CANTalon(RobotMap.leftTalonTwo);
        driveLeftFollower_One.enableBrakeMode(true);
        driveLeftFollower_One.setVoltageRampRate(RAMP);

        driveLeftFollower_Two = new CANTalon(RobotMap.leftTalonThree);
        driveLeftFollower_Two.enableBrakeMode(true);
        driveLeftFollower_Two.setVoltageRampRate(RAMP);
        // master follower
        // this.driveLeftFollower_One.changeControlMode(CANTalon.TalonControlMode.Follower);
        // this.driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
        // this.driveLeftFollower_Two.changeControlMode(CANTalon.TalonControlMode.Follower);
        // this.driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());
        // create list
        leftSide = new CANTalon[]{driveLeftMaster, driveLeftFollower_One, driveLeftFollower_Two};

        driveRightMaster = new CANTalon(RobotMap.rightTalonOne);
        driveRightMaster.enableBrakeMode(true);
        driveRightMaster.setVoltageRampRate(RAMP);

        driveRightFollower_One = new CANTalon(RobotMap.rightTalonTwo);
        driveRightFollower_One.enableBrakeMode(true);
        driveRightFollower_One.setVoltageRampRate(RAMP);

        driveRightFollower_Two = new CANTalon(RobotMap.rightTalonThree);
        driveRightFollower_Two.enableBrakeMode(true);
        driveRightFollower_Two.setVoltageRampRate(RAMP);
        // master follower
        // this.driveRightFollower_One.changeControlMode(TalonControlMode.Follower);
        // this.driveRightFollower_One.set(driveRightMaster.getDeviceID());
        // this.driveRightFollower_Two.changeControlMode(TalonControlMode.Follower);
        // this.driveRightFollower_Two.set(driveRightMaster.getDeviceID());
        // list time!
        rightSide = new CANTalon[]{driveRightMaster, driveRightFollower_One, driveLeftFollower_Two};

        wpiDrive = new RobotDrive(driveLeftMaster, driveRightMaster);
        wpiDrive.setExpiration(Integer.MAX_VALUE);
        wpiDrive.setSafetyEnabled(false);

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
        driveLeftMaster.set(left * scaleDown * leftScale);
        driveRightMaster.set(right * scaleDown * rightScale);
        driveLeftFollower_One.set(left * scaleDown * leftScale);
        driveRightFollower_One.set(right * scaleDown * rightScale);
        driveLeftFollower_Two.set(left * scaleDown * leftScale);
        driveRightFollower_Two.set(right * scaleDown * rightScale);
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
     * Arcade drive implements single stick driving. This function lets you
     * directly provide joystick values from any source.
     *
     * @param moveValue     The value to use for forwards/backwards
     * @param rotateValue   The value to use for the rotate right/left
     * @param squaredInputs If set, decreases the sensitivity at low speeds
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
        TankDrive(leftMotorSpeed, -rightMotorSpeed);
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
        driveRightMaster.setEncPosition(0);
    }

    /**
     * Resets the encoder on the left side of the drivetrain.
     */
    public void resetLeft() {
        this.encLeft.reset();
        driveLeftMaster.setEncPosition(0);
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

    /**
     * Stage-sets the drivetrain. Please, for the love of all that is holy call
     * {@link Drivetrain#snapBackToReality()} after this.
     * <br>
     * On second thought, please don't use this at all.
     *
     * @param left  The left power
     * @param right The right power
     */
    public void stagedTankDrive(double left, double right) {
        driveLeftMaster.set(left);
        driveRightMaster.set(right);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driveLeftFollower_One.set(left);
        driveRightFollower_One.set(right);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driveLeftFollower_Two.set(left);
        driveRightFollower_Two.set(right);
    }

    /**
     * Sets all motors on the drivetrain to PercentVbus control (for staged drivetrain mode.)
     */
    public void initStaging() {
        for (CANTalon t : leftSide) {
            t.changeControlMode(TalonControlMode.PercentVbus);
        }
        for (CANTalon t : rightSide) {
            t.changeControlMode(TalonControlMode.PercentVbus);
        }
    }

    /**
     * Resets the drivetrain to "master/follower" control mode.
     */
    public void snapBackToReality() {
        // left reset
        driveLeftFollower_One.changeControlMode(TalonControlMode.Follower);
        driveLeftFollower_Two.changeControlMode(TalonControlMode.Follower);
        driveLeftFollower_One.set(driveLeftMaster.getDeviceID());
        driveLeftFollower_Two.set(driveLeftMaster.getDeviceID());
        // right reset
        driveRightFollower_One.changeControlMode(TalonControlMode.Follower);
        driveRightFollower_Two.changeControlMode(TalonControlMode.Follower);
        driveRightFollower_One.set(driveRightMaster.getDeviceID());
        driveRightFollower_Two.set(driveRightMaster.getDeviceID());
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
}
