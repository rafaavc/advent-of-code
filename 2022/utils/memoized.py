def memoized(fn):
    mem = dict()

    def aux(*args):
        key = args[:2]
        if key in mem:
            return mem[key]
        result = fn(*args)
        mem[key] = result
        return result

    return aux
