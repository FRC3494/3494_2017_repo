package org.usfirst.frc.team3494.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogTrigger;

public class LineBreak {
	/**
	 * Actual analog channel.
	 */
	private AnalogInput ai1;
	/**
	 * Analog trigger object.
	 */
	private AnalogTrigger trigger1;

	public LineBreak(int channel) {
		this.ai1 = new AnalogInput(channel);
		this.trigger1 = new AnalogTrigger(ai1);
		this.trigger1.setLimitsVoltage(0.777, 3.703);
	}

	public LineBreak() {
		this(1);
	}

	/**
	 * Returns true if the trigger is broken.
	 */
	public boolean getBroken() {
		if (!this.trigger1.getInWindow()) {
			return trigger1.getTriggerState();
		} else {
			return false;
		}
	}
}
