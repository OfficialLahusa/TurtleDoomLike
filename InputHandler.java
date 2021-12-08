import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import java.awt.*;

/**
 * Die Klasse InputHandler verwaltet den Tastatureingaben zur Steuerung des Spiels.
 * @author Sven Schreiber, Lasse Huber-Saffer
 * @version 08.12.2021
 */
public class InputHandler
{
    // Ein Array das die Zust�nde der Tasten speichert (Gedr�ckt = true, nicht gedr�ckt = false)
    private static boolean[] _keys;
    
    private boolean _keepMouseInPlace = false;
    private Vector2 _lastGlobalMousePos;
    private Vector2 _lastLocalMousePos;
    private Vector2 _mouseResetAnchorPos;
    private Vector2 _mouseDelta;
    
    /**
     * Konstruktor f�r Objekte der Klasse InputHandler
     */
    public InputHandler()
    {       
        _keys = new boolean[KeyCode.values().length];
        _lastGlobalMousePos = null;
        _lastLocalMousePos = null;
        _mouseResetAnchorPos = null;
        _mouseDelta = new Vector2();
        
        // Initialize Event Listeners
        initKeyListener();
        initMouseListener();
    }
    
    /**
     * Gibt zur�ck ob eine bestimmte Taste gedr�ckt ist.
     * @param key Eine bestimmte Taste
     * @return Liefert true, falls die Taste gedr�ckt ist.
     */
    public boolean isKeyPressed(KeyCode key)
    {
        return _keys[key.ordinal()];
    }
    
    /**
     * Gibt die Position des Mauszeigers relativ zum Fenster zur�ck
     * @return die Position des Mauszeigers relativ zum Fenster
     */
    public Vector2 getLocalMousePos()
    {
        return new Vector2(_lastLocalMousePos);
    }
    
    /**
     * Gibt die Position des Mauszeigers relativ zum Bildschirm zur�ck
     * @return die Position des Mauszeigers relativ zum Bildschirm
     */
    public Vector2 getGlobalMousePos()
    {
        return new Vector2(_lastGlobalMousePos);
    }
    
    /**
     * Gibt die relative Mausposition seit dem letzten Zur�cksetzen zur�ck
     * @return relative Mausposition seit dem letzten Zur�cksetzen
     */
    public Vector2 getMouseDelta()
    {
        return new Vector2(_mouseDelta);
    }
    
    /**
     * Gibt die relative Mausposition seit dem letzten Zur�cksetzen zur�ck, danach wird sie zur�ckgesetzt.
     * @return relative Mausposition seit dem letzten Zur�cksetzen
     */
    public Vector2 getAndResetMouseDelta()
    {
        Vector2 result = new Vector2(_mouseDelta);
        _mouseDelta = new Vector2();
        return result;
    }
    
    /**
     * Gibt zur�ck, ob die Maus aktuell bei Bewegung zur vorherigen Position zur�ckgesetzt wird
     * @return true, wenn die Maus aktuell bei Bewegung zur vorherigen Position zur�ckgesetzt wird, sonst false
     */
    public boolean getKeepMouseInPlace()
    {
        return _keepMouseInPlace;
    }
    
    /**
     * Setzt den Zustand, ob die Maus bei Bewegung zur vorherigen Position zur�ckgesetzt werden soll
     * @param keepMouseInPlace ob die Maus bei Bewegung zur vorherigen Position zur�ckgesetzt werden soll
     */
    public void setKeepMouseInPlace(boolean keepMouseInPlace)
    {
        if(!keepMouseInPlace)
        {
            _mouseResetAnchorPos = null;
        }
        
        _keepMouseInPlace = keepMouseInPlace;
    }
    
