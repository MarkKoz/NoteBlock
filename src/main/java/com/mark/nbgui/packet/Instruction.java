package com.mark.nbgui.packet;

public enum Instruction {
	PlayPitch,
	ChangePitch,
	GetPitch;

	private static Instruction[] instructions = Instruction.values();

	public static Instruction get(int id) {
		return instructions[id];
	}
}
