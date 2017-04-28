package org.usfirst.frc.team3494.robot;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto;
import org.usfirst.frc.team3494.robot.commands.auto.NullAuto;
import org.usfirst.frc.team3494.robot.commands.auto.PIDAngleDrive;
import org.usfirst.frc.team3494.robot.subsystems.Climber;
import org.usfirst.frc.team3494.robot.subsystems.ClimberPTO;
import org.usfirst.frc.team3494.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3494.robot.subsystems.GearTake_2;
import org.usfirst.frc.team3494.robot.subsystems.Kompressor;
import org.usfirst.frc.team3494.robot.vision.GripPipeline;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 * The JVM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Drivetrain driveTrain;
	public static Climber climber;
	public static Kompressor kompressor;
	public static GearTake_2 gearTake;
	public static ClimberPTO pto;

	/**
	 * The gyro board mounted to the RoboRIO.
	 *
	 * @since 0.0.2
	 */
	public static AHRS ahrs;
	/**
	 * The robot's PDP panel. Use for reading current draw.
	 */
	public static PowerDistributionPanel pdp;

	private static Command autonomousCommand;
	public static SendableChooser<Command> chooser;
	public static Preferences prefs;

	public static UsbCamera camera_0;
	// Vision items
	private static final int IMG_WIDTH = 320;
	VisionThread visionThread;
	public double centerX = 0.0;
	public double absolutelyAverage = 0.0;
	@SuppressWarnings("unused")
	private ArrayList<MatOfPoint> filteredContours;
	private ArrayList<Double> averages = new ArrayList<>();

	private final Object imgLock = new Object();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		System.out.println("Hello FTAs, how are you doing?");
		System.out.println("Because I'm a QUADRANGLE.");
		// Init hardware
		pdp = new PowerDistributionPanel();
		ahrs = new AHRS(SerialPort.Port.kMXP);
		while (ahrs.isCalibrating()) {
			continue;
		}
		ahrs.reset();
		camera_0 = CameraServer.getInstance().startAutomaticCapture("Gear View", 0);
		camera_0.setExposureManual(20);
		// Init subsystems
		driveTrain = new Drivetrain();
		climber = new Climber();
		kompressor = new Kompressor();
		gearTake = new GearTake_2();
		gearTake.closeHolder();
		gearTake.setRamp(Value.kReverse);
		pto = new ClimberPTO();
		pto.disengagePTO();
		// Non subsystem software init
		prefs = Preferences.getInstance();
		chooser = new SendableChooser<>();
		oi = new OI();
		// Auto programs come after all subsystems are created
		chooser.addDefault("Drive to the baseline", new ConstructedAuto(AutoGenerator.crossBaseLine()));
		chooser.addObject("Center Gear Placer", new ConstructedAuto(AutoGenerator.placeCenterGear()));
		chooser.addObject("Passive Gear Placer - Robot turn right",
				new ConstructedAuto(AutoGenerator.gearPassiveRight()));
		chooser.addObject("Passive Gear Placer - Robot turn left",
				new ConstructedAuto(AutoGenerator.gearPassiveLeft()));
		chooser.addObject("Do nothing", new NullAuto());
		chooser.addObject("Active Gear placer - Robot turn left", new ConstructedAuto(AutoGenerator.activeGearLeft()));
		chooser.addObject("Active Gear placer - Robot turn right",
				new ConstructedAuto(AutoGenerator.activeGearRight()));
		chooser.addObject("Full auto (active gear + drive to loading station) - Red alliance, robot turn right",
				new ConstructedAuto(AutoGenerator.fullRedRight()));
		chooser.addObject("Full auto (active gear + drive to loading station) - Red alliance, robot turn left",
				new ConstructedAuto(AutoGenerator.fullRedLeft()));
		chooser.addObject("Full auto (active gear + drive to loading station) - Blue alliance, robot turn right",
				new ConstructedAuto(AutoGenerator.fullBlueRight()));
		chooser.addObject("Full auto (active gear + drive to loading station) - Blue alliance, robot turn left",
				new ConstructedAuto(AutoGenerator.fullBlueLeft()));
		chooser.addObject("Test - turn 60 degrees", new PIDAngleDrive(90));
		// put chooser on DS
		SmartDashboard.putData("AUTO CHOOSER", chooser);
		// Create and start vision thread
		visionThread = new VisionThread(camera_0, new GripPipeline(), pipeline -> {
			if (!pipeline.filterContoursOutput().isEmpty()) {
				if (pipeline.filterContoursOutput().size() >= 2) {
					MatOfPoint firstCont = pipeline.filterContoursOutput().get(0);
					MatOfPoint secondCont = pipeline.filterContoursOutput().get(1);
					double average_y_one = 0;
					for (Point p : firstCont.toList()) {
						average_y_one += p.y;
					}
					double average_y_two = 0;
					for (Point p : secondCont.toList()) {
						average_y_two += p.y;
					}
					// divide by number of points to give actual average
					average_y_two = average_y_two / secondCont.toList().size();
					average_y_one = average_y_one / firstCont.toList().size();
					Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
					synchronized (imgLock) {
						centerX = r.x + r.width / 2;
						filteredContours = pipeline.filterContoursOutput();
						// add averages to list
						averages.add(average_y_one);
						averages.add(average_y_two);
					}
				} else {
					Rect r = Imgproc.boundingRect(pipeline.findContoursOutput().get(0));
					synchronized (imgLock) {
						centerX = r.x + r.width / 2;
						filteredContours = pipeline.filterContoursOutput();
					}
				}
			}
		});
		visionThread.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings &amp; commands.
	 */
	@Override
	public void autonomousInit() {
		// reset gyro
		Robot.ahrs.reset();
		// set ramps
		for (CANTalon t : Robot.driveTrain.leftSide) {
			t.setVoltageRampRate(0);
			t.enableBrakeMode(true);
		}
		for (CANTalon t : Robot.driveTrain.rightSide) {
			t.setVoltageRampRate(0);
			t.enableBrakeMode(true);
		}
		autonomousCommand = chooser.getSelected();
		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			System.out.println("Selected command " + chooser.getSelected().getName());
			autonomousCommand.start();
		} else {
			System.out.println("Defaulting to track the shiny");
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putData(Scheduler.getInstance());
		if (autonomousCommand != null) {
			Scheduler.getInstance().run();
		} else {
			double centerX;
			synchronized (imgLock) {
				centerX = this.centerX;
				System.out.println("CenterX: " + this.centerX);
			}
			double turn = centerX - Robot.IMG_WIDTH / 2;
			// drive with turn
			System.out.println("Turn: " + turn);
			Robot.driveTrain.wpiDrive.arcadeDrive(0.5, turn * 0.005 * -1);
		}
		Robot.putDebugInfo();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
		camera_0.setExposureAuto();
		camera_0.setWhiteBalanceAuto();
		// set ramps
		for (CANTalon t : Robot.driveTrain.leftSide) {
			t.setVoltageRampRate(Drivetrain.RAMP);
			t.enableBrakeMode(true);
		}
		for (CANTalon t : Robot.driveTrain.rightSide) {
			t.setVoltageRampRate(Drivetrain.RAMP);
			t.enableBrakeMode(true);
		}
		Robot.driveTrain.setInputRange(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
		Robot.driveTrain.setOutputRange(-0.5, 0.5);
		Robot.driveTrain.teleop = true;
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		/*
		 * if (Robot.climber.getMotorCurrent() > 10.0D) {
		 * Robot.climber.engagePTO(); }
		 */
		Scheduler.getInstance().run();
		Robot.putDebugInfo();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}

	public static void putDebugInfo() {
		SmartDashboard.putNumber("[left] distance", Robot.driveTrain.getLeftDistance(UnitTypes.RAWCOUNT));
		SmartDashboard.putNumber("[left] distance inches", Robot.driveTrain.getLeftDistance(UnitTypes.INCHES));

		SmartDashboard.putNumber("[right] distance", Robot.driveTrain.getRightDistance(UnitTypes.RAWCOUNT));
		SmartDashboard.putNumber("[right] distance inches", Robot.driveTrain.getRightDistance(UnitTypes.INCHES));

		SmartDashboard.putNumber("Talon Distance Right", Robot.driveTrain.rightSide[0].getPosition());
		SmartDashboard.putNumber("Talon Distance Left", Robot.driveTrain.leftSide[0].getPosition());

		SmartDashboard.putNumber("Motor 0", Robot.pdp.getCurrent(0));
		SmartDashboard.putNumber("Motor 1", Robot.pdp.getCurrent(1));
		SmartDashboard.putNumber("Motor 2", Robot.pdp.getCurrent(2));

		SmartDashboard.putNumber("Motor 13", Robot.pdp.getCurrent(13));
		SmartDashboard.putNumber("Motor 14", Robot.pdp.getCurrent(14));
		SmartDashboard.putNumber("Motor 15", Robot.pdp.getCurrent(15));

		SmartDashboard.putNumber("Climber Motor", Robot.pdp.getCurrent(RobotMap.CLIMBER_MOTOR_PDP));

		SmartDashboard.putBoolean("Linebroken", Robot.gearTake.at.getInWindow());
	}

	public static Command GetAuto() {
		return Robot.autonomousCommand;
	}
}
