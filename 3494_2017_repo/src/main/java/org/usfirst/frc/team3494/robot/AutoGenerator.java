package org.usfirst.frc.team3494.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3494.robot.commands.auto.*;

import java.util.ArrayList;

/**
 * Class containing methods that return valid lists to pass to
 * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
 *
 * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
 * @since 0.0.3
 */
public class AutoGenerator {
    /**
     * The distance of the first pull in gear placing.
     */
    private static final double FIRST_PULL = 102 - 18 - 4;
    /**
     * The angle to turn after the first pull in gear placing.
     */
    private static final double ANGLE = 60;
    /**
     * After turning {@link AutoGenerator#ANGLE} degrees, drive this distance.
     */
    private static final double SECOND_PULL = 35;

    /**
     * Test method. Drives to XY (36, 36) (inches).
     *
     * @return A list of commands suitable for use with
     * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
     * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * @see org.usfirst.frc.team3494.robot.commands.auto.XYDrive
     * @since 0.0.3
     */
    public static ArrayList<Command> autoOne() {
        ArrayList<Command> list = new ArrayList<>();
        list.add(new XYDrive(36, 36));
        return list;
    }

    /**
     * Drives to the baseline.
     *
     * @return A list of commands suitable for use with
     * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto}.
     * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive
     * @since 0.0.3
     */
    static ArrayList<Command> crossBaseLine() {
        ArrayList<Command> list = new ArrayList<>();
        list.add(new DistanceDrive(90, UnitTypes.INCHES));
        return list;
    }

    static ArrayList<Command> placeCenterGear() {
        ArrayList<Command> list = new ArrayList<>();
        list.add(new PIDFullDrive(110.75 - 34.0D)); // shh
        return list;
    }

    static ArrayList<Command> activeCenterGear() {
        ArrayList<Command> list = new ArrayList<>();
        list.add(new PegDrive(0, false));
        // list.add(new DistanceDrive(-0.5, UnitTypes.INCHES));
        list.add(new DropGear(true));
        list.add(new DistanceDrive(-5, UnitTypes.INCHES));
        return list;
    }

    /**
     * Drives forward, turns right, drives forward again.
     *
     * @return A list for use with
     * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * ConstructedAuto}
     * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * Constructed Auto
     */
    public static ArrayList<Command> gearPassiveRight() {
        ArrayList<Command> list = new ArrayList<>();
        list.add(new PIDFullDrive(FIRST_PULL - 2, 0, true));
        list.add(new PIDAngleDrive(ANGLE));
        list.add(new PIDFullDrive(SECOND_PULL));
        // list.add(new DistanceDrive(-60, UnitTypes.INCHES));
        return list;
    }

    /**
     * Drives forward, turns left, drives forward again.
     *
     * @return A list for use with
     * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * ConstructedAuto}
     * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * Constructed Auto
     */
    static ArrayList<Command> gearPassiveLeft() {
        ArrayList<Command> list = new ArrayList<>();
        list.add(new PIDFullDrive(FIRST_PULL, 0, true));
        list.add(new PIDAngleDrive(-ANGLE));
        list.add(new PIDFullDrive(SECOND_PULL));
        // list.add(new DistanceDrive(-60, UnitTypes.INCHES));
        return list;
    }

    /**
     * Same as {@link AutoGenerator#gearPassiveLeft()}, but drops the gear on
     * the peg at the end.
     *
     * @return A list for use with
     * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * ConstructedAuto}
     * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * Constructed Auto
     */
    static ArrayList<Command> activeGearLeft() {
        ArrayList<Command> list = AutoGenerator.gearPassiveLeft();
        list.add(new DropGear(false));
        list.add(new PIDFullDrive(-15));
        list.add(new SetGearGrasp(Value.kReverse));
        list.add(new PIDAngleDrive(ANGLE));
        return list;
    }

    /**
     * Same as {@link AutoGenerator#gearPassiveRight()}, but drops the gear on
     * the peg at the end.
     *
     * @return A list for use with
     * {@link org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * ConstructedAuto}
     * @see org.usfirst.frc.team3494.robot.commands.auto.ConstructedAuto
     * Constructed Auto
     */
    static ArrayList<Command> activeGearRight() {
        ArrayList<Command> list = AutoGenerator.gearPassiveRight();
        list.add(new DropGear(false));
        list.add(new PIDFullDrive(-15));
        list.add(new SetGearGrasp(Value.kReverse));
        list.add(new PIDAngleDrive(-ANGLE));
        return list;
    }

    static ArrayList<Command> fullBlueRight() {
        ArrayList<Command> list = AutoGenerator.activeGearRight();
        list.add(new PIDFullDrive(300));
        return list;
    }

    static ArrayList<Command> fullBlueLeft() {
        ArrayList<Command> list = AutoGenerator.activeGearLeft();
        list.add(new PIDFullDrive(102));
        list.add(new PIDAngleDrive(40));
        list.add(new PIDFullDrive(250));
        list.add(new PIDFullDrive(-40));
        return list;
    }

    static ArrayList<Command> fullRedRight() {
        ArrayList<Command> list = AutoGenerator.activeGearRight();
        list.add(new PIDFullDrive(102));
        list.add(new PIDAngleDrive(-40));
        list.add(new PIDFullDrive(250));
        list.add(new PIDFullDrive(40));
        return list;
    }

    static ArrayList<Command> fullRedLeft() {
        ArrayList<Command> list = AutoGenerator.activeGearLeft();
        list.add(new PIDFullDrive(300));
        return list;
    }
}
