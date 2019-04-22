package com.radiosonda.wizard;

import java.util.HashMap;
import javafx.scene.control.Dialog;

/**
 * @author Kindred
 */
public class ContextDialog extends Dialog<HashMap<String, String>> {
    protected HashMap<String, String> result;
  
    public void setContext(HashMap<String, String> result){
        this.result = result;        
    }
}
