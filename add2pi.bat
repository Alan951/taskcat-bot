@ECHO OFF

cmd /C mvn clean package -DskipTests
rem anterior forma en la que se detenia el proceso
rem ssh root@%piaddr% "ps ax | grep 'taskcat-bot' | grep -v grep | awk '{print $1}' | xargs kill"
echo "stopping taskcat-bot service"
ssh root@%piaddr% "service taskcat-bot stop"
scp ./target/taskcat-bot.jar root@%piaddr%:/home/pi/services/taskcatbot/
echo "taskcatbot uploaded..."

scp -r ./plugins root@%piaddr%:/home/pi/services/taskcatbot/
echo "plugins uploaded..."

ssh root@%piaddr% "service taskcat-bot start && service taskcat-bot status"
echo "taskcatbot started"

pause