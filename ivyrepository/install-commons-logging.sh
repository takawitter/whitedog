#!/bin/bash
if [ $# -ne 1 ]; then
	echo "Usage: install-commons-logging.sh revision"
	exit 1
fi
java -jar ../build/lib.ivy/ivy-2.0.0-rc2.jar -settings ivysettings.xml -ivy install-commons-logging-$1.xml -publish internal -revision $1 -publishpattern work/commons-logging-$1/[artifact]-[revision].[ext] -deliverto work/commons-logging-[revision].xml
