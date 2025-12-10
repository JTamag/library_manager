package bci.app.work;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to display a work.
 */
class DoDisplayWork extends Command<LibraryManager> {

  DoDisplayWork(LibraryManager receiver) {
    super(Label.SHOW_WORK, receiver);
    addIntegerField("work", Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
    Object creation = _receiver.getCreation(integerField("work"));
    if (creation == null)
      throw new NoSuchWorkException(integerField("work"));
    _display.popup(creation);
  }
}
