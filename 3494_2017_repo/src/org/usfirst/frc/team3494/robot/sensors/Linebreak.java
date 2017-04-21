package org.usfirst.frc.team3494.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;

public class Linebreak {
	// internal analog trigger
	private AnalogTrigger at;
	private AnalogInput ai;

	public Linebreak(int port) {
		super();
		this.ai = new AnalogInput(port);
		this.at = new AnalogTrigger(this.ai);
	}

	public Linebreak(AnalogInput ai) {
		this(ai.getChannel());
	}

	public boolean get() {
		return at.getInWindow();
	}
	
	public double getVoltage() {
		return this.ai.getVoltage();
	}
}