    /**
     * Initialisiert den KeyListener
     */
    private void initKeyListener()
    {
        AWTEventListener listener = new AWTEventListener() 
        {
            @Override
            public void eventDispatched(AWTEvent event)
            {
                try
                {
                    KeyEvent evt = (KeyEvent)event;
                    if (evt.getID() == KeyEvent.KEY_PRESSED)
                    {
                        switch (evt.getKeyCode())
                        {
                            case KeyEvent.VK_W:
                                _keys[KeyCode.KEY_W.ordinal()] = true;
                                break;
                            case KeyEvent.VK_A:
                                _keys[KeyCode.KEY_A.ordinal()] = true;
                                break;
                            case KeyEvent.VK_S:
                                _keys[KeyCode.KEY_S.ordinal()] = true;
                                break;
                            case KeyEvent.VK_D:
                                _keys[KeyCode.KEY_D.ordinal()] = true;
                                break;
                            case KeyEvent.VK_SPACE:
                                _keys[KeyCode.KEY_SPACE.ordinal()] = true;
                                break;
                            case KeyEvent.VK_ESCAPE:
                                _keys[KeyCode.KEY_ESCAPE.ordinal()] = true;
                                break;
                            case KeyEvent.VK_PLUS:
                                _keys[KeyCode.KEY_PLUS.ordinal()] = true;
                                break;
                            case KeyEvent.VK_MINUS:
                                _keys[KeyCode.KEY_MINUS.ordinal()] = true;
                                break;
                        }
                    }
                    else if (evt.getID() == KeyEvent.KEY_RELEASED)
                    {
                        switch (evt.getKeyCode())
                        {
                            case KeyEvent.VK_W:
                                _keys[KeyCode.KEY_W.ordinal()] = false;
                                break;
                            case KeyEvent.VK_A:
                                _keys[KeyCode.KEY_A.ordinal()] = false;
                                break;
                            case KeyEvent.VK_S:
                                _keys[KeyCode.KEY_S.ordinal()] = false;
                                break;
                            case KeyEvent.VK_D:
                                _keys[KeyCode.KEY_D.ordinal()] = false;
                                break;
                            case KeyEvent.VK_SPACE:
                                _keys[KeyCode.KEY_SPACE.ordinal()] = false;
                                break;
                            case KeyEvent.VK_ESCAPE:
                                _keys[KeyCode.KEY_ESCAPE.ordinal()] = false;
                                break;
                            case KeyEvent.VK_PLUS:
                                _keys[KeyCode.KEY_PLUS.ordinal()] = false;
                                break;
                            case KeyEvent.VK_MINUS:
                                _keys[KeyCode.KEY_MINUS.ordinal()] = false;
                                break;
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.KEY_EVENT_MASK);
    }
    
    /**
     * Initialisiert den MouseListener
     */
    private void initMouseListener()
    {
        AWTEventListener listener = new AWTEventListener() 
        {
            @Override
            public void eventDispatched(AWTEvent event)
            {
                try
                {
                    MouseEvent evt = (MouseEvent)event;
                    
                    if(evt.getID() == MouseEvent.MOUSE_MOVED || evt.getID() == MouseEvent.MOUSE_DRAGGED)
                    {
                        Point mousePos = evt.getLocationOnScreen();
                        Point localMousePos = evt.getPoint();
                        
                        _lastGlobalMousePos = new Vector2(mousePos.getX(), mousePos.getY());
                        _lastLocalMousePos = new Vector2(localMousePos.getX(), localMousePos.getY());
                        
                        if(_mouseResetAnchorPos != null)
                        {
                            _mouseDelta = _mouseDelta.add(new Vector2(mousePos.getX() - _mouseResetAnchorPos.getX(), mousePos.getY() - _mouseResetAnchorPos.getY()));
                        }
                        
                        if(_keepMouseInPlace)
                        {
                            if(_mouseResetAnchorPos == null)
                            {
                                _mouseResetAnchorPos = new Vector2(mousePos.getX(), mousePos.getY());
                            }
                            else
                            {
                                try
                                {
                                    Robot bot = new Robot();
                                    bot.mouseMove((int)_mouseResetAnchorPos.getX(), (int)_mouseResetAnchorPos.getY());
                                }
                                catch (AWTException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        
        Toolkit.getDefaultToolkit().addAWTEventListener(listener, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }
}
