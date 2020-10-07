package CC;

import Operative.InterfaceMaterielle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.function.Consumer;

public class CommandControlImpl implements CommandControl{
    private InterfaceMaterielle _elevator;
    private int _floor;
    private HashMap<CCState, ArrayList<Connection>> _stateEventTable;
    private CCState _state;
    private PriorityQueue<Query> _queries;

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
        _state = CCState.IS_STOPPED;

        _elevator.setCommandControl(this);
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.NEW_FLOOR, q->{_floor++;});
        addEntryToStateEventTable(CCState.IS_GOING_UP, Event.USER_REQUEST, q->{_queries.add(q);_floor = q.getFloor();});

        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.NEW_FLOOR, q->{_floor--;});
        addEntryToStateEventTable(CCState.IS_GOING_DOWN, Event.USER_REQUEST, q->{_queries.add(q);});

        addEntryToStateEventTable(CCState.IS_STOPPED, Event.READY_TO_GO, q->{goToNextFloor();});
        addEntryToStateEventTable(CCState.IS_STOPPED, Event.USER_REQUEST, q->{System.out.println("requete dectete !");});

    }

    private void addEntryToStateEventTable(CCState state, Event ev, Consumer<Query> action){
        if(!_stateEventTable.keySet().contains(state))
            _stateEventTable.put(state, new ArrayList<>());
        _stateEventTable.get(state).add(new Connection(ev, action));
    }

    private void goToNextFloor(){

    }

    @Override
    public void handleEvent(Event event) {
        var connectionsToState = _stateEventTable.get(_state);
        for(var c : connectionsToState){
            if(c.event == event)
                c.action.accept(event.userQuery);
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
    public State getState() {
        return null;
    }
}
