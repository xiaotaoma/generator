发布jar包命令
mvn deploy:deploy-file -DgroupId=com.zkzn.common -DartifactId=common -Dversion=1.41 -Dpackaging=jar -Dfile=common.jar -Durl=http://192.168.0.33:8081/repository/3rd_part/ -DrepositoryId=nexus
mvn deploy:deploy-file -DgroupId=com.zkzn.generator -DartifactId=generator -Dversion=1.22 -Dpackaging=jar -Dfile=target\generator.jar -Durl=http://192.168.0.33:8081/repository/3rd_part/ -DrepositoryId=nexus
mvn deploy:deploy-file -DgroupId=com.zkzn.generator -DartifactId=generator -Dversion=1.30 -Dpackaging=jar -Dfile=target\generator.jar -Durl=http://192.168.0.33:8081/repository/3rd_part/ -DrepositoryId=nexus
