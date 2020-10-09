package CC;

import Operative.InterfaceMaterielle;

import java.util.Comparator;

public class StandartComp implements Comparator<Query> {
    private  CommandControl _cc;
    private Direction _previousDirection;

    public StandartComp(CommandControl cc){
        _cc = cc;
        _previousDirection = _cc.getDirection();
    }


    @Override
    public int compare(Query query1, Query query2) {
        if(_cc.getDirection() != Direction.None)
            _previousDirection = _cc.getDirection();
        else if( _cc.getFloor() == 0)
            _previousDirection = Direction.Up;

        if(_previousDirection == Direction.Up)
            return Integer.compare(query1.getFloor(), query2.getFloor());
        else if(_previousDirection == Direction.Down) {
            return Integer.compare(query2.getFloor(), query1.getFloor());
        }
        else
            assert false : "Erreur never go here !";
        return 0;
    }
}
