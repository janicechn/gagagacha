package persistence;

import org.json.JSONObject;

// Converting objects to JSON
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson(); ////https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
}
