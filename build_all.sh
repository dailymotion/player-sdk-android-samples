#!/usr/bin/env bash -e

# Usage: ./build_all.sh [JVM_PATH]
#   JVM_PATH (optional): JDK home used by Gradle, e.g. "$(/usr/libexec/java_home -v 21)".
#   Can also be provided through the JVM_PATH environment variable.
JVM_PATH="${1:-$JVM_PATH}"

BUILD_CMD="./gradlew clean assembleDebug"
if [ -n "$JVM_PATH" ]; then
	if [ ! -x "$JVM_PATH/bin/java" ]; then
		echo "==> Invalid JVM path: $JVM_PATH (no executable bin/java found)" >&2
		exit 1
	fi
	BUILD_CMD="$BUILD_CMD -Dorg.gradle.java.home=\"$JVM_PATH\""
fi

PROJECT_LIST=('BasicExample' 'CustomFullscreen' 'JetpackNavigationFullscreen' 'AdvancedExample')

for i in "${PROJECT_LIST[@]}"
do
	pushd . > /dev/null
	
	cd $i
	echo -e "\n==> Building project $i ..."
	echo "==> Running command: $BUILD_CMD"
	eval $BUILD_CMD
	echo "==> Building project $i DONE"
	
	popd > /dev/null
done