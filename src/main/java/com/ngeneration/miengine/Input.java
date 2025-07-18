package com.ngeneration.miengine;

import org.lwjgl.glfw.GLFW;

import com.ngeneration.miengine.graphics.OrthographicCamera;
import com.ngeneration.miengine.math.Vector2;
import com.ngeneration.miengine.scene.GameObject;

public interface Input {

	public final Keys Keys = new Keys();

	public float getX();

	public float getY();

	public boolean isKeyPressed(int key);

	public boolean isKeyJustPressed(int key);

	public boolean isTouched();

	public class Keys {

		public final int MOUSE_BTN1 = GLFW.GLFW_MOUSE_BUTTON_1;
		public final int MOUSE_BTN2 = GLFW.GLFW_MOUSE_BUTTON_2;
		public final int MOUSE_BTN3 = GLFW.GLFW_MOUSE_BUTTON_3;

		/**
		 * The first number in the range of ids used for key events.
		 */
		public final int KEY_FIRST = 400;

		/**
		 * The last number in the range of ids used for key events.
		 */
		public final int KEY_LAST = 402;

		/**
		 * The "key typed" event. This event is generated when a character is entered.
		 * In the simplest case, it is produced by a single key press. Often, however,
		 * characters are produced by series of key presses, and the mapping from key
		 * pressed events to key typed events may be many-to-one or many-to-many.
		 */
		public final int KEY_TYPED = 3;

		/**
		 * The "key pressed" event. This event is generated when a key is pushed down.
		 */
		public final int KEY_PRESSED = 1; // Event.KEY_PRESS

		/**
		 * The "key released" event. This event is generated when a key is let up.
		 */
		public final int KEY_RELEASED = 0; // Event.KEY_RELEASE

		public final int KEY_REPEATED = 2; // Event.KEY_RELEASE

		/* Virtual key codes. */

		/** Constant for the ENTER virtual key. */
		public final int VK_ENTER = 257; // '\n';

		/** Constant for the BACK_SPACE virtual key. */
		public final int VK_BACK_SPACE = GLFW.GLFW_KEY_BACKSPACE;

		/** Constant for the TAB virtual key. */
		public final int VK_TAB = GLFW.GLFW_KEY_TAB;

		/** Constant for the CANCEL virtual key. */
		public final int VK_CANCEL = 0x03;

		/** Constant for the CLEAR virtual key. */
		public final int VK_CLEAR = 0x0C;

		/** Constant for the SHIFT virtual key. */
		public final int VK_SHIFT = GLFW.GLFW_KEY_LEFT_SHIFT;

		/** Constant for the CONTROL virtual key. */
		public final int VK_CONTROL = GLFW.GLFW_KEY_LEFT_CONTROL;

		/** Constant for the ALT virtual key. */
		public final int VK_ALT = GLFW.GLFW_KEY_LEFT_ALT;

		/** Constant for the PAUSE virtual key. */
		public final int VK_PAUSE = 0x13;

		/** Constant for the CAPS_LOCK virtual key. */
		public final int VK_CAPS_LOCK = 0x14;

		/** Constant for the ESCAPE virtual key. */
		public final int VK_ESCAPE = GLFW.GLFW_KEY_ESCAPE;

		/** Constant for the SPACE virtual key. */
		public final int VK_SPACE = 0x20;

		/** Constant for the PAGE_UP virtual key. */
		public final int VK_PAGE_UP = 0x21;

		/** Constant for the PAGE_DOWN virtual key. */
		public final int VK_PAGE_DOWN = 0x22;

		/** Constant for the END virtual key. */
		public final int VK_END = GLFW.GLFW_KEY_END;

		/** Constant for the HOME virtual key. */
		public final int VK_HOME = GLFW.GLFW_KEY_HOME;

		/**
		 * Constant for the non-numpad <b>left</b> arrow key.
		 * 
		 * @see #VK_KP_LEFT
		 */
		public final int VK_LEFT = GLFW.GLFW_KEY_LEFT;

		/**
		 * Constant for the non-numpad <b>up</b> arrow key.
		 * 
		 * @see #VK_KP_UP
		 */
		public final int VK_UP = GLFW.GLFW_KEY_UP;

		/**
		 * Constant for the non-numpad <b>right</b> arrow key.
		 * 
		 * @see #VK_KP_RIGHT
		 */
		public final int VK_RIGHT = GLFW.GLFW_KEY_RIGHT;

