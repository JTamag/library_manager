package bci.core;

import java.io.*;
import java.util.*;
import bci.core.LibraryManager;
import bci.core.Rules.*;
import bci.core.Rules.Rule;
import bci.core.exception.*;

/**
 * Class that represents the library as a whole.
 */
public class Library implements Serializable {


  /** Serial number for serialization. */
  @Serial
  private static final long serialVersionUID = 202501101348L;

  private int _nextWorkId;
  private int _nextUserId;
  private boolean _modified;
  private Set<Creator> _creators;
  private Map<Integer,Creation> _creations;
  private Map<Integer,User> _users;
  private List<Rule> _rules;
  private Date _currentDate;
  private List<Request> _requests; 

  public Library(){
    _nextWorkId = 1;
    _nextUserId = 1;
    _modified = false;
    _creators = new HashSet<>();
    _creations = new HashMap<>();
    _users = new HashMap<>();
    _rules = new ArrayList<>();
    setupRules();
    _currentDate = Date.getInstance();
    _requests = new ArrayList<>();
  }

  private void setupRules(){
    _rules.add(new AlreadyRequestedRule());
    _rules.add(new InactiveUserRule());
    _rules.add(new AvailableCreationRule());
    _rules.add(new MaxRequestedRule());
    _rules.add(new CreationTypeRule());
    _rules.add(new CreationPriceRule());
  }

  /**
   * Returns the current date of the library.
   * @return date in days
   */
  public int getCurrentDate(){
    return _currentDate.getCurrentDate();
  }

  /**
   * Advances the current date of the library by a given integer number of days.
   * @param days number of days to advance
   */
  public void advanceDate(int days){
    _currentDate.advanceDate(days);
    _modified = true;
    for(User user : _users.values()){
        user.checkActiveStatus(_currentDate.getCurrentDate());
    }
  }

 // FIX ME APAGUEI O CHECKUSERSTATUS ERA O ERRO DE MUITOS FAZIA ITERACAO EXTRA DO ADDLATE O QUE FAZIA MAIS FALTOSOS CORRIGE MUITOS ERROS
 // SO ADICIONA ERRO NO 11-15

  /**
   * Creates a new user and adds it to the library.
   * @param name Name of the user
   * @param email Email of the user
   * @return ID of the newly created user
   */
  public int createUser(String name, String email){
    User user = new User(_nextUserId, name, email);
    _users.put(_nextUserId, user);
    _nextUserId++;
    _modified = true;
    return _nextUserId-1;
  }

  /**
   * Returns the user with the given ID.
   * @param id ID of the user to return
   * @return User with the given ID (or null if no such user exists)
   */
  public User getUser(int id){
    return _users.get(id);
  }

  /**
   * Returns a list of all users in the library.
   * @return List of all users in the library
   */
  public List<User> getUsers(){
    return new ArrayList<>(_users.values());
  }

  /**
   * Returns the creation with the given ID.
   * @param id ID of the creation to return
   * @return Creation with the given ID (or null if no such creation exists)
   */
  public Creation getCreation(int id){
    return _creations.get(id);
  }

  /**
   * Changes the quantity of a creation in the library.
   * @param id ID of the creation to change
   * @param quantity New quantity of the creation
   * @throws NotEnoughCopiesException
   */
  public void changeCreationQuantity(int id, int quantity) throws NotEnoughCopiesException{
    _modified = true;
    Creation c = _creations.get(id);
    int i = c.changeQuantity(quantity);
    if (i == 0){
      List<Creator> creators = c.getCreators();
      for (Creator cr : creators){
          cr.removeCreation(c);
          boolean hasOtherCreations = false;
          for (Creation creation : _creations.values()){
            if (creation.getCreators().contains(cr) && creation.getCreationId() != c.getCreationId()){
              hasOtherCreations = true;
              break;
            }
          }
          if (!hasOtherCreations){
            _creators.remove(cr);
          }
      }
      _creations.remove(id);
    }
  }

  /**
   * Returns a list of all creations in the library.
   * @return List of all creations in the library
   */
  public List<Creation> getCreations(){
    return new ArrayList<>(_creations.values());
  }

  /**
   * Returns a list of all creations by a given creator.
   * @param creator Creator whose creations to return
   * @return List of all creations by the given creator
   */
  public List<Creation> getCreationsByCreator(Creator creator){
    List<Creation> creationsByCreator = new ArrayList<>();
    for (Creation c : _creations.values()){
        if (c.getCreators().contains(creator)){
            creationsByCreator.add(c);
        }
    }
    return creationsByCreator;
  }
  
