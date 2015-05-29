package engineTester;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWvidmode.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import input.Input;
import input.MouseButtonInput;
import input.MousePosition;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import levelEngine.LevelManager;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.opengl.GLContext;

import audio.Audio;
import entities.Cursor;
import entities.Entity;
import entities.Player;
import entities.Square;
 
public class Main {
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    private GLFWCursorPosCallback cursorPosCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
 
    public static int MONITOR_WIDTH, MONITOR_HEIGHT;
    
    private Entity cursor = new Cursor();
    
    private Audio a;
    
    private long window;
    private boolean isFullscreen;
    
    List<Entity> entitiesList = new ArrayList<>();
    LevelManager levelManager;
    
    Player player;
 
    public Main() {
    	this.keyCallback = new Input();
    	this.cursorPosCallback = new MousePosition();
    	this.mouseButtonCallback = new MouseButtonInput();
    	this.errorCallback = errorCallbackPrint(System.err);
    	
    	glfwSetErrorCallback(errorCallback);

        if (glfwInit() != GL_TRUE)
            throw new IllegalStateException("Unable to initialize GLFW");
        
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        MONITOR_WIDTH = width(vidmode);
        MONITOR_HEIGHT = height(vidmode);
                
        entitiesList = new ArrayList<>();
        levelManager = new LevelManager();
        player = new Player(MONITOR_WIDTH / 2, MONITOR_HEIGHT / 2);
    }
    
    public void run() {
    	System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        try {
            init();
            loop();
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            glfwTerminate();
            errorCallback.release();
        }
    }

    private void init() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        
        window = glfwCreateWindow(MONITOR_WIDTH, MONITOR_HEIGHT, "First Program", glfwGetPrimaryMonitor(), NULL);
        initWindow(window);
        isFullscreen = true;
    }
 
    private void initImmediateMode() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, MONITOR_WIDTH, 0, MONITOR_HEIGHT, 1, -1);
		glMatrixMode(GL_MODELVIEW);
    }
    
    private void loop() {
        GLContext.createFromCurrent();
        initImmediateMode();
        glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0f);
        
        new TimeManager();

        while (glfwWindowShouldClose(window) == GL_FALSE) {
        	update();
        	render();
        }
    }
    
    private void update() {
    	glfwPollEvents();
    	TimeManager.newFrame();
    	levelManager.update();
    	a = new Audio();
    	
    	if (Input.keys[GLFW_KEY_ESCAPE])
    		glfwSetWindowShouldClose(window, GL_TRUE);
    	if (Input.lastEventKey == GLFW_KEY_F11 && Input.lastEventAction == GLFW_PRESS) {
    		toggleFullscreen();
    		Input.resetKeys();
    	}
    	if (Input.lastEventKey == GLFW_KEY_SPACE && Input.lastEventAction == GLFW_PRESS) {
    		swapBackgroundColors();
    		Input.resetKeys();
    	}

    	while (levelManager.hasNext()) {
    		switch (levelManager.getNextEntity()) {
	    		case LevelManager.LEVEL_SQUARE:
	    			Entity e;
	    			boolean isSuitable;
	    			do {
	    				isSuitable = true;
		    			e = new Square((float) Math.random() * MONITOR_WIDTH, (float) Math.random() * MONITOR_HEIGHT, 25);
		    			e.setxVelocity((float) Math.random() * 200);
		    			e.setyVelocity((float) Math.random() * 200);
		    			for (int i = 0; i < entitiesList.size(); i++) {
		    				if (e.isCollidingWith(entitiesList.get(i)))
		    					isSuitable = false;
		    			}
	    			} while(!isSuitable);
		    		entitiesList.add(e);
	    			break;
	    		case LevelManager.SWAP_BACKGROUND:
	    			swapBackgroundColors();
	    			break;
    		}
    	}
    	
    	player.update();
    	
    	for(Entity e : entitiesList) {
    		if (e.getXPos() < 0) {
    			e.setxPos(1);
    			e.reflectxVelocity();
    			a.playboing();
    		}
    		if (e.getXPos() > MONITOR_WIDTH) {
    			e.setxPos(MONITOR_WIDTH - 1);
    			e.reflectxVelocity();
    			a.playboing();
    		}
    		if (e.getYPos() < 0) {
    			e.setyPos(1);
    			e.reflectyVelocity();
    			a.playboing();
    		}
    		if (e.getYPos() > MONITOR_HEIGHT) {
    			e.setyPos(MONITOR_HEIGHT - 1);
    			e.reflectyVelocity();
    			a.playboing();
    		}
    		
    		e.update();
    	}
    	
    	if (MouseButtonInput.lastAction == GLFW_PRESS) {
	    	cursor.setxPos(MousePosition.getX());
	    	cursor.setyPos(MONITOR_HEIGHT - MousePosition.getY());
	    	System.out.println("ding: " + MousePosition.getX() + ", " + MousePosition.getY());
	    	MouseButtonInput.resetMouse();
    	}
    	
    	for (int i = 0; i < entitiesList.size(); i++) {
			Entity outerObject = entitiesList.get(i);
    		for (int j = i + 1; j < entitiesList.size(); j++) {
    			Entity innerObject = entitiesList.get(j);
    			if (outerObject.isCollidingWith(innerObject)) {
    				float x, y;
    				x = outerObject.getXPos();
    				y = outerObject.getYPos();
    				
    				outerObject.setxPos(x - outerObject.getxVelocity() * TimeManager.getDelta());
    				outerObject.setyPos(y - outerObject.getyVelocity() * TimeManager.getDelta());

    				x = innerObject.getXPos();
    				y = innerObject.getYPos();

    				innerObject.setxPos(x - innerObject.getxVelocity() * TimeManager.getDelta());
    				innerObject.setyPos(y - innerObject.getyVelocity() * TimeManager.getDelta());
    				
    				outerObject.reverseVector();
    				innerObject.reverseVector();
    				
    				a.playboing();
    			}
    		}
    		if (outerObject.isCollidingWith(player)) {
    			swapBackgroundColors();
    		}
    		if (cursor.isCollidingWith(outerObject)) {
    			System.out.println("comparing");
    			entitiesList.remove(i);
    			i--;
    			a.playclick();
    		}
    	}
    }
    
    private void render() {
    	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    	player.render();
    	for(Entity e : entitiesList) {
    		e.render();
    	}

    	glfwSwapBuffers(window);
    }
    
    
    
    private void initWindow(long window) {
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        glfwSetKeyCallback(window, keyCallback);
        glfwSetCursorPosCallback(window, cursorPosCallback);
        glfwSetMouseButtonCallback(window, mouseButtonCallback);
 
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
    }
    
    private void toggleFullscreen() {
    	if (isFullscreen) {
    		long tempWindow = glfwCreateWindow(MONITOR_WIDTH-500, MONITOR_HEIGHT-500, "Windowed", NULL, NULL);
    		initWindow(tempWindow);
    		glfwDestroyWindow(window);
    		window = tempWindow;
    	}
    	else {
    		long tempWindow = glfwCreateWindow(MONITOR_WIDTH, MONITOR_HEIGHT, "Fullscreen", glfwGetPrimaryMonitor(), NULL);
    		initWindow(tempWindow);
    		glfwDestroyWindow(window);
    		window = tempWindow;
    	}
        glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0f);
    	isFullscreen = !isFullscreen;
    	initImmediateMode();
    }
    
    private void swapBackgroundColors() {
        glClearColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1.0f);
    }
    
    public static void main(String[] args) {
        new Main().run();
    }
}