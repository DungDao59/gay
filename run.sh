cd src || exit

javac -cp ".:../lib/sqlite-jdbc-3.51.0.0.jar" $(find . -name "*.java")

if [ $? -ne 0 ]; then
    echo " Compiled failed."
    exit 1
fi

java -cp ".:../lib/sqlite-jdbc-3.51.0.0.jar" Main