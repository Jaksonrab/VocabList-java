import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that manages a double-linked list of vocabulary topics,
 * allowing for operations such as adding, removing, modifying, and searching topics and words.
 */
public class VocabDoubleList {
    private TopicNode startNode;
    private TopicNode endNode;
    private int topicCounter;
    private boolean isFileLoaded;
    /**
     * Constructor to initialize the VocabDoubleList with no topics loaded.
     */
    public VocabDoubleList() {
        startNode = null;
        endNode = null;
        topicCounter = 0;
        isFileLoaded = false;
    }
    /**
     * Inner class representing a node in the double-linked list,
     * containing topic content, and links to previous and next nodes.
     */
    private class TopicNode {
        TopicContent topicContent;
        TopicNode previousNode;
        TopicNode nextNode;
        /**
         * Constructor to initialize the topic node with the given content.
         * @param topicContent The content of the topic.
         */
        public TopicNode(TopicContent topicContent) {
            this.topicContent = topicContent;
            this.previousNode = null;
            this.nextNode = null;
        }
    }
    /**
     * Static inner class representing a node in a singly-linked list of words within a topic.
     */
    private static class WordNode {
        String word;
        WordNode nextWord;
        /**
         * Constructor to initialize the word node with a word.
         * @param word The word to be stored in this node.
         */
        public WordNode(String word) {
            this.word = word;
            this.nextWord = null;
        }
    }
    /**
     * Inner class representing the content of a topic,
     * containing the name of the topic and a linked list of words.
     */
    private class TopicContent {
        String topicName;
        WordNode wordHead;
        /**
         * Constructor to initialize the topic content with a topic name.
         * @param topicName The name of the topic.
         */
        public TopicContent(String topicName) {
            this.topicName = topicName;
            wordHead = null;
        }
        /**
         * Adds a word to the end of the linked list of words for this topic.
         * @param word The word to add.
         */
        public void addWord(String word) {
            WordNode newWordNode = new WordNode(word);
            if (wordHead == null) {
                wordHead = newWordNode;
            } else {
                WordNode temp = wordHead;
                while (temp.nextWord != null) {
                    temp = temp.nextWord;
                }
                temp.nextWord = newWordNode;
            }
        }
        /**
         * Lists all words in this topic to standard output.
         */
        public void listWords() {
            WordNode temp = wordHead;
            int wordIndex = 1; 
            while (temp != null) {
                System.out.println(wordIndex++ + ". " + temp.word); 
                temp = temp.nextWord;
            }
        }
    }
    /**
     * Displays the main menu for the vocabulary management system.
     */
    public void displayMenu() {
        System.out.println("\n-----------------------------");
        System.out.println("Vocabulary Control Center");
        System.out.println("-----------------------------");
        System.out.println("1 browse a topic");
        System.out.println("2 insert a new topic before another one");
        System.out.println("3 insert a new topic after another one");
        System.out.println("4 remove a topic");
        System.out.println("5 modify a topic");
        System.out.println("6 search topics for a word");
        System.out.println("7 load from a file");
        System.out.println("8 show all words starting with a given letter");
        System.out.println("9 save to file");
        System.out.println("0 exit");
        System.out.println("-----------------------------");
        System.out.print("Enter Your Choice: ");
    }
    /**
     * The main method that drives the vocabulary management system.
     */
    public static void main(String[] args) {
        VocabDoubleList vocabManager = new VocabDoubleList();
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        do {
            try {
                vocabManager.displayMenu();
                choice = scanner.nextInt();
                scanner.nextLine();  
                switch (choice) {
                    case 1:
                        vocabManager.browseTopic();
                        break;
                    case 2:
                        vocabManager.addTopicBefore();
                        break;
                    case 3:
                        vocabManager.addTopicAfter();
                        break;
                    case 4:
                        vocabManager.removeTopic();
                        break;
                    case 5:
                        vocabManager.modifyTopic();
                        break;
                    case 6:
                        vocabManager.searchTopicsForWord();
                        break;
                    case 7:
                        vocabManager.loadFromFile();
                        break;
                    case 8:
                        vocabManager.showWordsStartingWith();
                        break;
                    case 9:
                        vocabManager.saveToFile();
                        break;
                    case 0:
                        System.out.println("Exiting the program...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 0 and 9.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();  
            }
        } while (choice != 0);
        scanner.close();
    }
    /**
     * Displays a list of all topics and allows the user to select one to browse.
     * The user can view all words associated with the selected topic.
     */
    //option 1
    public void browseTopic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Browsing topics:");
        displayTopics();
        System.out.print("\nSelect a topic number or 0 to exit: ");
        int choice = scanner.nextInt();

        if (choice == 0) return;
        if (choice < 1 || choice > topicCounter) {
            System.out.println("Invalid topic number!");
            return;
        }

        TopicNode current = startNode;
        for (int i = 1; i < choice; i++) current = current.nextNode;
        System.out.println("Topic: " + current.topicContent.topicName);
        current.topicContent.listWords();
    }
    /**
     * Inserts a new topic before a specified existing topic.
     * Users must specify the position where the new topic should be inserted.
     */
    // option 2
    public void addTopicBefore() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("\nInserting a topic before another:");
        displayTopics();
        System.out.print("\nChoose a topic number or 0 to cancel: ");
        choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 0) return;
        if (choice < 1 || choice > topicCounter) {
            System.out.println("Invalid topic number!");
            return;
        }