		/**
		 * Constant for the non-numpad <b>down</b> arrow key.
		 * 
		 * @see #VK_KP_DOWN
		 */
		public final int VK_DOWN = GLFW.GLFW_KEY_DOWN;

		/**
		 * Constant for the comma key, ","
		 */
		public final int VK_COMMA = 0x2C;

		/**
		 * Constant for the minus key, "-"
		 * 
		 * @since 1.2
		 */
		public final int VK_MINUS = 0x2D;

		/**
		 * Constant for the period key, "."
		 */
		public final int VK_PERIOD = 0x2E;

		/**
		 * Constant for the forward slash key, "/"
		 */
		public final int VK_SLASH = 0x2F;

		/** VK_0 thru VK_9 are the same as ASCII '0' thru '9' (0x30 - 0x39) */

		/** Constant for the "0" key. */
		public final int VK_0 = 0x30;

		/** Constant for the "1" key. */
		public final int VK_1 = 0x31;

		/** Constant for the "2" key. */
		public final int VK_2 = 0x32;

		/** Constant for the "3" key. */
		public final int VK_3 = 0x33;

		/** Constant for the "4" key. */
		public final int VK_4 = 0x34;

		/** Constant for the "5" key. */
		public final int VK_5 = 0x35;

		/** Constant for the "6" key. */
		public final int VK_6 = 0x36;

		/** Constant for the "7" key. */
		public final int VK_7 = 0x37;

		/** Constant for the "8" key. */
		public final int VK_8 = 0x38;

		/** Constant for the "9" key. */
		public final int VK_9 = 0x39;

		/**
		 * Constant for the semicolon key, ";"
		 */
		public final int VK_SEMICOLON = 0x3B;

		/**
		 * Constant for the equals key, "="
		 */
		public final int VK_EQUALS = 0x3D;

		/** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */

		/** Constant for the "A" key. */
		public final int VK_A = GLFW.GLFW_KEY_A;

		/** Constant for the "B" key. */
		public final int VK_B = GLFW.GLFW_KEY_B;

		/** Constant for the "C" key. */
		public final int VK_C = GLFW.GLFW_KEY_C;

		/** Constant for the "D" key. */
		public final int VK_D = GLFW.GLFW_KEY_D;

		/** Constant for the "E" key. */
		public final int VK_E = GLFW.GLFW_KEY_E;

		/** Constant for the "F" key. */
		public final int VK_F = GLFW.GLFW_KEY_F;

		/** Constant for the "G" key. */
		public final int VK_G = GLFW.GLFW_KEY_G;

		/** Constant for the "H" key. */
		public final int VK_H = GLFW.GLFW_KEY_H;

		/** Constant for the "I" key. */
		public final int VK_I = GLFW.GLFW_KEY_I;

		/** Constant for the "J" key. */
		public final int VK_J = GLFW.GLFW_KEY_J;

		/** Constant for the "K" key. */
		public final int VK_K = GLFW.GLFW_KEY_K;

		/** Constant for the "L" key. */
		public final int VK_L = GLFW.GLFW_KEY_L;

		/** Constant for the "M" key. */
		public final int VK_M = GLFW.GLFW_KEY_M;

		/** Constant for the "N" key. */
		public final int VK_N = GLFW.GLFW_KEY_N;

		/** Constant for the "O" key. */
		public final int VK_O = GLFW.GLFW_KEY_O;

		/** Constant for the "P" key. */
		public final int VK_P = GLFW.GLFW_KEY_P;

		/** Constant for the "Q" key. */
		public final int VK_Q = GLFW.GLFW_KEY_Q;

		/** Constant for the "R" key. */
		public final int VK_R = GLFW.GLFW_KEY_R;

		/** Constant for the "S" key. */
		public final int VK_S = GLFW.GLFW_KEY_S;

		/** Constant for the "T" key. */
		public final int VK_T = GLFW.GLFW_KEY_T;

		/** Constant for the "U" key. */
		public final int VK_U = GLFW.GLFW_KEY_U;

		/** Constant for the "V" key. */
		public final int VK_V = GLFW.GLFW_KEY_V;

		/** Constant for the "W" key. */
		public final int VK_W = GLFW.GLFW_KEY_W;

		/** Constant for the "X" key. */
		public final int VK_X = GLFW.GLFW_KEY_X;

		/** Constant for the "Y" key. */
		public final int VK_Y = GLFW.GLFW_KEY_Y;

