

def to_adjlist(edges):
    """
        Edges are a list list, it assumes that the graph undirected
        returns the adjacency list of the graph.
    """
    AdjList = {}
    for L in edges:
        [U, V] = L
        UNeighbours = AdjList.get(U, [])
        VNeighbours = AdjList.get(V, [])
        UNeighbours.append(V)
        VNeighbours.append(U)
        AdjList[U] = UNeighbours
        AdjList[V] = VNeighbours
    print(f"Adjlist: {AdjList}")
    return AdjList


def DFS_recursive(root, adjlist):

    pass


def DFS_iterative(root, adjlist):
    """
            Does a DFS search on the graph and returns the depth for each of the vertices 
            on the DFS tree. 
            * Make sure the root node is in the adjlist list. 
            * This implementation is an advanced iterative solution. 
    """
    NextNeighbours = {}
    for V, _ in adjlist.items():
        NextNeighbours[V] = 0
    Stack = [root]
    Visited = set()
    Depth = {}
    Depth[root] = 0
    while Stack:
        print(f"Stack: {Stack}")
        V = Stack[-1]
        Visited.add(V)
        Exec = False
        while NextNeighbours[V] < len(adjlist[V]):
            Neighbour = adjlist[V][NextNeighbours[V]]
            if Neighbour not in Visited:
                Stack.append(Neighbour)
                NextNeighbours[V] += 1
                Depth[Neighbour] = Depth[V] + 1
                Exec = True
                break
            else:
                NextNeighbours[V] += 1
        if Exec:
            continue
        else:
            Stack.pop()
    return Depth


def BFS(startingVertices, adjlist):
    """
            Does a BFS search on the tree and returns the depth of the vertices on on the BFS tree 
            after the searching. 
            * Root node is at level 0
    """
    startingVertices = [startingVertices] if type(
        startingVertices) == int else startingVertices
    Visited = set(startingVertices)
    Depth = dict((V, 0) for V in startingVertices)
    while startingVertices:
        V = startingVertices.pop(0)
        for Neighbour in adjlist[V]:
            if Neighbour not in Visited:
                startingVertices.append(Neighbour)
                Visited.add(Neighbour)
                Depth[Neighbour] = Depth[V] + 1
    return Depth


def BFS_Advanced(startingVertices, adjlist):
    """
            Des the BFS search and construct the BFS tree for the graph after the BFS searching. 
    """

    pass


def main():
    def Test1():
        Edges = [
            [1, 2],
            [2, 3],
            [3, 4],
            [4, 1]
        ]
        print(to_adjlist(Edges))

    def Test2():
        EdgeList = [
            [1, 2],
            [2, 4],
            [2, 5],
            [1, 3],
            [3, 6],
            [3, 7],
            [1, 7],
            [1, 4]
        ]
        return

    def TestBFS_DFS():
        Edges = [
            [1, 2], [2, 3],
            [1, 4], [2, 5], [3, 6],
            [4, 5], [5, 6],
            [4, 7], [5, 8], [6, 9],
            [7, 8], [8, 9]]

        Adjlist = to_adjlist(Edges)
        print(f"Adjlist of the 3 by 3 grid is constructed")
        print(Adjlist)
        DepthMap = BFS(1, Adjlist)
        print(f"Depth map from the BFS: ")
        print(DepthMap)
        DepthMap = DFS_iterative(1, Adjlist)
        print(f"DepthMap from the DFS: ")
        print(DepthMap)
        return

    Test1()
    Test2()
    TestBFS_DFS()


if __name__ == "__main__":
    main()
