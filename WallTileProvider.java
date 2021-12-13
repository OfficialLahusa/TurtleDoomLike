import java.util.*;

/**
 * Ein TileProvider, der Mauern und optional S�ulen in Abh�ngigkeit von der Umgebung platziert
 * 
 * @author Lasse Huber-Saffer
 * @version 04.12.2021
 */
public class WallTileProvider implements ITileProvider
{
    private Mesh _wallMesh;
    private String _wallColor;
    private Mesh _pillarMesh;
    private String _pillarColor;
    
    /**
     * Konstruktor f�r Objekte der Klasse WallTileProvider
     * @param wallMesh Mesh, das f�r die Vorlage verwendet werden soll (Eine Seite auf der X-Achse, der Mittelpunkt auf der Y-Achse)
     * @param wallColor Farbe, die die Vorlage haben soll
     */
    public WallTileProvider(Mesh wallMesh, String wallColor)
    {
        this(wallMesh, wallColor, null, null);
    }
    
    /**
     * Konstruktor f�r Objekte der Klasse WallTileProvider
     * @param wallMesh Mesh, das f�r die Vorlage verwendet werden soll (Eine Seite auf der X-Achse, der Mittelpunkt auf der Y-Achse)
     * @param wallColor Farbe, die die Vorlage haben soll
     * @param pillarMesh Mesh, das f�r die Vorlage verwendet werden soll (Liegt auf der Y-Achse), darf null sein
     * @param pillarColor Farbe, die die Vorlage haben soll, darf null sein
     */
    public WallTileProvider(Mesh wallMesh, String wallColor, Mesh pillarMesh, String pillarColor)
    {
        _wallMesh = wallMesh;
        _wallColor = wallColor;
        _pillarMesh = pillarMesh;
        _pillarColor = pillarColor;
    }
        
    /**
     * Gibt die GameObjects zur�ck, die der TileProvider in der gegebenen Umgebung generiert
     * @param env Umgebung der Tile
     * @param x x-Position der Tile
     * @param y y-Position der Tile
     * @param mirrorZAxis gibt an, ob z-Achse der generierten Objekte gespiegelt sein soll
     * @return Liste an GameObjects, die von der Tile platziert werden
     */
    public ArrayList<IGameObject> getTileObjects(TileEnvironment env, int x, int z, double tileWidth, boolean mirrorZAxis)
    {
        // Environment muss bei diesem Typ != null sein (siehe ITileProvider.requiresEnvironment())
        if(env == null)
        {
            throw new IllegalArgumentException("TileEnvironment was null when providing wall tiles");
        }
        
        // R�ckgabe-ArrayList
        ArrayList<IGameObject> result = new ArrayList<IGameObject>();
        double mirrorFactor = mirrorZAxis ? -1 : 1;
        
        // W�nde
        // Wand auf +X-Seite
        if(!Tile.isSolidOrNone(env.px))
        {
            result.add(new StaticGameObject(getWallMesh(), getWallColor(), new Vector3((x + 1.0) * tileWidth, 0.0, mirrorFactor * (z + 0.5) * tileWidth), new Vector3(0.0, 90.0, 0.0), new Vector3(1.0, 1.0, 1.0)));
        }
        // Wand auf +Z-Seite
        if(!Tile.isSolidOrNone(env.pz))
        {
            result.add(new StaticGameObject(getWallMesh(), getWallColor(), new Vector3((x + 0.5) * tileWidth, 0.0, mirrorFactor * (z + 1.0) * tileWidth)));
        }
        // Wand auf -X-Seite
        if(!Tile.isSolidOrNone(env.nx))
        {
            result.add(new StaticGameObject(getWallMesh(), getWallColor(), new Vector3((x      ) * tileWidth, 0.0, mirrorFactor * (z + 0.5) * tileWidth), new Vector3(0.0, 90.0, 0.0), new Vector3(1.0, 1.0, 1.0)));
        }
        // Wand auf -Z-Seite
        if(!Tile.isSolidOrNone(env.nz))
        {
            result.add(new StaticGameObject(getWallMesh(), getWallColor(), new Vector3((x + 0.5) * tileWidth, 0.0, mirrorFactor * (z      ) * tileWidth)));
        }
        
        // S�ulen
        // S�ule an Ecke -X-Z
        if(!Tile.isSolidOrNone(env.nxnz) && !Tile.isSolidOrNone(env.nx) && !Tile.isSolidOrNone(env.nz))
        {
            result.add(new StaticGameObject(getPillarMesh(), getPillarColor(), new Vector3(x * tileWidth, 0.0, mirrorFactor * z * tileWidth)));
        }
        // S�ule an Ecke +X-Z
        if(!Tile.isSolidOrNone(env.pxnz) && !Tile.isSolidOrNone(env.px) && !Tile.isSolidOrNone(env.nz))
        {
            result.add(new StaticGameObject(getPillarMesh(), getPillarColor(), new Vector3((x + 1.0) * tileWidth, 0.0, mirrorFactor * z * tileWidth)));
        }
        // S�ule an Ecke +X+Z
        if(!Tile.isSolidOrNone(env.pxpz) && !Tile.isSolidOrNone(env.px) && !Tile.isSolidOrNone(env.pz))
        {
            result.add(new StaticGameObject(getPillarMesh(), getPillarColor(), new Vector3((x + 1.0) * tileWidth, 0.0, mirrorFactor * (z + 1.0) * tileWidth)));
        }
        // S�ule an Ecke -X+Z
        if(!Tile.isSolidOrNone(env.nxpz) && !Tile.isSolidOrNone(env.nx) && !Tile.isSolidOrNone(env.pz))
        {
            result.add(new StaticGameObject(getPillarMesh(), getPillarColor(), new Vector3(x * tileWidth, 0.0, mirrorFactor * (z + 1.0) * tileWidth)));
        }
        
        return result;
    }
    
    /**
     * Gibt zur�ck, ob der TileProvider ein TileEnvironment als Parameter der Funktion getStaticTileObject bekommen soll, oder nicht, da nicht jeder TileProvider-Typ diesen Parametertyp ben�tigt
     * @return Wahrheitswert der Aussage "Dieser TileProvider ben�tigt als Parameter ein TileEnvironment ungleich null"
     */
    public boolean requiresEnvironment()
    {
        return true;
    }
    
    /**
     * Gibt eine neue Instanz des Wandmeshs der Vorlage zur�ck
     * @return neue Instanz des Wandmeshs der Vorlage
     */
    public Mesh getWallMesh()
    {
        return new Mesh(_wallMesh);
    }
    
    /**
     * Gibt eine neue Instanz des S�ulenmeshs der Vorlage zur�ck
     * @return neue Instanz des S�ulenmeshs der Vorlage
     */
    public Mesh getPillarMesh()
    {
        return new Mesh(_pillarMesh);
    }
    
    /**
     * Gibt die Farbe der Vorlage zur�ck
     * @return Farbe der Vorlage
     */
    public String getWallColor()
    {
        return _wallColor;
    }
    
    /**
     * Gibt die Farbe der Vorlage zur�ck
     * @return Farbe der Vorlage
     */
    public String getPillarColor()
    {
        return _pillarColor;
    }
}
