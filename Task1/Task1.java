import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Task1 {

    public static void main(String[] args) {
        String inputFile = "input.txt"; 
        String outputFile = "output.txt"; 

        int charCount = 0; 
        int wordCount = 0;  
        int lineCount = 0;  

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            int content;
            boolean isWord = false;
            StringBuilder outputData = new StringBuilder();

            while ((content = fis.read()) != -1) {
                char c = (char) content;

              // Цю частину я розробив так щоб воно не рахувало пробіли як символ (для естетичності)
                if (!Character.isWhitespace(c)) {
                    charCount++;
                }

                if (Character.isWhitespace(c)) {
                    if (isWord) {
                        wordCount++;
                        isWord = false;
                    }
                } else {
                    isWord = true;
                }

                if (c == '\n') {
                    lineCount++;
                }
            }

            if (isWord) {
                wordCount++;
            }

            if (lineCount == 0 && charCount > 0) {
                lineCount = 1;
            }

            System.out.println("Кількість символів: " + charCount);
            System.out.println("Кількість слів: " + wordCount);
            System.out.println("Кількість рядків: " + lineCount);

            outputData.append("Кількість символів: ").append(charCount).append("\n");
            outputData.append("Кількість слів: ").append(wordCount).append("\n");
            outputData.append("Кількість рядків: ").append(lineCount).append("\n");

            fos.write(outputData.toString().getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
