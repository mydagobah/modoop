#!/usr/bin/env python

from pymongo import Connection
from random import randint
#from datetime import datetime

conn = Connection()
db = conn.modoop
collection_in = db['demo.in']
collection_out = db['demo.out']

# drop the db
collection_in.drop()
collection_out.drop()

#
size = 10000
for i in range(0, size):
    doc = {}
    doc['list'] = 'list' + str(randint(0, 10))
    doc['open'] = randint(0, 5)
    doc['click'] = randint(0, 5)
    collection_in.save(doc)
