public class Prize {
      private int number;
      private String statement;

      public Prize(int number, String statement) {
            this.setNumber(number);
            this.setStatement(statement);
      }

      public Prize(int number) {
            this.setNumber(number);
            this.setStatement("");
      }

      public void setNumber(int newNumber) {
            this.number = newNumber;
      }

      public void setStatement(String newStatement) {
            this.statement = newStatement;
      }

      public int getNumber() {
            return this.number;
      }

      public String getStatement() {
            return this.statement;
      }

      public String toString() {
            return this.getNumber() + " " + this.getStatement();
      }
}
