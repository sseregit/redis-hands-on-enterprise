echo "Build Project"

./gradlew clean build

echo "Start Server"

cd ./build/libs
java -jar *.jar