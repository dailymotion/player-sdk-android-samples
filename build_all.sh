#!/usr/bin/env bash -e

BUILD_CMD="./gradlew clean assembleDebug"
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