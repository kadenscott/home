cd ..
git pull
./gradlew clean build
cd ./app
cp ../build/libs/home.jar ./home.jar
java -jar ./home.jar