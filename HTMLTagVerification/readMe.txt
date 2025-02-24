1. How to Run The Program:
   
   My file name is Assignment03.java.

compile the file as: javac Assignment03.java

which will then produce .class files Assignment03.class, Stack.class, etc. for all .java files required.

run the program using: java Assignment03 <filename>
where,
 - Assignment03 is the .class file produced after the compilation

 - <filename> is the test HTML file that will be used as the input for the program, put this in the same directory as the Assignment03.class file

Example:
java Assignment03 text1.txt

will use text1.txt file as input to check the HTML tag rule.

2. CSX Server Tested On:
   
   ssh ldawes@csx1.cs.okstate.edu

3. Assumptions Made:
   - The program is being run with all of the .java files in the same directory
   - The program is being run with the argument inputFile in the same directory
   - The program assumes the input is provided via standard input.
   - It has been tested using Java version 17.