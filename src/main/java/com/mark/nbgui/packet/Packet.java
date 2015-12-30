package com.mark.nbgui.packet;

import com.mark.nbgui.data.Pitch;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class Packet implements IMessage {
    protected int x;
    protected int y;
    protected int z;
    protected Pitch pitch = null;
    protected Instruction instruction;

    public Packet() {
    }

    public Packet(int x, int y, int z, Pitch pitch, Instruction instruction) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.instruction = instruction;
    }

    public Packet(int x, int y, int z, Instruction instruction) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = new Pitch(0);
        this.instruction = instruction;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	x = buf.readInt();
    	y = buf.readInt();
    	z = buf.readInt();
    	pitch = new Pitch(buf.readInt());
    	instruction = Instruction.get(buf.readInt());
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(this.x);
    	buf.writeInt(this.y);
    	buf.writeInt(this.z);
        buf.writeInt(this.pitch.getNum());
        buf.writeInt(instruction.ordinal());
    }
}