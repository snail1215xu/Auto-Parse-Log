
JAVA_SRC="lib/src/MainLog.java lib/src/ParseLogInfo.java lib/src/ReadConfigFile.java\
		  lib/src/LogFormat.java lib/src/Log.java"
rm  -rf out
mkdir out
javac -cp ./out -d ./out ${JAVA_SRC}
