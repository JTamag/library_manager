package bci.app.work;

import bci.core.LibraryManager;
import pt.tecnico.uilib.menus.Command;

/**
 * Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

  DoPerformSearch(LibraryManager receiver) {
    super(Label.PERFORM_SEARCH, receiver);

    if (!receiver.getCreations().isEmpty()) {
        addStringField("string", Prompt.searchTerm());
    }
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  protected final void execute() {
    if (_receiver.getCreations().isEmpty()){
      return;
    }
    String result = _receiver.searchResult(stringField("string"));
    if(result.isEmpty()){
      return;
    }
    _display.popup(result);
  }
}