        System.out.print("Enter new topic name: ");
        String topicName = scanner.nextLine();
        TopicContent newTopic = new TopicContent(topicName);

        System.out.println("Enter words for the topic (ENTER to finish):");
        String word;
        while (!(word = scanner.nextLine()).isEmpty()) {
            newTopic.addWord(word);
        }

        TopicNode current = startNode;
        for (int i = 1; i < choice; i++) current = current.nextNode;
        TopicNode newNode = new TopicNode(newTopic);
        newNode.nextNode = current;
        newNode.previousNode = current.previousNode;
        if (current.previousNode == null) {
            startNode = newNode;
        } else {
            current.previousNode.nextNode = newNode;
        }
        current.previousNode = newNode;
        topicCounter++;
    }
    /**
     * Inserts a new topic after a specified existing topic.
     * Users must specify the position after which the new topic should be inserted.
     */
   //option 3
    public void addTopicAfter() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("\nInserting a topic after another:");
        displayTopics();
        System.out.print("\nChoose a topic number or 0 to cancel: ");
        choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 0) return;
        if (choice < 1 || choice > topicCounter) {
            System.out.println("Invalid topic number!");
            return;
        }

        System.out.print("Enter new topic name: ");
        String topicName = scanner.nextLine();
        TopicContent newTopic = new TopicContent(topicName);

        System.out.println("Enter words for the topic (ENTER to finish):");
        String word;
        while (!(word = scanner.nextLine()).isEmpty()) {
            newTopic.addWord(word);
        }

        TopicNode current = startNode;
        for (int i = 1; i < choice; i++) current = current.nextNode;
        TopicNode newNode = new TopicNode(newTopic);
        newNode.previousNode = current;
        newNode.nextNode = current.nextNode;
        if (current.nextNode == null) {
            endNode = newNode;
        } else {
            current.nextNode.previousNode = newNode;
        }
        current.nextNode = newNode;
        topicCounter++;
    }
    /**
     * Removes a topic from the list based on the user's selection.
     */
    //option 4
    public void removeTopic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nAvailable topics:");
        displayTopics();
        System.out.print("\nSelect a topic number or 0 to exit: ");
        int choice = scanner.nextInt();

        if (choice == 0) return;
        if (choice < 1 || choice > topicCounter) {
            System.out.println("Invalid topic number!");
            return;
        }

        TopicNode current = startNode;
        for (int i = 1; i < choice; i++) current = current.nextNode;
        if (current.previousNode == null) {
            startNode = current.nextNode;
        } else {
            current.previousNode.nextNode = current.nextNode;
        }
        if (current.nextNode == null) {
            endNode = current.previousNode;
        } else {
            current.nextNode.previousNode = current.previousNode;
        }
        topicCounter--;
        System.out.println("Topic removed successfully.");
    }
    /**
     * Allows the user to modify a topic by adding, removing, or changing words.
     */
    //option 5
    public void modifyTopic() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nModifying a topic:");
        displayTopics();
        System.out.print("\nSelect a topic number or 0 to exit: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  

        if (choice == 0) return;
        if (choice < 1 || choice > topicCounter) {
            System.out.println("Invalid topic number!");
            return;
        }

        TopicNode current = startNode;
        for (int i = 1; i < choice; i++) current = current.nextNode;

        String action;
        boolean exit = false;
        while (!exit) {
            System.out.println("\n-----------------------------");
            System.out.println("    Modify Topics Menu");
            System.out.println("-----------------------------");
            System.out.println("a add a word");
            System.out.println("r remove a word");
            System.out.println("c change a word");
            System.out.println("0 Exit");
            System.out.println("-----------------------------");
            System.out.print("Enter your choice: ");
            action = scanner.nextLine();

            switch (action) {
                case "a":
                    System.out.print("Enter a word to add: ");
                    String wordToAdd = scanner.nextLine();
                    if (isWordPresent(current.topicContent, wordToAdd)) {
                        System.out.println("Sorry, the word: '" + wordToAdd + "' is already listed.");
                    } else {
                        current.topicContent.addWord(wordToAdd);
                        System.out.println("Word added successfully.");
                    }
                    break;
                case "r":
                    System.out.print("Enter a word to remove: ");
                    String wordToRemove = scanner.nextLine();
                    if (removeWord(current.topicContent, wordToRemove)) {
                        System.out.println("Word removed successfully.");
                    } else {
                        System.out.println("Word not found.");
                    }
                    break;
                case "c":
                    System.out.print("Enter the word to change: ");
                    String wordToChange = scanner.nextLine();
                    System.out.print("Enter new word: ");
                    String newWord = scanner.nextLine();
                    if (changeWord(current.topicContent, wordToChange, newWord)) {
                        System.out.println("Word changed successfully.");
                    } else {
                        System.out.println("Original word not found.");
                    }
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
    /**
     * Checks if a specified word is present in the topic's word list.
     * 
     * @param topic The topic content to check within.
     * @param word The word to search for.
     * @return true if the word is present in the topic's word list, false otherwise.
     */
    private boolean isWordPresent(TopicContent topic, String word) {
        WordNode temp = topic.wordHead;
        while (temp != null) {
            if (temp.word.equalsIgnoreCase(word)) {
                return true;
            }
            temp = temp.nextWord;
        }
        return false;
    }
    /**
     * Removes a specified word from the topic's word list.
     * 
     * @param topic The topic content from which the word should be removed.
     * @param word The word to be removed.
     * @return true if the word was successfully removed, false if the word was not found.
     */
    private boolean removeWord(TopicContent topic, String word) {
        WordNode temp = topic.wordHead;
        WordNode prev = null;
        while (temp != null) {
            if (temp.word.equalsIgnoreCase(word)) {
                if (prev == null) {
                    topic.wordHead = temp.nextWord;
                } else {
                    prev.nextWord = temp.nextWord;
                }
                return true;
            }
            prev = temp;
            temp = temp.nextWord;
        }
        return false;
    }
    /**
     * Changes a specified word to a new word within the topic's word list.
     * 
     * @param topic The topic content containing the word to be changed.
     * @param oldWord The word to be replaced.
     * @param newWord The new word that will replace the old word.
     * @return true if the old word was found and successfully replaced, false otherwise.
     */
    private boolean changeWord(TopicContent topic, String oldWord, String newWord) {
        WordNode temp = topic.wordHead;
        while (temp != null) {
            if (temp.word.equalsIgnoreCase(oldWord)) {
                temp.word = newWord;
                return true;
            }
            temp = temp.nextWord;
        }
        return false;
    }
    /**
     * Searches all topics for the specified word.
     */
    //option 6
    public void searchTopicsForWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the word to search for: ");
        String word = scanner.nextLine().toLowerCase();

        boolean found = false;
        TopicNode current = startNode;
        while (current != null) {
            WordNode wordNode = current.topicContent.wordHead;
            while (wordNode != null) {
                if (wordNode.word.equalsIgnoreCase(word)) {
                    System.out.println("Found '" + word + "' in topic: " + current.topicContent.topicName);
                    found = true;
                    break;
                }
                wordNode = wordNode.nextWord;
            }
            current = current.nextNode;
        }

        if (!found) {
            System.out.println("No topics contain the word '" + word + "'.");
        }
    }
    /**
     * Loads topics and their words from a file.
     */
    //option 7
    public void loadFromFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter filename to load from: ");
        String filename = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            TopicContent currentTopic = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("Processing line: " + line);  
                if (line.startsWith("#")) {
                    String topicName = line.substring(1).trim();
                    currentTopic = new TopicContent(topicName);
                    TopicNode newNode = new TopicNode(currentTopic);
                    if (startNode == null) {
                        startNode = newNode;
                        endNode = newNode;
                    } else {
                        endNode.nextNode = newNode;
                        newNode.previousNode = endNode;
                        endNode = newNode;
                    }
                    topicCounter++;
                } else if (!line.trim().isEmpty()) {  
                    currentTopic.addWord(line.trim());
                }
            }
            isFileLoaded = true;
            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public void showWordsStartingWith() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the starting letter: ");
        char letter = scanner.next().toLowerCase().charAt(0);
        scanner.nextLine();  
        ArrayList<String> sortedWords = new ArrayList<>();
        TopicNode current = startNode;
        while (current != null) {
            WordNode wordNode = current.topicContent.wordHead;
            while (wordNode != null) {
                String word = wordNode.word;
                if (!word.isEmpty() && Character.toLowerCase(word.charAt(0)) == letter) {
                    int i = 0;
                    
                    while (i < sortedWords.size() && sortedWords.get(i).compareToIgnoreCase(word) < 0) {
                        i++;
                    }
                    sortedWords.add(i, word);  
                }
                wordNode = wordNode.nextWord;
            }
            current = current.nextNode;
        }

        if (sortedWords.isEmpty()) {
            System.out.println("No words found starting with '" + letter + "'.");
        } else {
            System.out.println("Words starting with '" + letter + "':");
            for (String word : sortedWords) {
                System.out.println(word);
            }
        }
    
    }
    /**
    * Saves all topics and their associated words to a file specified by the user.
    * This method will prompt the user for a filename and write each topic and its words to that file,
    * preceded by a "#" symbol for topics. If no file has been loaded into the system, it will not perform the save
    * and will notify the user accordingly.
    */
    //option 8
    public void saveToFile() {
        if (!isFileLoaded) {
            System.out.println("No file loaded, cannot save.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter filename to save to: ");
        String filename = scanner.nextLine();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            TopicNode current = startNode;
            while (current != null) {
                writer.write("# " + current.topicContent.topicName);
                writer.newLine();
                WordNode wordNode = current.topicContent.wordHead;
                while (wordNode != null) {
                    writer.write(wordNode.word);
                    writer.newLine();
                    wordNode = wordNode.nextWord;
                }
                current = current.nextNode;
            }
            System.out.println("Data saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
    /**
     * Displays a list of all topics currently loaded in the system.
     * Each topic is listed with an index number. If no topics are available, it notifies the user.
     */
    public void displayTopics() {
        if (startNode == null) {
            System.out.println("No topics available.");
            return;
        }
        int index = 1;
        TopicNode current = startNode;
        while (current != null) {
            System.out.println(index++ + ". " + current.topicContent.topicName);
            current = current.nextNode;
        }
    }

}
