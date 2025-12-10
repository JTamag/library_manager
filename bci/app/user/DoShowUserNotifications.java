package bci.app.user;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

  DoShowUserNotifications(LibraryManager receiver) {
    super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    addIntegerField("userId",Prompt.userId());
  }

  @Override
  protected final void execute() throws CommandException {
    int uid = integerField("userId");
    if (_receiver.getUser(uid) == null) {
      throw new NoSuchUserException(uid);
    }
    _display.popup(_receiver.getUserNotifications(uid));
  }
}