		/** Constant for the "Z" key. */
		public final int VK_Z = GLFW.GLFW_KEY_Z;

		/**
		 * Constant for the open bracket key, "["
		 */
		public final int VK_OPEN_BRACKET = 0x5B;

		/**
		 * Constant for the back slash key, "\"
		 */
		public final int VK_BACK_SLASH = 0x5C;

		/**
		 * Constant for the close bracket key, "]"
		 */
		public final int VK_CLOSE_BRACKET = 0x5D;

		/** Constant for the number pad "0" key. */
		public final int VK_NUMPAD0 = 0x60;

		/** Constant for the number pad "1" key. */
		public final int VK_NUMPAD1 = 0x61;

		/** Constant for the number pad "2" key. */
		public final int VK_NUMPAD2 = 0x62;

		/** Constant for the number pad "3" key. */
		public final int VK_NUMPAD3 = 0x63;

		/** Constant for the number pad "4" key. */
		public final int VK_NUMPAD4 = 0x64;

		/** Constant for the number pad "5" key. */
		public final int VK_NUMPAD5 = 0x65;

		/** Constant for the number pad "6" key. */
		public final int VK_NUMPAD6 = 0x66;

		/** Constant for the number pad "7" key. */
		public final int VK_NUMPAD7 = 0x67;

		/** Constant for the number pad "8" key. */
		public final int VK_NUMPAD8 = 0x68;

		/** Constant for the number pad "9" key. */
		public final int VK_NUMPAD9 = 0x69;

		/** Constant for the number pad multiply key. */
		public final int VK_MULTIPLY = 0x6A;

		/** Constant for the number pad add key. */
		public final int VK_ADD = 0x6B;

		/**
		 * This constant is obsolete, and is included only for backwards compatibility.
		 * 
		 * @see #VK_SEPARATOR
		 */
		public final int VK_SEPARATER = 0x6C;

		/**
		 * Constant for the Numpad Separator key.
		 * 
		 * @since 1.4
		 */
		public final int VK_SEPARATOR = VK_SEPARATER;

		/** Constant for the number pad subtract key. */
		public final int VK_SUBTRACT = 0x6D;

		/** Constant for the number pad decimal point key. */
		public final int VK_DECIMAL = 0x6E;

		/** Constant for the number pad divide key. */
		public final int VK_DIVIDE = 0x6F;

		/** Constant for the delete key. */
		public final int VK_DELETE = GLFW.GLFW_KEY_DELETE; /* ASCII DEL */

		/** Constant for the NUM_LOCK key. */
		public final int VK_NUM_LOCK = 0x90;

		/** Constant for the SCROLL_LOCK key. */
		public final int VK_SCROLL_LOCK = 0x91;

		/** Constant for the F1 function key. */
		public final int VK_F1 = 0x70;

		/** Constant for the F2 function key. */
		public final int VK_F2 = 0x71;

		/** Constant for the F3 function key. */
		public final int VK_F3 = 0x72;

		/** Constant for the F4 function key. */
		public final int VK_F4 = 0x73;

		/** Constant for the F5 function key. */
		public final int VK_F5 = 0x74;

		/** Constant for the F6 function key. */
		public final int VK_F6 = 0x75;

		/** Constant for the F7 function key. */
		public final int VK_F7 = 0x76;

		/** Constant for the F8 function key. */
		public final int VK_F8 = 0x77;

		/** Constant for the F9 function key. */
		public final int VK_F9 = 0x78;

		/** Constant for the F10 function key. */
		public final int VK_F10 = 0x79;

		/** Constant for the F11 function key. */
		public final int VK_F11 = 0x7A;

		/** Constant for the F12 function key. */
		public final int VK_F12 = 0x7B;

		/**
		 * Constant for the F13 function key.
		 * 
		 * @since 1.2
		 */
		/* F13 - F24 are used on IBM 3270 keyboard; use random range for constants. */
		public final int VK_F13 = 0xF000;

		/**
		 * Constant for the F14 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F14 = 0xF001;

		/**
		 * Constant for the F15 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F15 = 0xF002;

		/**
		 * Constant for the F16 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F16 = 0xF003;

		/**
		 * Constant for the F17 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F17 = 0xF004;

		/**
		 * Constant for the F18 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F18 = 0xF005;

		/**
		 * Constant for the F19 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F19 = 0xF006;

		/**
		 * Constant for the F20 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F20 = 0xF007;

		/**
		 * Constant for the F21 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F21 = 0xF008;

		/**
		 * Constant for the F22 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F22 = 0xF009;

		/**
		 * Constant for the F23 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F23 = 0xF00A;

		/**
		 * Constant for the F24 function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_F24 = 0xF00B;

		/** Constant for the PRINTSCREEN key. */
		public final int VK_PRINTSCREEN = 0x9A;

