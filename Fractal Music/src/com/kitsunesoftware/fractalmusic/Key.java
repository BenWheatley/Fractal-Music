package com.kitsunesoftware.fractalmusic;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.*;

import java.util.HashMap;

public class Key {
	
	/** Notes in for a given key */
	private final static int SCALE_CHROMATIC[] =	{C,	CS,	D,	DS,	E,	F,	FS,	G,	GS,	A,	AS,	B},
							SCALE_PENTATONIC[] =	{CS, DS, FS, GS, AS},
							
							SCALE_C_MAJOR[] =		{C,  D,  E, F,  G,  A,  B},
							SCALE_G_MAJOR[] =		{C,  D,  E, FS, G,  A,  B},
							SCALE_D_MAJOR[] =		{CS, D,  E, FS, G,  A,  B},
							SCALE_A_MAJOR[] =		{CS, D,  E, FS, GS, A,  B},
							SCALE_E_MAJOR[] =		{CS, DS, E, FS, GS, A,  B},
							SCALE_B_MAJOR[] =		{CS, DS, E, FS, GS, AS, B},
							
							SCALE_FS_MAJOR[] =		{CS, DS, ES, FS, GS, AS, B},
							SCALE_CS_MAJOR[] =		{CS, DS, ES, FS, GS, AS, BS},
							SCALE_GS_MAJOR[] =		{CS, DS, ES, FSS, GS, AS, BS},
							
							SCALE_A_MINOR[] = 		{C,  D,  E,  F,	 GS, A,  B}, // TODO: confirm that this is correct
							SCALE_E_MINOR[] =		{C,  D,  E,  FS, G,  A,  B},
							SCALE_B_MINOR[] =		{CS, D,  E,  FS, G,  A,  B},
							SCALE_FS_MINOR[] =		{CS, D,  E,  FS, GS, A,  B},
							SCALE_CS_MINOR[] =		{CS, DS, E,  FS, GS, A,  B},
							SCALE_GS_MINOR[] =		{CS, DS, E,  FS, GS, AS, B},
							SCALE_DS_MINOR[] =		{CS, DS, ES, FS, GS, AS, B},
							SCALE_AS_MINOR[] =		{CS, DS, ES, FS, GS, AS, BS},
							SCALE_F_MINOR[] =		{C,  DF, EF, F,  G,  AF, BF};
	
	public static final Key CHROMATIC = new Key(SCALE_CHROMATIC, "Chromatic"),
							PENTATONIC = new Key(SCALE_PENTATONIC, "Pentatonic"),
							
							C_MAJOR = new Key(SCALE_C_MAJOR, "C Major"),
							G_MAJOR = new Key(SCALE_G_MAJOR, "G Major"),
							D_MAJOR = new Key(SCALE_D_MAJOR, "D Major"),
							A_MAJOR = new Key(SCALE_A_MAJOR, "A Major"),
							E_MAJOR = new Key(SCALE_E_MAJOR, "E Major"),
							B_MAJOR = new Key(SCALE_B_MAJOR, "B Major"),
							
							FS_MAJOR = new Key(SCALE_FS_MAJOR, "F♯ Major"),
							CS_MAJOR = new Key(SCALE_CS_MAJOR, "C♯ Major"),
							GS_MAJOR = new Key(SCALE_GS_MAJOR, "G♯ Major"),
							
							A_MINOR = new Key(SCALE_A_MINOR, "A Minor"),
							E_MINOR = new Key(SCALE_E_MINOR, "E Minor"),
							B_MINOR = new Key(SCALE_B_MINOR, "B Minor"),
							FS_MINOR = new Key(SCALE_FS_MINOR, "F♯ Minor"),
							CS_MINOR = new Key(SCALE_CS_MINOR, "C♯ Minor"),
							GS_MINOR = new Key(SCALE_GS_MINOR, "G♯ Minor"),
							DS_MINOR = new Key(SCALE_DS_MINOR, "D♯ Minor"),
							AS_MINOR = new Key(SCALE_AS_MINOR, "A♯ Minor"),
							F_MINOR = new Key(SCALE_F_MINOR, "F Minor");
	
	@SuppressWarnings("serial")
	public static final HashMap<String, Key> allKeys = new HashMap<String, Key>() {{
		Key all[] = {CHROMATIC, PENTATONIC, C_MAJOR, G_MAJOR, D_MAJOR, A_MAJOR, E_MAJOR, B_MAJOR,
				FS_MAJOR, CS_MAJOR, GS_MAJOR, A_MINOR, E_MINOR, B_MINOR, FS_MINOR, CS_MINOR, GS_MINOR,
				DS_MINOR, AS_MINOR, F_MINOR};
		for (Key k : all) {
			put(k.name, k);
		}
	}};
	
	public final int notes[];
	public final String name;
	
	public boolean equals(Key otherKey) {
		return this.name.trim().equalsIgnoreCase(otherKey.name.trim());
	}
	
	private Key(int notesArg[], String nameArg) {
		name = nameArg;
		notes = new int[notesArg.length];
		System.arraycopy(notesArg, 0, notes, 0, notesArg.length);
	}
}
