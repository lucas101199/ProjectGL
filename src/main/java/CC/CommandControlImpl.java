package CC;

import Operative.InterfaceMaterielle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class CommandControlImpl implements CommandControl{
    private InterfaceMaterielle _elevator;
    private int _floor;
    private HashMap<CCState, ArrayList<Connection>> _stateEventTable;
    private CCState _state;
    private PriorityQueue<Query> _queries;
    private Direction _direction;
    private Query _currentQuery;

    private class Connection{
        public Connection(Event e,Consumer<Query> a){
            event = e;
            action = a;
        }
        public Event event;
        public Consumer<Query> action;
    }

    public CommandControlImpl(InterfaceMaterielle elevator, int initFloor){
        _elevator = elevator;
        _floor = initFloor;
        _stateEventTable = new HashMap<>();
        _queries = new PriorityQueue<>();
        _state = CCState.IS_STOPPED;

        _elevator.setCommandControl(this);

        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.NEW_FLOOR, q->{updateFloorAndStopIfNecessary();});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.USER_REQUEST, q->{_queries.add(q);});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.STOPPED, q->{_state = CCState.IS_STOPPED;});

        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.NEW_FLOOR, q->{updateFloorAndStopIfNecessary();});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.USER_REQUEST, q->{_queries.add(q);});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.STOPPED, q->{_state = CCState.IS_STOPPED;});

        addEntryToStateEventTable(CCState.IS_STOPPED, Event.READY_TO_GO, q->{goToNextFloor();});
        addEntryToStateEventTable(CCState.IS_STOPPED, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_STOPPED, Event.USER_REQUEST, q->{_queries.add(q);});
    /*
        addEntryToStateEventTable(CCState.IS_READY_TO_GO, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_READY_TO_GO, Event.READY_TO_GO, q->{goToNextFloor();});
        addEntryToStateEventTable(CCState.IS_READY_TO_GO, Event.USER_REQUEST, q->{_queries.add(q);});*/
    }

    private void addEntryToStateEventTable(CCState state, Event ev, Consumer<Query> action){
        if(!_stateEventTable.keySet().contains(state))
            _stateEventTable.put(state, new ArrayList<>());
        _stateEventTable.get(state).add(new Connection(ev, action));
    }

    //TODO copy all old element in the new Queue.
    public void setStrategy(Comparator<Query> comp){
        _queries = new PriorityQueue<>(comp);
    }

    private void goToNextFloor(){
        _currentQuery = _queries.poll();
        if(_currentQuery.getFloor() > _floor + 1) {
            _elevator.Up();
            _state = CCState.IS_GOING_UP;
            _direction = Direction.Up;
        }
        else if(_currentQuery.getFloor() < _floor - 1) {
            _elevator.Down();
            _state = CCState.IS_GOING_DOWN;
            _direction = Direction.Down;
        }
        else if(Math.abs(_currentQuery.getFloor() - _floor ) == 1) {
            if(_floor - _currentQuery.getFloor() == -1){
                _direction = Direction.Up;
                _state = CCState.IS_GOING_UP;
                _elevator.Up();
                waitToInitiateUpOrDown();
            }
            else {
                _direction = Direction.Down;
                _state = CCState.IS_GOING_DOWN;
                _elevator.Down();
                waitToInitiateUpOrDown();
            }
            _elevator.stopNextFloor();
        }

    }

    private void waitToInitiateUpOrDown(){
        try{Thread.sleep(200);}catch (Exception e){}
    }

    private void updateFloorAndStopIfNecessary(){
        System.out.println("Stop Next Floor !");

        if(_state == CCState.IS_GOING_UP)
            _floor++;
        else if(_state == CCState.IS_GOING_DOWN)
            _floor--;

        if((Math.abs(_currentQuery.getFloor() - _floor) == 1 && _direction == Direction.Up) ||
                (_currentQuery.getFloor() == _floor && _direction == Direction.Down)){

            _elevator.stopNextFloor();}
    }

    @Override
    public void handleEvent(Event event) {
        if(!_queries.isEmpty())
            _queries.add(_queries.poll());  //Update the priorityQueue.
        var connectionsToState = _stateEventTable.get(_state);
        for(var c : connectionsToState){
            if(c.event == event) {
                System.out.println(event.name());
                c.action.accept(event.userQuery);
            }
        }
    }

    @Override
    public void updateFloor() {

    }

    @Override
    public int getFloor() {
        return _floor;
    }

    @Override
    public CCState getState() {
        return _state;
    }

    @Override
    public Direction getDirection() {
        return _direction;
    }
}
