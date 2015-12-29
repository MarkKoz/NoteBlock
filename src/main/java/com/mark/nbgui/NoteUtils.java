package com.mark.nbgui;

import net.minecraft.block.Block;
import net.minecraft.block.BlockNote;
import net.minecraft.block.material.Material;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NoteUtils {
    private static NoteBlockEvent.Instrument[] instruments
            = NoteBlockEvent.Instrument.values();

    private static NoteBlockEvent.Note[] notes
            = NoteBlockEvent.Note.values();

    private static NoteBlockEvent.Octave[] octaves
            = NoteBlockEvent.Octave.values();

    public static NoteBlockEvent.Note getNoteFromInput(String input) {
    	input.replaceAll("\\s+","");
    	String noteLetter = input.substring(0,1);
    	String noteFinal;
    	
    	if (input.contains(I18n.format("nbgui.string.note.sharp", new Object[0])) || input.contains("sharp") || input.contains("#") || input.contains("♯")) {
    		//TODO Convert flats to sharps
    		//TODO Add option to change key and save it, or option to switch between displaying flats/sharps
    		noteFinal = noteLetter.toUpperCase()+"_SHARP";
    	}
    	else {
        	noteFinal = noteLetter.toUpperCase();	
    	}
    	return NoteBlockEvent.Note.valueOf(noteFinal);
    }
    
    public static NoteBlockEvent.Octave getOctaveFromInput (String octave) {   	
    	if (octave.equals("3")) {
    		octave = "LOW"; 		
    	}
    	else if (octave.equals("4")) {
    		octave = "MID"; 		
    	}
    	else if (octave.equals("5")) {
    		octave = "HIGH"; 		
    	}
    	else {
    		octave = null;
    	}
    	return NoteBlockEvent.Octave.valueOf(octave);
    }
    
    public static String getInstrumentString(NoteBlockEvent.Instrument instrument) {
        switch (instrument) {
            case PIANO:
                return I18n.format("nbgui.string.instrument.piano", new Object[0]);
            case BASSDRUM:
                return I18n.format("nbgui.string.instrument.bassdrum", new Object[0]);
            case SNARE:
                return I18n.format("nbgui.string.instrument.snaredrum", new Object[0]);
            case CLICKS:
                return I18n.format("nbgui.string.instrument.clicks", new Object[0]);
            case BASSGUITAR:
                return I18n.format("nbgui.string.instrument.doublebass", new Object[0]);
        }
        return "";
    }   
    
    public static String getNoteString(NoteBlockEvent.Note note) {
        switch (note) {
            case F_SHARP:
                return "F♯";
            case G:
                return "G";
            case G_SHARP:
                return "G♯";
            case A:
                return "A";
            case A_SHARP:
            	//Minecraft.getMinecraft().gameSettings.language
                return "A♯";
            case B:
                return "B";
            case C:
                return "C";
            case C_SHARP:
                return "C♯";
            case D:
                return "D";
            case D_SHARP:
                return "D♯";
            case E:
                return "E";
            case F:
                return "F";
        }
        return "";
    }

    public static String getOctaveString(NoteBlockEvent.Octave octave) {
        switch (octave) {
            case LOW:
                return "3";
            case MID:
                return "4";
            case HIGH:
                return "5";
        }
        return "";
    }

    public static String parseNote(String note) {
    	note.replaceAll("\\s+","");
    	String noteLetter = note.substring(0,1);
    	String noteFinal;
    	if (note.contains("sharp") || note.contains("#") || note.contains("♯")) {
    		//TODO Convert flats to sharps
    		//TODO Add option to change key and save it, or option to switch between displaying flats/sharps
    		return noteLetter.toUpperCase()+"_SHARP";
    	} else {
        	return noteLetter.toUpperCase();
    	}
    }
    
    public static int parseNoteInt (String note) {
    	if (parseNote(note).equals("F_SHARP")) {
    		return 0;
    	} else if (parseNote(note).equals("G")) {
    		return 1;
    	} else if (parseNote(note).equals("G_SHARP")) {
    		return 2;
    	} else if (parseNote(note).equals("A")) {
    		return 3;
    	} else if (parseNote(note).equals("A_SHARP")) {
    		return 4;
    	} else if (parseNote(note).equals("B")) {
    		return 5;
    	} else if (parseNote(note).equals("C")) {
    		return 6;
    	} else if (parseNote(note).equals("C_SHARP")) {
    		return 7;
    	} else if (parseNote(note).equals("D")) {
    		return 8;
    	} else if (parseNote(note).equals("D_SHARP")) {
    		return 9;
    	} else if (parseNote(note).equals("E")) {
    		return 10;
    	} else if (parseNote(note).equals("F")) {
    		return 11;
    	} else {
    		return 12;
    	}
    }
    
    public static String parseNoteStr (String note) {
    	if (parseNoteInt(note) == 0) {
    		return "F♯";
    	} else if (parseNoteInt(note) == 1) {
    		return "G";
    	} else if (parseNoteInt(note) == 2) {
    		return "G♯";
    	} else if (parseNoteInt(note) == 3) {
    		return "A";
    	} else if (parseNoteInt(note) == 4) {
    		return "A♯";
    	} else if (parseNoteInt(note) == 5) {
    		return "B";
    	} else if (parseNoteInt(note) == 6) {
    		return "C";
    	} else if (parseNoteInt(note) == 7) {
    		return "C♯";
    	} else if (parseNoteInt(note) == 8) {
    		return "D";
    	} else if (parseNoteInt(note) == 9) {
    		return "D♯";
    	} else if (parseNoteInt(note) == 10) {
    		return "E";
    	} else if (parseNoteInt(note) == 11) {
    		return "F";
    	} else {
    		return null;
    	}
    }
    
    public static int parsePitch (String note, String octave) {
    	int octaveInt = Integer.parseInt(octave);
    	
    	//TODO Based on real music rules, octave should reset at C, not F#. 
    	//TODO Add option to select initial note of block.
    	if (parseNoteInt(note) != 12) {
        	if(octaveInt == 4) {
        		return parseNoteInt(note) + 13;
        	} else if(octaveInt == 5 && parseNoteInt(note) == 0) {
        		return parseNoteInt(note) + 25;
        	} else if (octaveInt >= 5 || octaveInt <= 3) {  		
        		octaveInt = 3;
        		return parseNoteInt(note);
        	}
    	}
    	return 12;
    	//TODO Return to previous noteId if input was invalid.
    }
    
    public static int noteToInt(NoteBlockEvent.Note note) {
        return 0;
    }

    public static NoteBlockEvent.Instrument getNoteBlockInstrument(Block blockUnder) {
        if (blockUnder == null) {
            return NoteBlockEvent.Instrument.PIANO;
        }
        Material material = blockUnder.getMaterial();
        byte id = 0;
        if (material == Material.rock) {
            id = 1;
        } else if (material == Material.sand) {
            id = 2;
        } else if (material == Material.glass) {
            id = 3;
        } else if (material == Material.wood) {
            id = 4;
        }
        return NoteUtils.instruments[id];
    }

    public static NoteBlockEvent.Note getBlockNote(TileEntityNote entityNote) {
        byte note = entityNote.note;

        return NoteUtils.notes[note % 12];
    }

    public static NoteBlockEvent.Octave getBlockOctave(TileEntityNote entityNote) {
        byte note = entityNote.note;
        return note < 12
                ? NoteBlockEvent.Octave.LOW : note == 24
                ? NoteBlockEvent.Octave.HIGH : NoteBlockEvent.Octave.MID;
    }
}
