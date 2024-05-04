import time

import redis

r = redis.Redis(db=1, password="123456")

# start = time.time()
# name = r.get("name")
# print(name)
# print(time.time() - start, "s")

# wst = time.time()
# r.set("age", 18)
# print(time.time() - wst, "s")

# rst = time.time()
# remark = r.get("remark")
# print(time.time() - rst, "s")
# print(remark)

s1 = time.time()
r.hset("user", items=["name", "zhangsan", "age", 18, "remark", "平安喜乐"])
print(time.time() - s1, "s")