  /**
   * Returns the creator with the given name.
   * @param name Name of the creator to return
   * @return Creator with the given name (or null if no such creator exists)
   */
  public Creator getCreator(String name){
    for (Creator c : _creators){
        if (c.getName().equals(name)){
            return c;
        }
    }
    return null;
  }

  /**
   * Registers a creator with the given name.
   * If the creator already exists, returns the existing creator. 
   * @param name Name of the creator to register
   * @return The registered creator
   */
  public Creator registerCreator(String name){
    _modified = true;
    Creator creator = getCreator(name);
    if (creator == null) {
        creator = new Creator(name);
        _creators.add(creator);
    }
    return creator;
  }

  /**
   * Registers a DVD in the library.
   * @param title Title of the DVD
   * @param genre Genre of the DVD
   * @param price Price of the DVD
   * @param quantity Quantity of the DVD
   * @param igac IGAC code of the DVD
   * @param director Director of the DVD
   */
  public void registerDvd(String title, Category genre, int price, int quantity, String igac, Creator director) {
    _modified = true;
    int id = _nextWorkId++;  
    Dvd dvd = new Dvd(id, title, genre, price, quantity, igac, director);
    _creations.put(id, dvd);
  }

  /**
   * Registers a Book in the library.
   * @param title Title of the Book
   * @param authors List of authors of the Book
   * @param genre Genre of the Book
   * @param price Price of the Book
   * @param quantity Quantity of the Book
   * @param isbn ISBN of the Book
   */
  public void registerBook(String title, List<Creator> authors, Category genre, int price, int quantity, String isbn){
    _modified = true;
    int id = _nextWorkId++;
    Book book = new Book(authors, id, title, genre, price, quantity, isbn);
    _creations.put(id, book);
  }


  /**
   * Read text input file at the beginning of the program and populates the
   * the state of this library with the domain entities represented in the text file.
   * 
   * @param filename name of the text input file to process
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   **/
  void importFile(String filename) throws UnrecognizedEntryException, IOException {
    MyParser parser = new MyParser(this);
    parser.parseFile(filename);
  }

  /**
   * Sets the modified flag of the library.
   * @param modified
   */
  public void setModified(boolean modified) {
    _modified = modified;
  }

  /**
   * Returns whether the library has been modified since the last save.
   * @return true if the library has been modified, false otherwise
   */
  public boolean wasModified() {
    return _modified;
  }

  public Request requestWork(int userId,int creationId) throws RuleViolationException{
    User user = _users.get(userId);
    Creation creation = _creations.get(creationId);
    int ruleId = verifyRules(user,creation);
    if (ruleId != 0){
      throw new RuleViolationException(ruleId);
    }

    Request request = new Request(user,creation,_currentDate.getCurrentDate());

    try{
      creation.changeCopiesAvailable(-1);
    }
    catch(NotEnoughCopiesException e){
      throw new RuleViolationException(3);
    }

    _requests.add(request);
    user.addRequest(request);

    creation.notifyRequestObservers(NotificationType.REQUEST);

    return request;
  }

  // returns 0 if passed all
  private int verifyRules(User user, Creation creation){
    for (Rule r : _rules){
      if (!r.check(creation, user)){
        return r.getId();
      }
    }
    return 0;
  }

  public int returnWork(int userId,int creationId){
    User user = _users.get(userId);
    Creation creation = _creations.get(creationId);

    Request request = user.removeRequest(creation); // o try deve ser neste bloco 
    boolean wasInTime = _currentDate.getCurrentDate() <= request.getDueDate();
    if (wasInTime){
      user.addSuccessiveOnTimeReturn();
    } else {
      user.addSuccessiveLateReturn();
    }
    _requests.remove(request);
    try{
      creation.changeCopiesAvailable(1);
    }
    catch(NotEnoughCopiesException e){
      // should never happen
    }

    int fine = request.calculateFine(_currentDate.getCurrentDate());
    if(fine > 0){
      user.addFine(fine);
      user.deactivate();
    }
    user.checkActiveStatus(_currentDate.getCurrentDate());
    return user.getFines();
  }

  public void payFines(int userId) throws UserIsActiveException{
    User user = _users.get(userId);
    user.payFines(_currentDate.getCurrentDate());
  }

  public void notifyUserWhenAvailable(int uid, int cid) {
    User user = _users.get(uid);
    Creation creation = _creations.get(cid);
    creation.addAvailabilityObserver((Observer)user);
  }
}