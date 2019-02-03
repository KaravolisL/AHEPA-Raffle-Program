public class Ticket {
      private String name;
      private int number;
      private int numberDrawn;

      public Ticket(String name, int number) {
            this.name = name;
            this.number = number;
            this.numberDrawn = 0;
      }

      public Ticket() {
            this.name = null;
            this.number = 0;
            this.numberDrawn = 0;
      }

      public int getNumber() {
            return this.number;
      }

      public String getName() {
            return this.name;
      }

      public int getNumberDrawn() {
            return this.numberDrawn;
      }

      public void setNumber(int newNumber) {
            this.number = newNumber;
      }

      public void setName(String newName) {
            this.name = newName;
      }

      public void setNumberDrawn(int newNumberDrawn) {
            this.numberDrawn = newNumberDrawn;
      }

      public String toString() {
            return this.getName();
      }
}
