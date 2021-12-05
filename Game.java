import java.util.*;
import javafx.util.*;

/**
 * Diese Klasse enth�lt die Kernlogik des Spiels und den Gameloop
 * 
 * @author Lasse Huber-Saffer, Sven Schreiber, Nico H�dicke
 * @version 01.12.2021
 */
public class Game
{
    private Renderer _renderer;
    private TimeManager _timeManager;
    private InputManager _inputManager;
    private TextRenderer _textRenderer;
    private SoundRegistry _soundRegistry;
    private WavefrontObjectLoader _objLoader;
    private MapHandler _mapHandler;
    private Camera _camera;
    
    // FPS-Berechnung
    private double _fps;
    private double fpsTimer = 0.0; 
    private TimeManager _frameCapTimeManager;
    public static final double STATIC_FPS_CAP = 60.0;
    // Wenn aktiv: FPS werden auf den Wert von STATIC_FPS_CAP begrenzt.
    public static final boolean CAP_FRAMERATE = true;
    // Wenn aktiv: Framezeit wird um DYNAMIC_FPS_FACTOR * frametime erh�ht, um den fertigen Frame l�nger anzuzeigen
    public static final boolean DYNAMIC_FPS_CAPPING = true;
    public static final double DYNAMIC_FPS_FACTOR = 1.0;

    /**
     * Konstruktor f�r Objekte der Klasse Game
     */
    public Game()
    {
        _renderer = new Renderer();
        _timeManager = new TimeManager();
        _inputManager = new InputManager();
        _textRenderer = new TextRenderer(_renderer);
        _soundRegistry = new SoundRegistry();
        _objLoader = new WavefrontObjectLoader();
        _mapHandler = new MapHandler();
        _camera = new Camera(new Vector3(0.0, 2.0, 10.0), 1.0, 90.0);
        
        _frameCapTimeManager = new TimeManager();

        _mapHandler.load("TestMap");
        _camera.setPosition(_mapHandler.getMap().getPlayerSpawn());
    }
    
    /**
     * Startmethode des Spiels
     */
    public void start()
    {
        _soundRegistry.loadSound("test", "./res/sounds/to_the_front.mp3");
        _soundRegistry.sounds.get("test").setVolume(0.2);
        _soundRegistry.sounds.get("test").play();
        
        runGameLoop();
    }
    
    public void runGameLoop()
    {
        double runTime = 0.0, deltaTime = 0.0;
        
        // Gameloop
        while(true)
        {
            // Zeiten berechnen
            runTime = _timeManager.getRunTime();
            deltaTime = _timeManager.getDeltaTime();
            
            // reset frameCap Timer
            _frameCapTimeManager.getDeltaTime();
            
            // Input-Handling
            if(_inputManager.isKeyPressed(KeyCode.KEY_W))
            {
                _camera.move(_camera.getDirection().multiply(-5.0 * deltaTime));
            }
            if(_inputManager.isKeyPressed(KeyCode.KEY_S))
            {
                _camera.move(_camera.getDirection().multiply(5.0 * deltaTime));
            }
            if(_inputManager.isKeyPressed(KeyCode.KEY_A))
            {
                _camera.rotateYaw(-120.0 * deltaTime);
            }
            if(_inputManager.isKeyPressed(KeyCode.KEY_D))
            {
                _camera.rotateYaw(120.0 * deltaTime);
            }
            
            // Updated Map-LODs
            _mapHandler.getMap().updateLOD(_camera.getPosition());
            
            // Cleart das Bild
            _renderer.clear();
            
            // Rendering
            _mapHandler.getMap().draw(_renderer, _camera);
            
            // X-, Y- und Z-Achse zeichnen
            _renderer.drawAxis(_camera);
            
            // Zeichnet den FPS-Z�hler
            fpsTimer += deltaTime;
            if(fpsTimer > 1.0)
            {
                fpsTimer = 0;
                _fps = 1.0 / deltaTime;
                
            }
            _textRenderer.write(new Vector2(10,10), 5, "fps: " + (int)Math.round(_fps));
            
            // Bildrate auf maximal FPS_CAP (Konstante) begrenzen
            double currentFrameTime = _frameCapTimeManager.getDeltaTime();
            if(CAP_FRAMERATE)
            {
                try
                {
                    if(DYNAMIC_FPS_CAPPING)
                    {
                        Thread.sleep((long)(1000.0 * DYNAMIC_FPS_FACTOR * currentFrameTime));
                    }
                    else
                    {
                        double diff = (1.0 / STATIC_FPS_CAP) - currentFrameTime;
                        if(diff > 0.0)
                        {
                            Thread.sleep((long)(1000.0 * diff));
                        }
                    }

                }
                catch (InterruptedException ie)
                {
                    throw new RuntimeException("unhandled interrupt");
                }
            }
        }
    }
}
