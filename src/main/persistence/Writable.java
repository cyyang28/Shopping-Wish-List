// Referenced code in JsonSerializationDemo-Writable class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemopackage

package persistence;

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