		/** Constant for the INSERT key. */
		public final int VK_INSERT = 0x9B;

		/** Constant for the HELP key. */
		public final int VK_HELP = 0x9C;

		/** Constant for the META key. */
		public final int VK_META = 0x9D;

		/** Constant for the BACK_QUOTE key. */
		public final int VK_BACK_QUOTE = 0xC0;

		/** Constant for the QUOTE key. */
		public final int VK_QUOTE = 0xDE;

		/**
		 * Constant for the numeric keypad <b>up</b> arrow key.
		 * 
		 * @see #VK_UP
		 * @since 1.2
		 */
		public final int VK_KP_UP = 0xE0;

		/**
		 * Constant for the numeric keypad <b>down</b> arrow key.
		 * 
		 * @see #VK_DOWN
		 * @since 1.2
		 */
		public final int VK_KP_DOWN = 0xE1;

		/**
		 * Constant for the numeric keypad <b>left</b> arrow key.
		 * 
		 * @see #VK_LEFT
		 * @since 1.2
		 */
		public final int VK_KP_LEFT = 0xE2;

		/**
		 * Constant for the numeric keypad <b>right</b> arrow key.
		 * 
		 * @see #VK_RIGHT
		 * @since 1.2
		 */
		public final int VK_KP_RIGHT = 0xE3;

		/* For European keyboards */
		/** @since 1.2 */
		public final int VK_DEAD_GRAVE = 0x80;
		/** @since 1.2 */
		public final int VK_DEAD_ACUTE = 0x81;
		/** @since 1.2 */
		public final int VK_DEAD_CIRCUMFLEX = 0x82;
		/** @since 1.2 */
		public final int VK_DEAD_TILDE = 0x83;
		/** @since 1.2 */
		public final int VK_DEAD_MACRON = 0x84;
		/** @since 1.2 */
		public final int VK_DEAD_BREVE = 0x85;
		/** @since 1.2 */
		public final int VK_DEAD_ABOVEDOT = 0x86;
		/** @since 1.2 */
		public final int VK_DEAD_DIAERESIS = 0x87;
		/** @since 1.2 */
		public final int VK_DEAD_ABOVERING = 0x88;
		/** @since 1.2 */
		public final int VK_DEAD_DOUBLEACUTE = 0x89;
		/** @since 1.2 */
		public final int VK_DEAD_CARON = 0x8a;
		/** @since 1.2 */
		public final int VK_DEAD_CEDILLA = 0x8b;
		/** @since 1.2 */
		public final int VK_DEAD_OGONEK = 0x8c;
		/** @since 1.2 */
		public final int VK_DEAD_IOTA = 0x8d;
		/** @since 1.2 */
		public final int VK_DEAD_VOICED_SOUND = 0x8e;
		/** @since 1.2 */
		public final int VK_DEAD_SEMIVOICED_SOUND = 0x8f;

		/** @since 1.2 */
		public final int VK_AMPERSAND = 0x96;
		/** @since 1.2 */
		public final int VK_ASTERISK = 0x97;
		/** @since 1.2 */
		public final int VK_QUOTEDBL = 0x98;
		/** @since 1.2 */
		public final int VK_LESS = 0x99;

		/** @since 1.2 */
		public final int VK_GREATER = 0xa0;
		/** @since 1.2 */
		public final int VK_BRACELEFT = 0xa1;
		/** @since 1.2 */
		public final int VK_BRACERIGHT = 0xa2;

		/**
		 * Constant for the "@" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_AT = 0x0200;

		/**
		 * Constant for the ":" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_COLON = 0x0201;

		/**
		 * Constant for the "^" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_CIRCUMFLEX = 0x0202;

		/**
		 * Constant for the "$" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_DOLLAR = 0x0203;

		/**
		 * Constant for the Euro currency sign key.
		 * 
		 * @since 1.2
		 */
		public final int VK_EURO_SIGN = 0x0204;

		/**
		 * Constant for the "!" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_EXCLAMATION_MARK = 0x0205;

		/**
		 * Constant for the inverted exclamation mark key.
		 * 
		 * @since 1.2
		 */
		public final int VK_INVERTED_EXCLAMATION_MARK = 0x0206;

