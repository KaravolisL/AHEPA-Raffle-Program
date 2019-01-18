public class Ticket {
      private String name;
      private int number;

      public Ticket(String name, int number) {
            this.name = name;
            this.number = number;
      }

      public Ticket() {
            this.name = null;
            this.number = 0;
      }

      public int getNumber() {
            return this.number;
      }

      public String getName() {
            return this.name;
      }

      public void setNumber(int newNumber) {
            this.number = newNumber;
      }

      public void setName(String newName) {
            this.name = newName;
      }

}
