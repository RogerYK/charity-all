#!/usr/bin/env bash


docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.4.3
docker run -d --name kibana --link elasticsearch:elasticsearch -p 5601:5601 kibana:6.4.3
