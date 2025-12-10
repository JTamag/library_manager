package bci.app.main;

import bci.core.LibraryManager;
import bci.app.exception.FileOpenFailedException;
import bci.core.exception.UnavailableFileException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

class DoOpenFile extends Command<LibraryManager> {

  DoOpenFile(LibraryManager receiver) {
    super(Label.OPEN_FILE, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    if (_receiver.hasLibraryBeenModified()){
      boolean saveBeforeLoad = Form.confirm(Prompt.saveBeforeExit());
      if (saveBeforeLoad){
        try{
          _receiver.save();
        } catch (Exception e) {
          String filenameSave = Form.requestString(Prompt.newSaveAs());
          try{
            _receiver.saveAs(filenameSave);
          } catch (Exception ex) {
            throw new FileOpenFailedException(ex);
          }
        }
      }
    }
    String filename = Form.requestString(Prompt.openFile());
    try {
      _receiver.load(filename);
    } catch (UnavailableFileException efe) {
    throw new FileOpenFailedException(efe);
    }
  
  }
}
