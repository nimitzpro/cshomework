# import asyncio
# @asyncio.coroutine
# def say_boo():
#   i = 0
#   while True:
#     yield None
#     print("...boo {0}".format(i))
#     i += 1
# @asyncio.coroutine
# def say_baa():
#   i = 0
#   while True:
#     yield
#     print("...baa {0}".format(i))
#     i += 1
# boo_task = asyncio.ensure_future(say_boo())
# baa_task = asyncio.ensure_future(say_baa())
# loop = asyncio.get_event_loop()
# loop.run_forever()

import asyncio
from concurrent.futures import ProcessPoolExecutor
print('running async test')
def say_boo():
    i = 0
    while True:
        print('...boo {0}'.format(i))
        i += 1
def say_baa():
    i = 0
    while True:
        print('...baa {0}'.format(i))
        i += 1

async def main():
    print("running main")
    executor = ProcessPoolExecutor(2)
    boo = asyncio.create_task(loop.run_in_executor(executor, say_boo))
    baa = asyncio.create_task(loop.run_in_executor(executor, say_baa))

if __name__ == "__main__":
    loop = asyncio.get_event_loop()
    loop.create_task(main())
    loop.run_forever()