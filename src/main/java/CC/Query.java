package CC;

import java.util.HashMap;

public class Query {

    private HashMap<String, String> field = new HashMap<>();

    public void addField(String nameField, String instruction){
        field.put(nameField, instruction);
    }

    public String getInstruction(String nameField){
        return field.get(nameField);
    }
}
