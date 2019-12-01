.PHONY = compile run jar runjar zip all clean

#TODO: edit with path to your javac (java compiler)
JC =  /usr/lib/jvm/adoptopenjdk-11-jdk-hotspot/bin/javac 

#TODO: edit with path to your java (java runtime environment)
JRE =  /usr/lib/jvm/adoptopenjdk-11-jdk-hotspot/bin/java 

#TODO: edit with path to your module-path for javafx
MP = --module-path ~/eclipse-workspace/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 

#TODO: edit with your classpath from Eclipse 
CP = -classpath ./:application/*.java:junit-platform-console-standalone-1.5.2.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.base.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.controls.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.fxml.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.graphics.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.media.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.swing.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.web.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx-swt.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/src.zip 

SRC = application/*.java   

APP = application.Main 

ARGS = deb mark sapan  # place your command line args here

compile:
	$(JC) $(CP) $(SRC) 

run:
	$(JRE) $(MP) $(CP) $(APP) $(ARGS)

jar:
	jar -cvmf manifest.txt executable.jar .

runjar:
	$(JRE) $(MP) -jar executable.jar $(ARGS)

# Create zip file for submitting to handin
zip: 
	zip -r ateam.zip .

#Eclipse's "Show Command Line"
all:
	/usr/lib/jvm/adoptopenjdk-11-jdk-hotspot/bin/java --module-path /p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml -Dfile.encoding=UTF-8 -classpath ./:application/*.java:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.base.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.controls.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.fxml.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.graphics.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.media.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.swing.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx.web.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/javafx-swt.jar:/p/course/cs400-deppeler/eclipse-ubuntu/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib/src.zip application.Main deb mark sapan

# Remove generated files
clean:
	rm -f application/*.class
	rm -f executable.jar

