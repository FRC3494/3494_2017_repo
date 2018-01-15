package org.usfirst.frc.team3494.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;

public class PegDrive extends Command {
    private double angle;
    private boolean fast;

    public PegDrive(double angle, boolean f) {
        requires(Robot.driveTrain);
        requires(Robot.gearTake);
        // distance = dist;
        this.angle = angle;
        fast = f;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.driveTrain.resetLeft();
        Robot.driveTrain.resetRight();
        try {
            Thread.sleep(RobotMap.TALON_RESET_DELAY);
        } catch (InterruptedException e) {
            System.out.println("ah crap");
            e.printStackTrace();
        }
        Robot.ahrs.reset();
        Robot.driveTrain.enable();
        Robot.driveTrain.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        SmartDashboard.putNumber("angle", Robot.ahrs.getAngle());
        if (!fast) {
            if (!Robot.gearTake.at.getInWindow()) {
                Robot.driveTrain.ArcadeDrive(-0.5, -Robot.driveTrain.PIDTune, true);
            } else {
                Robot.driveTrain.ArcadeDrive(0.5, -Robot.driveTrain.PIDTune, true);
            }
        } else {
            if (!Robot.gearTake.at.getInWindow()) {
                Robot.driveTrain.ArcadeDrive(-0.6, -Robot.driveTrain.PIDTune, true);
            } else {
                Robot.driveTrain.ArcadeDrive(0.6, -Robot.driveTrain.PIDTune, true);
            }
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return Robot.gearTake.at.getInWindow();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        System.out.println("done PID driving");
        Robot.driveTrain.stopAll();
        Robot.driveTrain.disable();
        Robot.driveTrain.resetRight();
        Robot.driveTrain.resetLeft();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}