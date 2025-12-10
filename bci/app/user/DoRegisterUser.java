package bci.app.user;

import bci.core.LibraryManager;
import bci.app.exception.UserRegistrationFailedException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

  DoRegisterUser(LibraryManager receiver) {
    super(Label.REGISTER_USER, receiver);
    addStringField("nome", Prompt.userName());
    addStringField("email", Prompt.userEMail());
  }

  @Override
  protected final void execute() throws UserRegistrationFailedException {
    String nameString = null;
    String emailString = null;
    try{
      nameString = stringField("nome");
      emailString = stringField("email");
      int id = _receiver.createUser(nameString,emailString);
      _display.popup(Message.registrationSuccessful(id));
    }
    catch(Exception e){
      throw new UserRegistrationFailedException(nameString, emailString);
    }
  }
}
