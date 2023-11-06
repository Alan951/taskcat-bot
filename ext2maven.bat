mvn clean package -P shared
mvn install:install-file -Dfile=./target/taskcatbot-ext.jar -DgroupId=com.jalan -DartifactId=handlerext -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true