call mvn clean package -P shared
call mvn install:install-file -Dfile=./target/taskcatbot-ext.jar -DgroupId=com.jalan -DartifactId=handlerext -Dversion=1.1 -Dpackaging=jar -DgeneratePom=true