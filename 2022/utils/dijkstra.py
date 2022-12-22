from queue import PriorityQueue


def dijkstra(data, expand, check_end, start, end):
    pq = PriorityQueue()
    pq.put((0, start))
    seen = {start}
    while not pq.empty():
        steps, node = pq.get()
        if check_end(node, end):
            return steps
        expand(data, node, steps, pq, seen)
    return -1
