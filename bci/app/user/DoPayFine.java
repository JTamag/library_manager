package bci.app.user;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.UserIsActiveException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

  DoPayFine(LibraryManager receiver) {
    super(Label.PAY_FINE, receiver);
    addIntegerField("user",Prompt.userId());
  }

  @Override
  protected final void execute() throws CommandException {
    int uid = integerField("user");
    if (_receiver.getUser(uid) == null){
      throw new NoSuchUserException(uid);
    }
    try {
      _receiver.payFines(uid);
    } catch(Exception e){
      throw new UserIsActiveException(integerField("user"));
    }
  }
}
