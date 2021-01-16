javac -d . -cp ./*.jar *.java
java -Djava.ext.dirs=./ io.netty.mickey.http.HTTPServer

