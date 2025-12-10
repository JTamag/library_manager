package bci.app.request;

import bci.core.LibraryManager;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.NoSuchWorkException;
import bci.app.exception.WorkNotBorrowedByUserException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

  DoReturnWork(LibraryManager receiver) {
    super(Label.RETURN_WORK, receiver);
    addIntegerField("userId",bci.app.user.Prompt.userId());
    addIntegerField("creationId",bci.app.work.Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
    int uid = integerField("userId");
    int cid = integerField("creationId");
    if (_receiver.getCreation(cid) == null){
      throw new NoSuchWorkException(cid);
    }
    if (_receiver.getUser(uid) == null){
      throw new NoSuchUserException(uid);
    }
    if (!_receiver.hasRequestedCreation(uid, cid)){
      throw new WorkNotBorrowedByUserException(cid, uid);
    }
    int fines = _receiver.returnWork(uid, cid);
    if (fines == 0){
      return;
    }
    _display.popup(Message.showFine(uid,fines));
    if(Form.confirm(Prompt.finePaymentChoice())){
      try{
        _receiver.payFines(uid);
      } catch(Exception e){
        // should never happen
      }
    }
  }
}
