"""
    More Dynamic Programming. 

    1. Recursification
    2. Statification
    3. Default Param Exploit
"""


def fib_recur_mem(n):
    """
        Inner Scope Recursification of Fibo-Sequence with Memiozation
    """
    if n <= 0:
        return None
    T = {}
    def F(i):
        if i == 1 or i == 2:
            return 1
        if i in T:
            return T[i]
        T[i] = F(i - 1) + F(i - 2)
    return F(n)


def fib_recur_mem_stackified(n, T = {}):
    """
        Inner Scope Stackification of Fibo-Sequence Memiozation
    """
    if n <= 0:
        return None
    T[1] = T[2] = 1  # Important! Base Cases. 
    def F(i):
        if i in T:
            return T[i]  # Out of this means no base cases. 
        S = [i]
        while len(S) != 0:
            i = S[-1]
            # Merge in ---------------------------------------------------------
            if ((i - 1) in T) and ((i - 2) in T):
                T[i] = T[i - 1] + T[i - 2]
                S.pop()
            # Branch out -------------------------------------------------------
            else:
                if (i - 1) not in T:
                    S.append(i - 1)
                    continue
                S.append(i - 2)
                continue
        return T[i]
    return F(n)


def Ackerman(m, n):
    T = {}  # mem
    M, N = m, n
    Stack = [(M, N)]
    while len(Stack) != 0:
        M, N = Stack[-1]
        if M == 0:
            T[M, N] = N + 1
            Stack.pop()
            continue
        if M > 0 and N == 0:
            if (M - 1, 1) in T:
                T[M, N] = T[M - 1, 1]
                Stack.pop()
            else:
                Stack.append((M - 1, 1))
            continue
        if M > 0 and N > 0:
            if (M, N - 1) in T:
                if (M - 1, T[M, N - 1]) in T:
                    T[M, N] = T[M - 1, T[M, N - 1]]
                    Stack.pop()
                else:
                    Stack.append((M - 1, T[M, N - 1]))
                continue
            else:
                Stack.append((M, N - 1))
            continue
    return T[m, n]

def main():
    # for I in range(5000):
    #     print(fib_recur_mem_stackified(I))
    print(Ackerman(2, 4))
    print(Ackerman(3, 4))
    print(Ackerman(4, 1))



if __name__ == "__main__":
    main()