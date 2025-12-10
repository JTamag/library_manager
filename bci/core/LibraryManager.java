package bci.core;

import java.util.*;
import bci.core.User;
import bci.core.exception.*;
import java.io.*;

/**
 * The façade class. Represents the manager of this application. It manages the current
 * library and works as the interface between the core and user interaction layers.
 */
public class LibraryManager {

  /** The object doing all the actual work. */
  /* The current library */
  private Library _library;
  private String _filename;
  
  public LibraryManager() {
    _library = new Library();
    _filename = null;
  }

  /**
   * Saves the serialized application's state into the file associated to the current library
   *
   * @throws FileNotFoundException if for some reason the file cannot be created or opened. 
   * @throws MissingFileAssociationException if the current library does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/
  public void save() throws MissingFileAssociationException, FileNotFoundException, IOException {
    if (!_library.wasModified())
        return;
    if (_filename == null)
        throw new MissingFileAssociationException();
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(_filename))) {
        out.writeObject(_library);
        _library.setModified(false);
    }
  }

  /**
   * Saves the serialized application's state into the specified file. The current library is
   * associated to this file.
   *
   * @param filename the name of the file.
   * @throws FileNotFoundException if for some reason the file cannot be created or opened.
   * @throws MissingFileAssociationException if the current library does not have a file.
   * @throws IOException if there is some error while serializing the state of the network to disk.
   **/
  public void saveAs(String filename) throws FileNotFoundException, MissingFileAssociationException, IOException {
    _filename = filename;
    save();
  }

