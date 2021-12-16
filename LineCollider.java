/**
 * Linien-Collider zwischen zwei Punkten
 * 
 * @author Lasse Huber-Saffer
 * @version 16.12.2021
 */
public class LineCollider implements ICollider
{
    private PhysicsLayer _layer;
    private Vector2 _pos1;
    private Vector2 _pos2;

    /**
     * Konstruktor f�r Objekte der Klasse LineCollider
     * @param pos1 erster Punkt der Linie
     * @param pos2 zweiter Punkt der Linie
     * @param layer Physik-Ebene auf der dieser Collider agiert
     */
    public LineCollider(Vector2 pos1, Vector2 pos2, PhysicsLayer layer)
    {
        _pos1 = new Vector2(pos1);
        _pos2 = new Vector2(pos2);
        _layer = layer;
    }
    
    /**
     * @see ICollider#intersect()
     */
    public boolean intersects(ICollider other)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    /**
     * @see ICollider#getLayer()
     */
    public PhysicsLayer getLayer()
    {
        return _layer;
    }
    
    /**
     * @see ICollider#setLayer()
     */
    public void setLayer(PhysicsLayer layer)
    {
        _layer = layer;
    }
    
    /**
     * Gibt die Position des ersten Punktes der Linie zur�ck
     * @return Position des ersten Punktes der Linie
     */
    public Vector2 getFirstPoint()
    {
        return new Vector2(_pos1);
    }
    
    /**
     * Gibt die Position des zweiten Punktes der Linie zur�ck
     * @return Position des zweiten Punktes der Linie
     */
    public Vector2 getSecondPoint()
    {
        return new Vector2(_pos2);
    }
    
    /**
     * Setzt die Position des ersten Punktes der Linie
     * @param Position des ersten Punktes der Linie
     */
    public void setFirstPoint(Vector2 pos)
    {
        _pos1 = new Vector2(pos);
    }
    
    /**
     * Setzt die Position des zweiten Punktes der Linie
     * @param Position des zweiten Punktes der Linie
     */
    public void setSecondPoint(Vector2 pos)
    {
        _pos2 = new Vector2(pos);
    }
}