package bci.app.main;

import bci.app.exception.FileOpenFailedException;
import bci.core.LibraryManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;

/**
 * Save to file under current name (if unnamed, query for name).
 */
class DoSaveFile extends Command<LibraryManager> {

  DoSaveFile(LibraryManager receiver) {
    super(Label.SAVE_FILE, receiver);
  }

  @Override
  protected final void execute() throws FileOpenFailedException {
    try {
      _receiver.save();
    } catch (Exception e) {
      String filename = Form.requestString(Prompt.newSaveAs());
      try{
        _receiver.saveAs(filename);
      } catch (Exception ex) {
        throw new FileOpenFailedException(ex);
      }
    }
  }
}
