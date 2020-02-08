#!/usr/bin/env bash

docker run --name charity-db \
        -p 3306:3306  \
        -e MYSQL_ROOT_PASSWORD=root \
        -e MYSQL_DATABASE=charity   \
        -v "$(pwd)"/sql:/docker-entrypoint-initdb.d/ \
        -v "$(pwd)"/.data:/var/lib/mysql \
        -d  \
        mysql