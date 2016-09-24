package extensao.ufc.br.network;

import java.util.Objects;

/**
 * Created by alan on 11/22/15.
 */
public class Answer {

    public enum Cod{SUCCESS, ERROR, INTERNAL_ERROR};

    private Cod result;
    private String message;
    private Object object;

    public Cod getResult() {
        return result;
    }

    public void setResult(Cod result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
