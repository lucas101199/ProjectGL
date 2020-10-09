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
            return Integer.valueOf(query1.getFloor()).compareTo(Integer.valueOf(query2.getFloor()));
        else if(_previousDirection == Direction.Down) {
            if(query1.getFloor() < query2.getFloor())
                return 1;
            else if(query1.getFloor() == query2.getFloor())
                return 0;
            else
                return -1;
        }
        else
            assert false : "Erreur never go here !";
        return 0;
    }
}
