#!/usr/bin/env bash

mvn clean package

echo 'Copy files...'

scp -i ~/.ssh/id_rsa \
    target/auto-0.0.1-SNAPSHOT.jar \
    bato@185.185.68.121:/home/bato/
echo 'Restart server...'

ssh -i ~/.ssh/id_rsa bato@185.185.68.121 << EOF
pgrep java | xargs kill -9
nohup java -jar auto-0.0.1-SNAPSHOT.jar > log.txt &
EOF

echo 'Bye'