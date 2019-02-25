#!/bin/sh

echo Backup db script started...
echo DATE=`date '+%Y_%m_%d_%H_%M_%S'`

echo Number of ASH db backup dumps:
NUM_ASH_DB=$(find /media/app/data/backupDumps/automationservicehub* -maxdepth 0 | wc -l)
echo $NUM_ASH_DB

if [ "$NUM_ASH_DB" -gt 3 ]
	then echo WARNING: You have over 3 backup dumps!
fi

echo Making ASH DB Backup as db dump
mysqldump -h 127.0.0.1 --port 3311 -u root automationservicehub  --max_allowed_packet=512M > /media/app/data/backupDumps/automationservicehub`date '+%Y_%m_%d_%H_%M_%S'`.sql
echo ASH DB Backup done!

echo ...backup done!