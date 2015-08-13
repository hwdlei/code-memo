#!/usr/bin/env python
# coding=utf-8
import sys
import time
for line in sys.stdin:
    line = line.strip()
    userid, movieid, rating, unixtime = line.split('\t')
    weekday = time.localtime(float(unixtime)).tm_wday
    print '\t'.join([userid, movieid, rating, str(weekday)])