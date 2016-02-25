all:
	cd RunwayRedeclaration; \
	javac -cp lib/javafx.jar:. src/application/*.java src/application/model/*.java src/application/view/*.java

run:
	cd RunwayRedeclaration/src; \
	java -cp lib/javafx.jar:. application.Main

clean:
	cd RunwayRedeclaration; \
	rm src/application/*.class src/application/model/*.class src/application/view/*.class
