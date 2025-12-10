package bci.app.request;

import bci.core.LibraryManager;
import bci.core.exception.RuleViolationException;
import bci.app.exception.NoSuchUserException;
import bci.app.exception.NoSuchWorkException;
import bci.app.exception.BorrowingRuleFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

  DoRequestWork(LibraryManager receiver) {
    super(Label.REQUEST_WORK, receiver);
    addIntegerField("userId",bci.app.user.Prompt.userId());
    addIntegerField("creationId",bci.app.work.Prompt.workId());
  }

  @Override
  protected final void execute() throws CommandException {
    int uid = integerField("userId");
    int cid = integerField("creationId");

    if (_receiver.getUser(uid) == null) {
      throw new NoSuchUserException(uid);
    }
    if (_receiver.getCreation(cid) == null) {
      throw new NoSuchWorkException(cid);
    }

    try{
      int dueDate = _receiver.requestWork(uid, cid).getDueDate();
      _display.popup(Message.workReturnDay(cid,dueDate));
    }catch(RuleViolationException rve){
      int ruleId = rve.getRuleId();
      if(ruleId != 3){
        throw new BorrowingRuleFailedException(uid,cid,ruleId);
      }
      if(Form.confirm(Prompt.returnNotificationPreference())){
        _receiver.notifyUserWhenAvailable(uid,cid);
      }
    }
  }
    
}