  /**
   * Loads the previously serialized application's state as set it as the current library.
   *
   * @param filename name of the file containing the serialized application's state
   *        to load.
   * @throws UnavailableFileException if the specified file does not exist or there is
   *         an error while processing this file.
   **/
  public void load(String filename) throws UnavailableFileException {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
        _library = (Library) in.readObject();
        _filename = filename;
    } catch (IOException | ClassNotFoundException e) {
        throw new UnavailableFileException(filename);
    }
  }

  /**
   * Read text input file and initializes the current library (which should be empty)
   * with the domain entities representeed in the import file.
   *
   * @param datafile name of the text input file
   * @throws ImportFileException if some error happens during the processing of the
   * import file.
   **/
  public void importFile(String datafile) throws ImportFileException {
    try {
      if (datafile != null && !datafile.isEmpty())
        _library.importFile(datafile);
    } catch (IOException | UnrecognizedEntryException e) {
      throw new ImportFileException(datafile, e);
    }
  } 

  /**
   * Returns the current date of the library.
   *
   * @return the current date as an integer value
   */
  public int getCurrentDate(){
    return _library.getCurrentDate();
  }

  /**
   * Advances the current date of the library by a given number of days.
   *
   * @param days number of days to advance as an integer
   */
  public void advanceDays(int days){
    _library.advanceDate(days);
  }

  /**
   * Creates a new user and registers it into the library.
   *
   * @param name the name of the user
   * @param email the email of the user
   * @return the ID of the newly created user
   */
  public int createUser(String name, String email) throws InvalidUserException{
    if (name.isEmpty() || email.isEmpty()){
      throw new InvalidUserException();
    }
    return _library.createUser(name, email);
  }

  /**
   * Returns the user with the given ID.
   *
   * @param id the ID of the user to return
   * @return the User with the given ID, or null if no such user exists
   */
  public User getUser(int id){
    return _library.getUser(id);
  }

  /**
   * Returns the current library instance.
   *
   * @return the current Library object
   */
  public Library getLibrary(){
    return _library;
  }

  /**
   * Returns a sorted list of all users in the library.
   *
   * @return a sorted List of User objects
   */
  public List<User> getUsers(){
    List<User> copyList = new ArrayList<>(_library.getUsers());
    Collections.sort(copyList);
    return copyList;
  }

  /**
   * Returns the creation with the given ID.
   *
   * @param id the ID of the creation to return
   * @return the Creation with the given ID, or null if no such creation exists
   */
  public Creation getCreation(int id){
    return _library.getCreation(id);
  }

  /**
   * Returns a sorted list of all creations in the library.
   * The list is sorted by creation ID in ascending order.
   *
   * @return a sorted List of Creation objects
   */
  public List<Creation> getCreations(){
    List<Creation> copyList = new ArrayList<>(_library.getCreations());
    Comparator<Creation> cmp = new Comparator<Creation>() {
      public int compare(Creation c1, Creation c2){
        return Integer.compare(c1.getCreationId(), c2.getCreationId());
      }
    };
    copyList.sort(cmp);
    return copyList;
  }

  /**
   * Returns a sorted list of all creations by a specific creator.
   * The list is sorted by title in alphabetical order.
   * @param creatorName the name of the creator
   * @return a sorted List of Creation objects by the specified creator
   * @throws NoSuchCreatorException if the creator does not exist
   */
  public List<Creation> getCreationsByCreator(String creatorName) throws NoSuchCreatorException{
    Creator creator = _library.getCreator(creatorName);
    if (creator == null){
        throw new NoSuchCreatorException();
    }
    List<Creation> copyList = new ArrayList<>(_library.getCreationsByCreator(creator));
    Comparator<Creation> cmp = new Comparator<Creation>() {
      public int compare(Creation c1, Creation c2){
        return c1.getTitle().compareToIgnoreCase(c2.getTitle());
      }
    };
    copyList.sort(cmp);
    return copyList;
  }

  /**
   * Changes the quantity of a creation in the library.
   *
   * @param id the ID of the creation to change
   * @param quantity the new quantity of the creation
   * @throws NotEnoughCopiesException if there are not enough copies available
   */
  public void changeCreationQuantity(int id, int quantity) throws NotEnoughCopiesException{
    _library.changeCreationQuantity(id, quantity);
  }

  /**
   * Registers a new creator in the library.
   *
   * @param name the name of the creator
   * @return the newly registered Creator object
   */
  public Creator registerCreator(String name){
    return _library.registerCreator(name);
  }

  /**
   * Registers a new DVD in the library.
   *
   * @param title the title of the DVD
   * @param genre the category of the DVD
   * @param price the price of the DVD
   * @param quantity the quantity of the DVD
   * @param igac the IGAC code of the DVD
   * @param director the director of the DVD
   */
  public void registerDvd(String title, Category genre, int price, int quantity, String igac, Creator director){
    _library.registerDvd(title,genre,price,quantity,igac,director);
  }

  /**
   * Registers a new book in the library.
   *
   * @param title the title of the book
   * @param authors the list of authors of the book
   * @param genre the category of the book
   * @param price the price of the book
   * @param quantity the quantity of the book
   * @param isbn the ISBN of the book
   */
  public void registerBook(String title, List<Creator> authors, Category genre, int price, int quantity, String isbn){
    _library.registerBook(title,authors, genre, price, quantity, isbn);
  }

  /**
   * Checks if the library has been modified since the last save.
   *
   * @return true if the library was modified, false otherwise
   */
  public boolean hasLibraryBeenModified(){
    return _library.wasModified();
  }
  
  public String searchResult(String term){
    String result = "";
    List<Creation> creations = getCreations();
    for (Creation c : creations){
      List<Creator> creators = c.getCreators();
      for( Creator cr : creators){
        if (cr.getName().toLowerCase().contains(term.toLowerCase()) || c.getTitle().toLowerCase().contains(term.toLowerCase())){
          result += c.toString() + "\n";
          break;
        }
      }
    }
    return result;
  }

  public Request requestWork(int userId,int creationId) throws RuleViolationException{
    return _library.requestWork(userId,creationId);
  }
  public int returnWork(int userId,int creationId ){
    return _library.returnWork(userId,creationId);
  }
  public void payFines(int userId) throws UserIsActiveException{
    _library.payFines(userId);
  }

  public void notifyUserWhenAvailable(int uid, int cid) {
    _library.notifyUserWhenAvailable(uid, cid);
  }

  public boolean hasRequestedCreation(int uid, int cid) {
    User user = _library.getUser(uid);
    Creation creation = _library.getCreation(cid);
    if (user == null || creation == null) {
      return false;
    }
    return user.hasRequestedCreation(creation);
  }

  public List<Notification> getUserNotifications(int uid) {
    if (_library.getUser(uid) == null) {
      return null;
    }
    return _library.getUser(uid).getNotifications();
  }
}