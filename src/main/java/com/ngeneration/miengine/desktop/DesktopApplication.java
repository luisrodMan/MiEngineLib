package com.ngeneration.miengine.desktop;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_DOUBLEBUFFER;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import com.ngeneration.miengine.ApplicationListener;
import com.ngeneration.miengine.Input;
import com.ngeneration.miengine.Input.Keys;
import com.ngeneration.miengine.EngineApplicationConfiguration;
import com.ngeneration.miengine.Engine;
import com.ngeneration.miengine.math.Vector2I;

public class DesktopApplication {

	private long window;
	private int metaModifiers;
	private Vector2I mouseLocation = new Vector2I();
	private int clickCount = 0;
	private EngineApplicationConfiguration config;
	private ApplicationListener listener;

	private DesktopGraphics graphics;
	private DesktopInput input;

	public DesktopApplication(ApplicationListener listener, EngineApplicationConfiguration config) {
		this.config = config;
		this.listener = listener;

		// create env
		Engine.graphics = graphics = new DesktopGraphics(config.getWidth(), config.getHeight());
		Engine.input = input = new DesktopInput();
		// update on resize

		init();

		GL.createCapabilities();

		glViewport(0, 0, config.getWidth(), config.getHeight());
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		glEnable(GL_SCISSOR_TEST);

		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void loop() {

		// Set the clear color

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		glfwSwapInterval(1);

		glClearColor(1, 0.0f, 1.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT); // clear the framebuffer

		listener.create();
		// dont call resize - just when nesessary
		// listener.resize(config.getWidth(), config.getHeight());
//		glfwSwapBuffers(window); // swap the color buffers

		double startTime = glfwGetTime();
		double lastTime = startTime;
		while (!glfwWindowShouldClose(window)) {
			double currentTime = glfwGetTime();
			double delta = currentTime - lastTime;
			graphics.setDelta((float) delta);
			graphics.setTime((float) (currentTime - startTime));
			listener.render();
			input.update();
			glfwSwapBuffers(window); // swap the color buffers
			glfwPollEvents();
			lastTime = currentTime;
		}
		listener.dispose();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_DOUBLEBUFFER, GL_TRUE);// double buffer disabled - use framebuffer instead
		// Create the window
		window = glfwCreateWindow(config.getWidth(), config.getHeight(), config.getTitle(), NULL, NULL);
		if (window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated
		// or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);

		// Enable v-sync
		// By default, the swap interval is zero, meaning buffer swapping will occur
		// immediately.
//		glfwSwapInterval(1); -- 

		// events
		GLFW.glfwSetCursorPosCallback(window, (long window, double mx, double my) -> {
			mouseLocation.set((int) mx, (int) my);
			input.setCursorPosition((int) mx, (int) my);
//			processMouseEvent(mousePressed ? MouseEvent.MOUSE_DRAGGED : MouseEvent.MOUSE_MOVED, MouseEvent.BUTTON1,
//					metaModifiers);
		});
		GLFW.glfwSetMouseButtonCallback(window, (long window, int button, int action, int mods) -> {
//			System.out.println("action: " + action + "   b" + button);
			metaModifiers = mods;

			if (action == Input.Keys.KEY_PRESSED)
//				pressedKeys.add(key);
				input.updateKey(button, true);
			else if (action == Input.Keys.KEY_RELEASED)
				input.updateKey(button, false);
		});

		GLFW.glfwSetKeyCallback(window, new GLFWKeyCallbackI() {
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods) {
				metaModifiers = mods;
				if (action == Input.Keys.KEY_PRESSED)
//					pressedKeys.add(key);
					input.updateKey(key, true);
				else if (action == Input.Keys.KEY_RELEASED)
					input.updateKey(key, false);
//					pressedKeys.remove((Object) key);
//				if (focusedComponent != null) {
//					var event = new KeyEvent(focusedComponent, key, action, mods);
//					focusedComponent.processKeyEvent(event);
////				System.out.println("°key: " + scancode + " " + key);
//				}
			}
		});
		GLFW.glfwSetCharCallback(window, new GLFWCharCallbackI() {
			@Override
			public void invoke(long window, int codepoint) {
//				if (focusedComponent != null)
//					focusedComponent.processKeyEvent(new KeyEvent(focusedComponent, codepoint, KeyEvent.KEY_TYPED, 0));
			}
		});

		// Make the window visible
		glfwShowWindow(window);

	}

}
