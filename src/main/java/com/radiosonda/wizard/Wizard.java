package com.radiosonda.wizard;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author manuel
 */
public class Wizard{
    private final List<ContextDialog> dialogs;
    private final HashMap<String, String> results;
    
    public Wizard(List<ContextDialog> dialogs){ 
        this.dialogs = dialogs;
        this.results = new HashMap<>();        
    }
    
    public Optional<HashMap<String, String>> start(){
        for(ContextDialog dialog: dialogs){            
            dialog.setContext(results);  
            Optional<HashMap<String, String>> result = dialog.showAndWait();
            if(result.isPresent()){                
                this.results.putAll(result.get());
            }else{
                return Optional.empty();
            }
        }
        return Optional.of(this.results);
    }
}