		/**
		 * Constant for the "(" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_LEFT_PARENTHESIS = 0x0207;

		/**
		 * Constant for the "#" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_NUMBER_SIGN = 0x0208;

		/**
		 * Constant for the "+" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_PLUS = 0x0209;

		/**
		 * Constant for the ")" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_RIGHT_PARENTHESIS = 0x020A;

		/**
		 * Constant for the "_" key.
		 * 
		 * @since 1.2
		 */
		public final int VK_UNDERSCORE = 0x020B;

		/**
		 * Constant for the Microsoft Windows "Windows" key. It is used for both the
		 * left and right version of the key.
		 * 
		 * @see #getKeyLocation()
		 * @since 1.5
		 */
		public final int VK_WINDOWS = 0x020C;

		/**
		 * Constant for the Microsoft Windows Context Menu key.
		 * 
		 * @since 1.5
		 */
		public final int VK_CONTEXT_MENU = 0x020D;

		/* for input method support on Asian Keyboards */

		/* not clear what this means - listed in Microsoft Windows API */
		/** Constant for the FINAL key. */
		public final int VK_FINAL = 0x0018;

		/** Constant for the Convert function key. */
		/* Japanese PC 106 keyboard, Japanese Solaris keyboard: henkan */
		public final int VK_CONVERT = 0x001C;

		/** Constant for the Don't Convert function key. */
		/* Japanese PC 106 keyboard: muhenkan */
		public final int VK_NONCONVERT = 0x001D;

		/** Constant for the Accept or Commit function key. */
		/* Japanese Solaris keyboard: kakutei */
		public final int VK_ACCEPT = 0x001E;

		/* not clear what this means - listed in Microsoft Windows API */
		/** Constant for the MODECHANGE key. */
		public final int VK_MODECHANGE = 0x001F;

		/*
		 * replaced by VK_KANA_LOCK for Microsoft Windows and Solaris; might still be
		 * used on other platforms
		 */
		/**
		 * Constant for the KANA lock key.
		 * 
		 * @see #VK_KANA_LOCK
		 **/
		public final int VK_KANA = 0x0015;

		/*
		 * replaced by VK_INPUT_METHOD_ON_OFF for Microsoft Windows and Solaris; might
		 * still be used for other platforms
		 */
		/**
		 * Constant for KANJI.
		 * 
		 * @see #VK_INPUT_METHOD_ON_OFF
		 */
		public final int VK_KANJI = 0x0019;

		/**
		 * Constant for the Alphanumeric function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard: eisuu */
		public final int VK_ALPHANUMERIC = 0x00F0;

		/**
		 * Constant for the Katakana function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard: katakana */
		public final int VK_KATAKANA = 0x00F1;

		/**
		 * Constant for the Hiragana function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard: hiragana */
		public final int VK_HIRAGANA = 0x00F2;

		/**
		 * Constant for the Full-Width Characters function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard: zenkaku */
		public final int VK_FULL_WIDTH = 0x00F3;

		/**
		 * Constant for the Half-Width Characters function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard: hankaku */
		public final int VK_HALF_WIDTH = 0x00F4;

		/**
		 * Constant for the Roman Characters function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard: roumaji */
		public final int VK_ROMAN_CHARACTERS = 0x00F5;

		/**
		 * Constant for the All Candidates function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard - VK_CONVERT + ALT: zenkouho */
		public final int VK_ALL_CANDIDATES = 0x0100;

		/**
		 * Constant for the Previous Candidate function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard - VK_CONVERT + SHIFT: maekouho */
		public final int VK_PREVIOUS_CANDIDATE = 0x0101;

		/**
		 * Constant for the Code Input function key.
		 * 
		 * @since 1.2
		 */
		/* Japanese PC 106 keyboard - VK_ALPHANUMERIC + ALT: kanji bangou */
		public final int VK_CODE_INPUT = 0x0102;

		/**
		 * Constant for the Japanese-Katakana function key. This key switches to a
		 * Japanese input method and selects its Katakana input mode.
		 * 
		 * @since 1.2
		 */
		/* Japanese Macintosh keyboard - VK_JAPANESE_HIRAGANA + SHIFT */
		public final int VK_JAPANESE_KATAKANA = 0x0103;

