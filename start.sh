echo "Checking if Redis is installed..."

REDIS_INSTALLED=$(brew ls --versions redis)
if [ -z "$REDIS_INSTALLED" ]; then
  echo "Redis is not installed. Please install Redis first."
  exit 1
fi

echo "Checking if Redis is install..."

REDIS_STATUS=$(brew services info redis | grep 'Running' | awk '{ print $2 }')

if [ "$REDIS_STATUS" = "false" ]; then
  echo "Starting Redis..."
  brew services start redis
  sleep 3

  # 재확인
  REDIS_STATUS=$(brew services info redis | grep 'Running' | awk '{ print $2 }')
fi

if [ "$REDIS_STATUS" = "false" ]; then
  echo "Redis is not running. Aborting."
  exit 1
fi

echo "Build Project"

./gradlew clean build

echo "Start Server"

cd ./build/libs
java -jar *.jar

# java -jar 에서 나온 종료 코드를 $? 로 받음
APP_EXIT_CODE=$?

# -ne 0 : 종료 코드가 0이 아닌 경우 = 비정상 종료
if [ $APP_EXIT_CODE -ne 0 ]; then
  echo "Application crashed or exited with non-zero status."
  echo "Stopping Redis..."
  brew services stop redis
  exit $APP_EXIT_CODE  # 자바 앱의 종료 코드를 그대로 스크립트도 반환
fi

# 여기까지 왔으면 종료 코드가 0 (정상)인 상태
echo "Application exited normally. Stopping Redis..."
brew services stop redis
exit 0
