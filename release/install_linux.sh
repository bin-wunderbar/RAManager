#!/bin/bash

APPNAME=RAManager
PROGRAM=RAManager.jar
ICON=rekord_autoak_logo.png
DEST_FOLDER=/opt/ramanager
DESKTOP=Escritorio

mkdir -p $DEST_FOLDER

cp $PROGRAM $DEST_FOLDER
cp $ICON $DEST_FOLDER

for homeuser in `ls /home`
do
    if [ ! -d /home/$homeuser/$DESKTOP ]; then mkdir -p /home/$homeuser/$DESKTOP; fi

cat<<EOF > /home/$homeuser/$DESKTOP/$APPNAME.desktop
[Desktop Entry]
Version=1.0
Type=Application
Terminal=false
Icon=$DEST_FOLDER/$ICON
Icon[es_ES]=$DEST_FOLDER/$ICON
Name[es_ES]=$APPNAME
Exec=java -jar $DEST_FOLDER/$PROGRAM
Name=$APPNAME
EOF

chown $homeuser /home/$homeuser/$DESKTOP/$APPNAME.desktop
chmod +x /home/$homeuser/$DESKTOP/$APPNAME.desktop

done

