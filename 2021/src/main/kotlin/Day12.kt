import utils.FileManager
import utils.Logger

class Node(value: String) {
    val children = mutableSetOf<Node>()
    val isLowercase: Boolean; val isEnd: Boolean; val isStart: Boolean

    init {
        isLowercase = value[0].isLowerCase()
        isEnd = value == "end"
        isStart = value == "start"
    }

    fun addChild(child: Node) {
        children.add(child)
    }
}

fun main() {
    val lines = FileManager.read(12)

    val nodes = mutableMapOf<String, Node>()

    fun createNode(name: String) {
        if (!nodes.containsKey(name))
            nodes[name] = Node(name)
    }

    fun addChild(c1: String, c2: String) {
        nodes[c1]!!.addChild(nodes[c2]!!)
        nodes[c2]!!.addChild(nodes[c1]!!)
    }

    for (line in lines) {
        val split = line.split("-")
        for (s in split) createNode(s)
        addChild(split[0], split[1])
        addChild(split[1], split[0])
    }

    val root = nodes["start"]!!

    fun distinctPaths1(node: Node, lowercaseAncestors: Set<Node>): Int {
        if (node.isEnd) return 1

        var result = 0
        for (c in node.children) {
            if (lowercaseAncestors.contains(c)) continue

            val childLowercaseAncestors = mutableSetOf<Node>()
            childLowercaseAncestors.addAll(lowercaseAncestors)
            if (node.isLowercase) childLowercaseAncestors.add(node)

            result += distinctPaths1(c, childLowercaseAncestors)
        }
        return result
    }

    fun solvePuzzle1(): Int = distinctPaths1(root, setOf())

    fun distinctPaths2(node: Node, lowercaseAncestors: Set<Node>, hasSeenLowercase: Boolean = false): Int {
        if (node.isEnd) return 1

        var result = 0
        for (c in node.children) {
            if (c.isStart) continue

            if (lowercaseAncestors.contains(c) && hasSeenLowercase) continue

            val childLowercaseAncestors = mutableSetOf<Node>()
            childLowercaseAncestors.addAll(lowercaseAncestors)
            if (node.isLowercase) childLowercaseAncestors.add(node)

            if (lowercaseAncestors.contains(c)) { // && !hasSeenLowercase
                result += distinctPaths2(c, childLowercaseAncestors, true)
                continue
            }

            result += distinctPaths2(c, childLowercaseAncestors, hasSeenLowercase)
        }
        return result
    }

    fun solvePuzzle2(): Int = distinctPaths2(root, setOf())

    Logger.logResults(solvePuzzle1(), solvePuzzle2())
}
