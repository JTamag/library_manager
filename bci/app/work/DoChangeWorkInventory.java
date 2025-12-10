package bci.app.work;

import bci.core.LibraryManager;
import bci.core.exception.NotEnoughCopiesException;
import bci.app.exception.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {
  DoChangeWorkInventory(LibraryManager receiver) {
    super(Label.CHANGE_WORK_INVENTORY, receiver);
    addIntegerField("id", Prompt.workId());
    addIntegerField("qty", Prompt.amountToDecrement());
  }
  @Override
  protected final void execute() throws NoSuchWorkException,CommandException {
    int id = integerField("id");
    int qty = integerField("qty");
    try {
        _receiver.changeCreationQuantity(id, qty);
    } catch (Exception e) {
        _display.popup(Message.notEnoughInventory(id,qty));
    }
  }
}