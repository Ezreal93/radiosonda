package com.radiosonda.new_project_wizard;

import com.radiosonda.wizard.ContextDialog;
import com.radiosonda.wizard.Wizard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author manuel
 */
public class NewProjectWizardFactory {
    
    public static Wizard create() throws IOException {          // Metodo para crear Wizards
        List<ContextDialog> dialogs = new ArrayList<>();        
        dialogs.add(new NewProjectWizardStep1());               // Los WizardStep son creados y agregados a la
        dialogs.add(new NewProjectWizardStep2());               // lista 'dialogs'
        dialogs.add(new NewProjectWizardStep3());
        return new Wizard(dialogs);                             
    }
    
}
