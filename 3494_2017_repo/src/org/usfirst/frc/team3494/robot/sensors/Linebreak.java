package org.usfirst.frc.team3494.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;

public class Linebreak {
	// internal analog trigger
	private AnalogTrigger at;

	public Linebreak(int port) {
		super();
		this.at = new AnalogTrigger(port);
	}

	public Linebreak(AnalogInput ai) {
		this(ai.getChannel());
	}

	public boolean get() {
		return at.getInWindow();
	}
}
