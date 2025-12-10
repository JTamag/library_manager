package bci.app.user;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

  DoShowUser(LibraryManager receiver) {
    super(Label.SHOW_USER, receiver);
    addIntegerField("user", Prompt.userId());
  }

  @Override
  protected final void execute() throws CommandException {
    int userId = integerField("user");
    var user = _receiver.getUser(userId);
    if(user == null){
      throw new NoSuchUserException(userId); 
    }
    _display.popup(user);
  }
}
