package CC;

import GUI.GUI_CC;
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
        /**
         *
         * @param e
         * @param a
         */
        public Connection(Event e,Consumer<Query> a){
            event = e;
            action = a;
        }
        public Event event;
        public Consumer<Query> action;
    }

    /**
     *
     * @param elevator
     * @param initFloor
     */
    public CommandControlImpl(InterfaceMaterielle elevator, int initFloor){
        _elevator = elevator;
        _floor = initFloor;
        _stateEventTable = new HashMap<>();
        _queries = new PriorityQueue<>();
        _state = CCState.IS_STOPPED;
        _direction = Direction.None;
        _elevator.setCommandControl(this);

        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.NEW_FLOOR, q->{updateFloorAndStopIfNecessary();});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.USER_REQUEST, q->{_queries.add(q);});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.STOPPED, q->{_state = CCState.IS_STOPPED; _currentQuery = null;});

        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.NEW_FLOOR, q->{updateFloorAndStopIfNecessary();});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.USER_REQUEST, q->{_queries.add(q);});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.STOPPED, q->{_state = CCState.IS_STOPPED; _currentQuery = null;});

        addEntryToStateEventTable(CCState.IS_STOPPED, Event.READY_TO_GO, q->{startOrWait();});
        addEntryToStateEventTable(CCState.IS_STOPPED, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.IS_STOPPED, Event.USER_REQUEST, q->{_queries.add(q);});

        addEntryToStateEventTable(CCState.WAITING, Event.EMMERGENCY_STOP, q->{_state = CCState.IS_BLOCKED;});
        addEntryToStateEventTable(CCState.WAITING, Event.USER_REQUEST, q->{goToNextFloor();});
        addEntryToStateEventTable(CCState.WAITING, Event.USER_REQUEST, q->{_queries.add(q);});
    }

    /**
     *
     * @param state
     * @param ev
     * @param action
     */
    private void addEntryToStateEventTable(CCState state, Event ev, Consumer<Query> action){
        if(!_stateEventTable.keySet().contains(state))
            _stateEventTable.put(state, new ArrayList<>());
        _stateEventTable.get(state).add(new Connection(ev, action));
    }

    //TODO copy all old element in the new Queue.
    /**
     * set the strategy with a specific comparator
     * @param comp
     */
    public void setStrategy(Comparator<Query> comp){
        _queries = new PriorityQueue<>(comp);
    }

    private void goToNextFloor(){
        if(_currentQuery != null)
            _queries.add(_currentQuery);
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
        GUI_CC.displayFloor();
    }

    private void waitToInitiateUpOrDown(){
        try{Thread.sleep(200);}catch (Exception e){}
    }

    private void updateFloorAndStopIfNecessary(){
        System.out.println("Stop Next Floor !");

        if(_direction == Direction.Up)
            _floor++;
        else if(_direction == Direction.Down)
            _floor--;

        if((Math.abs(_currentQuery.getFloor() - _floor) == 1 && _direction == Direction.Up) ||
                (_currentQuery.getFloor() == _floor && _direction == Direction.Down)){

            _elevator.stopNextFloor();}
    }

    /**
     *
     * @param query
     */
    private void addQueryAndAdaptDecision(Query query){
        _queries.add(query);
        goToNextFloor();
    }

    private void startOrWait(){
        System.out.println("Ok !");
            if(!_queries.isEmpty())
                goToNextFloor();
            else
                _state = CCState.WAITING;
    }

    /**
     * Search in the stateEventTable if the event is already created and accept the query of the user
     * @param event
     */
    @Override
    public void handleEvent(Event event) {
        ArrayList<Connection> connectionsToState = _stateEventTable.get(_state);
        for(Connection c : connectionsToState){
            if(c.event == event) {
                System.out.println(event.name());
                c.action.accept(event.userQuery);
            }
        }
    }

    @Override
    public void updateFloor() {

    }

    /**
     * get the actual floor of the elevator
     * @return int floor
     */
    @Override
    public int getFloor() {
        return _floor;
    }

    /**
     * get the state of the command control
     * @return CCState state
     */
    @Override
    public CCState getState() {
        return _state;
    }

    /**
     *
     * @return Direction direction
     */
    @Override
    public Direction getDirection() {
        return _direction;
    }
}
