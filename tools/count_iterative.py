#!/usr/bin/env python

from pymongo import Connection
import timeit
#from datetime import datetime
start_time = timeit.default_timer()

conn = Connection()
db = conn.modoop
collection_in = db['demo.in']
collection_out = db['demo.out']

# drop the db
collection_out.drop()

#
summary = {}

for stats in collection_in.find():
    listname = stats['list']
    opens = stats['open']
    clicks = stats['click']

    if listname in summary:
	tmp = summary[listname]
	summary[listname] = {'opens' : opens + tmp['opens'], 'clicks' : clicks + tmp['clicks']}
    else:
	summary[listname] = {'opens' : opens, 'clicks' : clicks}

for l in summary:
    doc = {}
    doc['list'] = l
    doc['opens'] = summary[l]['opens']
    doc['clicks'] = summary[l]['clicks']
    collection_out.save(doc)

elapsed = timeit.default_timer() - start_time

print "run time: ", elapsed
