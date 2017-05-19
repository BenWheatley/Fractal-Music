package com.kitsunesoftware.fractalmusic;

import static com.kitsunesoftware.fractalmusic.FractalMusicConstants.*;

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
	
	/** Identifiers (not an array of notes!) for a key */
	private static final int INDEX_CHROMATIC = 0,
							INDEX_PENTATONIC = 1,
							
							INDEX_C_MAJOR = 2,
							INDEX_G_MAJOR = 3,
							INDEX_D_MAJOR = 4,
							INDEX_A_MAJOR = 5,
							INDEX_E_MAJOR = 6,
							INDEX_B_MAJOR = 7,
							
							INDEX_FS_MAJOR = 8,
							INDEX_CS_MAJOR = 9,
							INDEX_GS_MAJOR = 10,
							
							INDEX_A_MINOR = 11,
							INDEX_E_MINOR = 12,
							INDEX_B_MINOR = 13,
							INDEX_FS_MINOR = 14,
							INDEX_CS_MINOR = 15,
							INDEX_GS_MINOR = 16,
							INDEX_DS_MINOR = 17,
							INDEX_AS_MINOR = 18,
							INDEX_F_MINOR = 19;
							
	public static final int INDEX_KEY_COUNT = 20;

	public static final Key CHROMATIC = new Key(INDEX_CHROMATIC),
							PENTATONIC = new Key(INDEX_PENTATONIC),
							
							C_MAJOR = new Key(INDEX_C_MAJOR),
							G_MAJOR = new Key(INDEX_G_MAJOR),
							D_MAJOR = new Key(INDEX_D_MAJOR),
							A_MAJOR = new Key(INDEX_A_MAJOR),
							E_MAJOR = new Key(INDEX_E_MAJOR),
							B_MAJOR = new Key(INDEX_B_MAJOR),
							
							FS_MAJOR = new Key(INDEX_FS_MAJOR),
							CS_MAJOR = new Key(INDEX_CS_MAJOR),
							GS_MAJOR = new Key(INDEX_GS_MAJOR),
							
							A_MINOR = new Key(INDEX_A_MINOR),
							E_MINOR = new Key(INDEX_E_MINOR),
							B_MINOR = new Key(INDEX_B_MINOR),
							FS_MINOR = new Key(INDEX_FS_MINOR),
							CS_MINOR = new Key(INDEX_CS_MINOR),
							GS_MINOR = new Key(INDEX_GS_MINOR),
							DS_MINOR = new Key(INDEX_DS_MINOR),
							AS_MINOR = new Key(INDEX_AS_MINOR),
							F_MINOR = new Key(INDEX_F_MINOR);
	
	private static final Key[] allKeysArray = {CHROMATIC, PENTATONIC,
		C_MAJOR, G_MAJOR, D_MAJOR, A_MAJOR, E_MAJOR, B_MAJOR, FS_MAJOR, CS_MAJOR, GS_MAJOR,
		A_MINOR, E_MINOR, B_MINOR, FS_MINOR, CS_MINOR, GS_MINOR, DS_MINOR, AS_MINOR, F_MINOR};
	
	public final int notes[];
	public final String name;
	
	private final int keyType;
	
	public boolean equals(Key otherKey) {
		return this.keyType == otherKey.keyType;
	}
	
	private Key(int keyTypeArg) {
		keyType = keyTypeArg;
		int src[] = {};
		switch (keyType) {
		case INDEX_CHROMATIC: src = SCALE_CHROMATIC; name = "Chromatic"; break;
		case INDEX_PENTATONIC: src = SCALE_PENTATONIC; name = "Pentatonic"; break;
		
		case INDEX_C_MAJOR: src = SCALE_C_MAJOR; name = "C Major"; break;
		case INDEX_G_MAJOR: src = SCALE_G_MAJOR; name = "G Major"; break;
		case INDEX_D_MAJOR: src = SCALE_D_MAJOR; name = "D Major"; break;
		case INDEX_A_MAJOR: src = SCALE_A_MAJOR; name = "A Major"; break;
		case INDEX_E_MAJOR: src = SCALE_E_MAJOR; name = "E Major"; break;
		case INDEX_B_MAJOR: src = SCALE_B_MAJOR; name = "B Major"; break;
		
		case INDEX_FS_MAJOR: src = SCALE_FS_MAJOR; name = "F♯ Major"; break;
		case INDEX_CS_MAJOR: src = SCALE_CS_MAJOR; name = "C♯ Major"; break;
		case INDEX_GS_MAJOR: src = SCALE_GS_MAJOR; name = "G♯ Major"; break;
		
		case INDEX_A_MINOR: src = SCALE_A_MINOR; name = "A Minor"; break;
		case INDEX_E_MINOR: src = SCALE_E_MINOR; name = "E Minor"; break;
		case INDEX_B_MINOR: src = SCALE_B_MINOR; name = "B Minor"; break;
		case INDEX_FS_MINOR: src = SCALE_FS_MINOR; name = "F♯ Minor"; break;
		case INDEX_CS_MINOR: src = SCALE_CS_MINOR; name = "C♯ Minor"; break;
		case INDEX_GS_MINOR: src = SCALE_GS_MINOR; name = "G♯ Minor"; break;
		case INDEX_DS_MINOR: src = SCALE_DS_MINOR; name = "D♯ Minor"; break;
		case INDEX_AS_MINOR: src = SCALE_AS_MINOR; name = "A♯ Minor"; break;
		case INDEX_F_MINOR: src = SCALE_F_MINOR; name = "F Minor"; break;
		default: throw new InvalidKeyException();
		}
		notes = new int[src.length];
		System.arraycopy(src, 0, notes, 0, src.length);
	}
	
	public class InvalidKeyException extends RuntimeException {	}
	
	public static Key keyByName(final String keyName) {
		for (int i=0; i<allKeysArray.length; ++i) {
			if (allKeysArray[i].name.equalsIgnoreCase(keyName)) {
				return allKeysArray[i];
			}
		}
		return null;
	}
}
