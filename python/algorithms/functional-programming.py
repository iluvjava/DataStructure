def VectorDotProduct_Traditional(a, b):
    """
        a,b are arrays of numbers

        goal: foreach element, we multiply each elemebt from a, b and sum them up. 
    """
    RunningSum = 0
    for I in range(min(len(a), len(b))):
        RunningSum += a[I]*b[I]
    return RunningSum


def VectorDotProduct_Functional(a, b):
    Zipped = zip(a, b)
    Mapped = map(lambda x : x[0]*x[1], Zipped)
    Summed = sum(Mapped)
    return Summed


def main():
    print(VectorDotProduct_Functional([1, 2, 3], [3, 2, 1]))
    print(VectorDotProduct_Traditional([1, 2, 3], [3, 2, 1]))
    

if __name__ == "__main__":
    main()