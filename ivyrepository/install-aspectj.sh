#!/bin/bash
if [ $# -ne 1 ]; then
	echo "Usage: install-aspectj.sh revision"
	exit 1
fi
java -jar ../build/lib.ivy/ivy-2.0.0-rc2.jar -settings ivysettings.xml -ivy install-aspectj-$1.xml -publish internal -revision $1 -publishpattern work/aspectj-$1/lib/[artifact].[ext] -deliverto work/aspectj-[revision].xml
