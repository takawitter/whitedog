#!/bin/bash
if [ $# -ne 1 ]; then
	echo "Usage: install-jgroups.sh revision"
	exit 1
fi
java -jar ../build/lib.ivy/ivy-2.0.0-rc2.jar -settings ivysettings.xml -ivy install-jgroups-$1.xml -publish internal -revision $1 -publishpattern work/JGroups-$1.GA.bin/[artifact].[ext] -deliverto work/JGroups-[revision].xml
