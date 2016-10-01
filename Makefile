all: Main 


Main: State Astar Main.java
	javac Main.java

State: State.java
	javac State.java

Astar: Astar.java
	javac Astar.java

clean:
	$(RM) *.class
