import java.util.*;
import java.io.*;

public class JosephusSim {
   private PersonNode circle;     // a PersonNode pointer that tracks first node
   private int size;              // the number of people in the circle
   private int eliminationCount;  // the number to count to for elimination       
   private PersonNode track;      // a PersonNode pointer to help with elimination

   public JosephusSim(String fileName) {
      try {
         // load names from the file in order, generating a singly linked list of PersonNodes
         Scanner file = new Scanner(new File(fileName));
         circle = new PersonNode(file.next());
         PersonNode current = circle;
         size = 1;
         // make the ring circular by attaching last node's next to front
         while(file.hasNextLine()) {
            current.next = new PersonNode(file.next());
            current = current.next;
            size++;
         }
         // remember the last node as the one in front of the next to get eliminated
         current.next = circle;
         
         track = current;
         
         // generate, print, and save the random elimination count
         eliminationCount = (int)(Math.random() * size) + 1;
         System.out.println("=== Elimination count is " + eliminationCount + " ===");

      } catch(FileNotFoundException e) {
         System.out.println("Something went wrong with " + fileName);
      }
   }
   
   
   public void eliminate() {
      // count to the elimination count
      for(int i = 1; i < eliminationCount; i++) {
         track = track.next;
      }

      // print who will be eliminated
      PersonNode eliminated = track.next;
      System.out.println("Eliminated: " + track.next.name);
      
      if(eliminated == circle) {
         circle = circle.next;
      }
      
      track.next = eliminated.next;
      
      // eliminate the person and update "front" of the circle and size
      track = track.next;
      
      size--;
   }
   
   public boolean isOver() {
      // check if there's only one person left in the circle
      return size == 1;
   }
 
      
   public String toString() {
      if(circle == null) {
         return "";
      } 
      
      // if there's only one person left, print them as the last survivor
      
      if(isOver()) {
         return track.name + " is the last survivor!";
      } 
      
      // print the remaining survivors (watch out for infinite loop since list is circular)

      String result = "Remaining survivors: ";
      PersonNode temp = track;
      for(int i = 1; i <= size; i++){
         result += i + "-" + temp.name;
         if(i < size) {
            result += ", ";
         }
         temp = temp.next;
      }
      return result;
   }
   

}