		/**
		 * Constant for the Japanese-Hiragana function key. This key switches to a
		 * Japanese input method and selects its Hiragana input mode.
		 * 
		 * @since 1.2
		 */
		/* Japanese Macintosh keyboard */
		public final int VK_JAPANESE_HIRAGANA = 0x0104;

		/**
		 * Constant for the Japanese-Roman function key. This key switches to a Japanese
		 * input method and selects its Roman-Direct input mode.
		 * 
		 * @since 1.2
		 */
		/* Japanese Macintosh keyboard */
		public final int VK_JAPANESE_ROMAN = 0x0105;

		/**
		 * Constant for the locking Kana function key. This key locks the keyboard into
		 * a Kana layout.
		 * 
		 * @since 1.3
		 */
		/*
		 * Japanese PC 106 keyboard with special Windows driver - eisuu + Control;
		 * Japanese Solaris keyboard: kana
		 */
		public final int VK_KANA_LOCK = 0x0106;

		/**
		 * Constant for the input method on/off key.
		 * 
		 * @since 1.3
		 */
		/* Japanese PC 106 keyboard: kanji. Japanese Solaris keyboard: nihongo */
		public final int VK_INPUT_METHOD_ON_OFF = 0x0107;

		/* for Sun keyboards */
		/** @since 1.2 */
		public final int VK_CUT = 0xFFD1;
		/** @since 1.2 */
		public final int VK_COPY = 0xFFCD;
		/** @since 1.2 */
		public final int VK_PASTE = 0xFFCF;
		/** @since 1.2 */
		public final int VK_UNDO = 0xFFCB;
		/** @since 1.2 */
		public final int VK_AGAIN = 0xFFC9;
		/** @since 1.2 */
		public final int VK_FIND = 0xFFD0;
		/** @since 1.2 */
		public final int VK_PROPS = 0xFFCA;
		/** @since 1.2 */
		public final int VK_STOP = 0xFFC8;

		/**
		 * Constant for the Compose function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_COMPOSE = 0xFF20;

		/**
		 * Constant for the AltGraph function key.
		 * 
		 * @since 1.2
		 */
		public final int VK_ALT_GRAPH = 0xFF7E;

		/**
		 * Constant for the Begin key.
		 * 
		 * @since 1.5
		 */
		public final int VK_BEGIN = 0xFF58;

		/**
		 * This value is used to indicate that the keyCode is unknown. KEY_TYPED events
		 * do not have a keyCode value; this value is used instead.
		 */
		public final int VK_UNDEFINED = 0x0;

		/**
		 * KEY_PRESSED and KEY_RELEASED events which do not map to a valid Unicode
		 * character use this for the keyChar value.
		 */
		public final char CHAR_UNDEFINED = 0xFFFF;

		/**
		 * A constant indicating that the keyLocation is indeterminate or not relevant.
		 * {@code KEY_TYPED} events do not have a keyLocation; this value is used
		 * instead.
		 * 
		 * @since 1.4
		 */
		public final int KEY_LOCATION_UNKNOWN = 0;

		/**
		 * A constant indicating that the key pressed or released is not distinguished
		 * as the left or right version of a key, and did not originate on the numeric
		 * keypad (or did not originate with a virtual key corresponding to the numeric
		 * keypad).
		 * 
		 * @since 1.4
		 */
		public final int KEY_LOCATION_STANDARD = 1;

		/**
		 * A constant indicating that the key pressed or released is in the left key
		 * location (there is more than one possible location for this key). Example:
		 * the left shift key.
		 * 
		 * @since 1.4
		 */
		public final int KEY_LOCATION_LEFT = 2;

		/**
		 * A constant indicating that the key pressed or released is in the right key
		 * location (there is more than one possible location for this key). Example:
		 * the right shift key.
		 * 
		 * @since 1.4
		 */
		public final int KEY_LOCATION_RIGHT = 3;

		/**
		 * A constant indicating that the key event originated on the numeric keypad or
		 * with a virtual key corresponding to the numeric keypad.
		 * 
		 * @since 1.4
		 */
		public final int KEY_LOCATION_NUMPAD = 4;

		private int code;

		private int action;

		private boolean consumed;

	}

	public default Vector2 getLocalMouse() {
		return new Vector2(getX(), getY());
	}

	public default Vector2 getMouse() {
		var vector = new Vector2(getX(), getY());
		// relative to viewport - viewport pos!!!
		OrthographicCamera camera = (OrthographicCamera) GameObject.getRootCamera();
		camera.ToWorld(vector);
		return vector;
	}

}
