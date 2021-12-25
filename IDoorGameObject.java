import javafx.util.*;
import java.util.*;

/**
 * Interface f�r GameObjects mit T�r-Funktionalit�t
 * 
 * @author Lasse Huber-Saffer
 * @version 25.12.2021
 */

public interface IDoorGameObject extends IGameObject
{
    /**
     * Updated das T�robjekt
     * @param deltaTime Deltazeit des vorigen Frames
     * @param runTime Laufzeit des Programms
     * @param cameraPosition Position der Kamera im dreidimensionalen Raum
     */
    public void update(double deltaTime, double runTime, Vector3 cameraPosition);
    
    /**
     * Gibt zur�ck, ob die T�r offen ist
     * @return Offenheit der T�r
     */
    public boolean isOpen();
    
    /**
     * Gibt zur�ck, ob die z-Achse durch die �ffnung dieser T�r hindurchgeht
     * @return Ob die z-Achse durch die �ffnung dieser T�r hindurchgeht
     */
    public boolean isFacingZ();
    
    /**
     * Setzt die Offenheit der T�r
     * @param isOpen Offenheit der T�r
     */
    public void setOpen(boolean isOpen);
    
    /**
     * Gibt die Position der T�r-Tile im Grid zur�ck
     * @return Position der T�r-Tile im Grid
     */
    public Vector2i getTilePosition();
    
    /**
     * Gibt die IDs der verbundenen R�ume der T�r zur�ck
     * @return Paar zweier Integer, die jeweils eine Raum-ID oder null, d.h. kein Raum, sind
     */
    public Pair<Integer, Integer> getConnectedRoomIDs();
    
    /**
     * Setzt die IDs der verbundenen R�ume der T�r
     * @param first erster verbundener Raum, null -> kein erster Raum (Reihenfolge irrelevant)
     * @param second zweiter verbundener Raum, null -> kein zweiter Raum (Reihenfolge irrelevant)
     */
    public void setConnectedRoomIDs(Integer first, Integer second);
}
