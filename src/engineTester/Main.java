package engineTester;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWvidmode.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import input.Input;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import levelEngine.LevelManager;

import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GLContext;

import entities.Entity;
import entities.Player;
import entities.Square;
 
public class Main {
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    public static int MONITOR_WIDTH, MONITOR_HEIGHT;
    
    private long window;
    private boolean isFullscreen;
    
    List<Entity> entitiesList = new ArrayList<>();
    LevelManager levelManager;
    
    Player player;
 
    public Main() {
    	this.keyCallback = new Input();
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
    		if (levelManager.getNextEntity() == LevelManager.LEVEL_SQUARE) {
    			Entity e = new Square((float) Math.random() * MONITOR_WIDTH, (float) Math.random() * MONITOR_HEIGHT, 25);
    			e.setxVelocity((float) Math.random() * 2);
    			e.setyVelocity((float) Math.random() * 2);
    			entitiesList.add(e);
    		}
    	}
    	
    	player.update();
    	
    	for(Entity e : entitiesList) {
    		if (e.getXPos() < 0) {
    			e.setxPos(1);
    			e.reflectxVelocity();
    		}
    		if (e.getXPos() > MONITOR_WIDTH) {
    			e.setxPos(MONITOR_WIDTH - 1);
    			e.reflectxVelocity();
    		}
    		if (e.getYPos() < 0) {
    			e.setyPos(1);
    			e.reflectyVelocity();
    		}
    		if (e.getYPos() > MONITOR_HEIGHT) {
    			e.setyPos(MONITOR_HEIGHT - 1);
    			e.reflectyVelocity();
    		}
    		
    		e.update();
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