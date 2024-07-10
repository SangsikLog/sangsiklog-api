#!/bin/bash

# Redis 노드가 시작되고 서비스가 완전히 준비되기를 기다린다.
sleep 1

redis-cli --cluster call redis-node-1:7000 flushall
redis-cli --cluster call redis-node-1:7000 cluster reset

# 마스터 설정하기
echo "yes" | redis-cli --cluster create redis-node-1:7000 redis-node-2:7001 redis-node-3:7002

# 슬레이브 등록하기
echo "yes" | redis-cli --cluster add-node redis-node-4:7100 redis-node-1:7000 --cluster-slave
echo "yes" | redis-cli --cluster add-node redis-node-5:7101 redis-node-2:7001 --cluster-slave
echo "yes" | redis-cli --cluster add-node redis-node-6:7102 redis-node-3:7002 --cluster-slave

# 노드만 만들고 클러스터 구성 안 한 상태로 Master-slave 한번에 구성하기
#echo "yes" | redis-cli --cluster create master1:7000 master2:7001 master3:7002 slave1:7100 slave2:7101 slave3:7102 --cluster-replicas 1

# 클러스터 정보 확인
redis-cli --cluster check master1:7000

# redis-stat
#java -jar redis-stat-0.4.14.jar localhost:7000 localhost:7001 localhost:7002 localhost:7100 localhost:7101 localhost:7102 --server=8888