package com.mark.nbgui.utils;

import com.mark.nbgui.data.Instrument;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NotePlayer {
	private TileEntityNote note = null;
	private Instrument instrument = null;
	private World world = null;
	private BlockPos pos = null;

	public NotePlayer(TileEntityNote note, BlockPos pos, World world,
	                  Instrument instrument) {
		this.note = note;
		this.pos = pos;
		this.world = world;
		this.instrument = instrument;
	}

	public NotePlayer(TileEntityNote note, BlockPos pos, World world) {
		this.note = note;
		this.pos = pos;
		this.world = world;
		this.instrument = null;
	}

	public TileEntityNote getNote() {
		return note;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public void play() {
		try {
			if (this.instrument == null) {
				this.note.triggerNote(this.world, this.pos);
				return;
			}
			if (this.world.getBlockState(this.pos.up()).getMaterial() == Material.AIR) {
				this.world.addBlockEvent(this.pos, Blocks.NOTEBLOCK, this
						.instrument
						.id(), this.note.note);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
