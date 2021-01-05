"""
    This is a collection of good/classic/insteresting Dynamic Programming problems and their corresponding solutions. 

"""


def edit_distance(w1, w2):
    """
        Given 2 string, what is the minimum number of operations that transform one string to the other. 
        Set of operations: 
        1. Deletion. 
        2. Insersion. 
        3. Replacement of character.

        From "Edit Distance Leetcode"
    """
    M, N = len(w1), len(w2)
    T = {}
    def recur(I, J):
        if (I, J) in T:
            return T[I, J]
        if I < 0 or J < 0:
            return max(I, J) + 1
        L1, L2 = w1[I], w2[J]
        if L1 == L2:
            return recur(I - 1, J - 1)  # it won't change the edit distance!
        T[I, J] = 1 + min(recur(I - 1, J), recur(I, J - 1), recur(I - 1, J - 1))
        return T[I, J]
    return recur(M - 1, N - 1)


def edit_distance2(a, b):
    """
        Given 2 string, determine whether it is possible, to reduce string "a" to string "b" with the following 
        set of operations. 

        From "Hackerrank Abbreviation"
    """
    T = {}
    M, N = len(a) - 1, len(b) - 1
    def DP(I, J):
        if (I, J) in T:
            return T[I, J]
        if I < 0 and J < 0:
            return True
        if I < 0:
            return False
        if J < 0:
            return True
    for I in range(M):
        for J in range(N):
            L1, L2 = a[I], b[J]
            if L1.lower() == L1:
                T[I, J] = DP(I - 1, J) or (DP(I - 1, J - 1) and L1.upper() == L2)
            else:
                T[I, J] = L1 == L2 and DP(I - 1, J - 1)
    return T[M - 1, N - 1]



def candies(arr):
    """
        Given an array of integers, assign value for each integers such that, if the neighbor of an integer 
        is higher, then it will get assigned with a higher score. 

        From: "Candies Hackerrank"
    """
    T = {}  # Recrusion table, storing the result. 
    T[-1] = T[len(arr)] = 0  # Recursion Base Case. 

    def MinCandyFor(i):
        if i in T:
            return T[i]
        RecurStack = [i] # Root. 
        while len(RecurStack) != 0:
            i = RecurStack[-1]
            ScoreLeft = 0 if i - 1 < 0 else arr[i - 1]
            ScoreRight = 0 if i + 1 >= len(arr) else arr[i + 1]
            # Branch Out -------------------------------------------------------
            if arr[i] > ScoreLeft and (i - 1) not in T:
                RecurStack.append(i - 1)
                continue
            if arr[i] > ScoreRight and (i + 1) not in T:
                RecurStack.append(i + 1)
                continue
            # Merge in ---------------------------------------------------------
            LowerBoundLeft = (T[i - 1] + 1) if arr[i] > ScoreLeft else 1
            LowerRightBound = (T[i + 1] + 1) if arr[i] > ScoreRight else 1
            T[i] = max(LowerBoundLeft, LowerRightBound)
            RecurStack.pop() # Only pop when it's solved! 
        return T[i]
    
    Candies = []
    for I in range(len(arr)):
        Candies.append(MinCandyFor(I))
    return sum(Candies)



def main():
    pass


if __name__ == "__main__":
    main()